package controller;
import model.ADT.MyIList;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;
import model.MyException;
import model.PrgState;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
public class Controller {
    private IRepository repository;
    private ExecutorService executor;
    private List<String> outputList;
    public Controller(IRepository repository) {
        this.repository = repository;
    }
    public IRepository getRepository() {
        return repository;
    }
    public void setRepository(IRepository repository) {
        this.repository = repository;
    }
    public List<PrgState> getProgramStates(){
        return repository.getProgramList();
    }

    public void setProgramStates(List<PrgState> prgStates) {
        repository.setProgramList(prgStates);
    }

    //add a method that gets the output list not the exe stack
    public MyIList<Value> getOut(PrgState state){
        return state.getOut();
    }
    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }


    private List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, p.getHeap().getContent())));
    }

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        List<Integer> heapAddr = getAddrFromHeap(heap.values());
        return heap.entrySet().stream()
                .filter(e -> (symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public List<PrgState> removeCompletedPrg(List<PrgState> inputProgramList) {
        return inputProgramList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }
    public void oneStepForAllPrograms(List<PrgState> programList) throws InterruptedException{
        programList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        List<Callable<PrgState>> callableList = programList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<PrgState> newProgramList = executor.invokeAll(callableList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());
        programList.addAll(newProgramList);
        programList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        repository.setProgramList(programList);
    }

    public void allStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programList = removeCompletedPrg(repository.getProgramList());
        while(programList.size() > 0){
            conservativeGarbageCollector(programList);
            oneStepForAllPrograms(programList);
            System.out.println(programList.get(0).getOut());
            programList = removeCompletedPrg(repository.getProgramList());
        }
        executor.shutdownNow();
        repository.setProgramList(programList);
    }

    public void oneStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repository.getProgramList());
        oneStepForAllPrograms(programStates);
        conservativeGarbageCollector(programStates);
        executor.shutdownNow();
    }
}

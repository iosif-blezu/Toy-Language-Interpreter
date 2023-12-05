package controller;
import model.ADT.ListInterface;
import model.value.RefrenceValue;
import model.value.Value;
import repository.InterfaceRepository;
import model.MyException;
import model.ProgramState;
import model.ADT.StackInterface;
import model.statement.InterfaceStatement;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private InterfaceRepository repository;
    public Controller(InterfaceRepository repository)
    {
        this.repository = repository;
    }
    public InterfaceRepository getRepository()
    {
        return repository;
    }
    public void setRepository(InterfaceRepository repository)
    {
        this.repository = repository;
    }
    public List<ProgramState> getProgramStates()
    {
        return repository.getProgramStateList();
    }

    //add a method that gets the output list not the exe stack

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefrenceValue)
                .map(v -> {RefrenceValue v1 = (RefrenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Map<Integer,Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heapAddr){
        return heapAddr.entrySet().stream()
                .filter(elem-> symTableAddr.contains(elem.getKey()) || heapAddr.containsKey(elem.getKey()))
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue)); }

    public ListInterface<Value> getOut(ProgramState state)
    {
        return state.getOutputList();
    }

    public ProgramState oneStep(ProgramState state) throws MyException
    {
        StackInterface<InterfaceStatement> exeStack = state.getExecutionStack();
        if (exeStack.isEmpty()) throw new MyException("ProgramState stack is empty");
        InterfaceStatement crtStmt = exeStack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException
    {
        ProgramState prg = repository.getCurrentProgram();
        repository.logProgramStateExecution(prg);
        while (!prg.getExecutionStack().isEmpty())
        {
            oneStep(prg);
            repository.logProgramStateExecution(prg);
            prg.getHeapTable().setContent((HashMap<Integer, Value>) safeGarbageCollector(
                    getAddrFromSymTable(prg.getSymbolTable().getDict().values()),
                    prg.getHeapTable().getContent()));
        }
    }
    public void displayPrgState(ProgramState state)
    {
        System.out.println(state);
    }
    private void printThings()
    {
        ProgramState programState = repository.getCurrentProgram();

        System.out.println("******************************************************");

        System.out.println("Execution Stack:");
        System.out.println("================");
        System.out.println(programState.getExecutionStack());

        System.out.println("Output List:");
        System.out.println("============");
        System.out.println(programState.getOutputList());

        System.out.println("Symbol Table:");
        System.out.println("=============");
        System.out.println(programState.getSymbolTable());

        System.out.println("File Table:");
        System.out.println("===========");
        System.out.println(programState.getFileTable());

        System.out.println("******************************************************");
    }
}

package controller;
import model.ADT.ListInterface;
import model.value.Value;
import repository.InterfaceRepository;
import model.MyException;
import model.ProgramState;
import model.ADT.StackInterface;
import model.statement.InterfaceStatement;

import java.util.List;

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

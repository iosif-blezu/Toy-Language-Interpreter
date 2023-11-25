package model;

import model.ADT.*;
import model.statement.InterfaceStatement;
import model.value.Value;
import java.io.BufferedReader;


public class ProgramState {
    StackInterface<InterfaceStatement> executionStack;
    DictionaryInterface<String, Value> symbolTable;
    ListInterface<Value> outputList;
    FileTableInterface<String, BufferedReader> fileTable;
    InterfaceStatement originalProgram; //optional field, but good to have
    public ProgramState(StackInterface<InterfaceStatement> stack, DictionaryInterface<String, Value> symTable, ListInterface<Value> out, FileTableInterface<String,BufferedReader> fileTable , InterfaceStatement program){
        executionStack = stack;
        symbolTable = symTable;
        outputList = out;
        originalProgram = program;
        this.fileTable = fileTable;
        stack.push(program);
    }

    @Override
    public String toString() {
        return "ProgramState{" +
                "executionStack = " + executionStack +
                ", symbolTable = " + symbolTable +
                ", outputList = " + outputList +
                '}';
    }

    public StackInterface<InterfaceStatement> getExecutionStack()
    {
        return executionStack;
    }
    public DictionaryInterface<String, Value> getSymbolTable()
    {
        return symbolTable;
    }
    public ListInterface<Value> getOutputList()
    {
        return outputList;
    }
    public FileTableInterface<String, BufferedReader> getFileTable()
    {
        return fileTable;
    }

    public void setFileTable(FileTableInterface<String, BufferedReader> fileTable)
    {
        this.fileTable = fileTable;
    }
    public void setExecutionStack(StackInterface<InterfaceStatement> executionStack)
    {
        this.executionStack = executionStack;
    }

    public void setSymbolTable(DictionaryInterface<String, Value> symbolTable)
    {
        this.symbolTable = symbolTable;
    }
    public void setOutputList(ListInterface<Value> outputList)
    {
        this.outputList = outputList;
    }
    public boolean isNotCompleted()
    {
        return !executionStack.isEmpty();
    }
    public ProgramState oneStep() throws Exception
    {
        if(executionStack.isEmpty()) throw new Exception("Program state stack is empty");
        InterfaceStatement crtStmt = executionStack.pop();
        return crtStmt.execute(this);
    }
    public InterfaceStatement getOriginalProgram()
    {
        return originalProgram;
    }
}

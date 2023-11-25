package repository;

import model.MyException;
import model.ProgramState;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements InterfaceRepository {
    private final List<ProgramState> programStateList;
    private final String logFilePath;
    public Repository(List<ProgramState> p, String givenLogFilePath) {
        programStateList = p;
        logFilePath = givenLogFilePath;
    }

    @Override
    public List<ProgramState> getProgramStateList()
    {
        return this.programStateList;
    }

    @Override
    public ProgramState getCurrentProgram()
    {
        return this.programStateList.get(0);
    }
    @Override
    public void addProgram(ProgramState prg)
    {
        this.programStateList.add(prg);
    }

    @Override
    public void logProgramStateExecution(ProgramState program) throws MyException
    {
        try
        {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            logFile.println("-----ExeStack-----");
            logFile.println(program.getExecutionStack().toString());
            logFile.println("-----SymbolTable-----");
            logFile.println(program.getSymbolTable().toString());
            logFile.println("-----Output-----");
            logFile.println(program.getOutputList().toString());
            logFile.println("-----FileTable-----");
            logFile.println(program.getFileTable().toString());
            logFile.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            logFile.println("\n");
            logFile.close();
        }
        catch (IOException e)
        {
            throw new MyException("Error writing to log file");
        }
    }


}

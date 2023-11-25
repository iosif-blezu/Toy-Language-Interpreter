package repository;

import model.ProgramState;
import model.MyException;

import java.util.List;

public interface InterfaceRepository {
    public List<ProgramState> getProgramStateList();

    ProgramState getCurrentProgram();

    void addProgram(ProgramState prg);
    void logProgramStateExecution(ProgramState program) throws MyException;
}

package model.statement;

import model.MyException;
import model.ProgramState;


public interface InterfaceStatement {
    public ProgramState execute(ProgramState state) throws MyException;
}

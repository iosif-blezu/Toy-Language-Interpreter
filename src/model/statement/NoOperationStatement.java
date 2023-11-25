package model.statement;

import model.MyException;
import model.ProgramState;

public class NoOperationStatement implements InterfaceStatement {
    public NoOperationStatement(){}
    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
        return null;
    }

}

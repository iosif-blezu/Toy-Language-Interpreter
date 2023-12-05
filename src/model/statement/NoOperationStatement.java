package model.statement;

import model.ADT.DictionaryInterface;
import model.MyException;
import model.ProgramState;
import model.type.Type;

public class NoOperationStatement implements InterfaceStatement {
    public NoOperationStatement(){}
    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

}

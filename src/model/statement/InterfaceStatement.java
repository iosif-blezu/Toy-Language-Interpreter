package model.statement;

import model.ADT.DictionaryInterface;
import model.MyException;
import model.ProgramState;
import model.type.Type;


public interface InterfaceStatement {
    public ProgramState execute(ProgramState state) throws MyException;

    DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException;
}

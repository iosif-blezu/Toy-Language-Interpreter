package model.statement;

import model.ADT.*;
import model.MyException;
import model.ProgramState;
import model.type.Type;
import model.value.Value;
public class VariableDeclarationStatement implements InterfaceStatement {
    private String name;
    private Type type;
    public VariableDeclarationStatement(String name, Type type){
        this.name = name;
        this.type = type;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Type getType()
    {
        return type;
    }
    public void setType(Type type)
    {
        this.type = type;
    }
    @Override
    public String toString()
    {
        return type.toString() + " " + name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
       StackInterface<InterfaceStatement> exeStack = state.getExecutionStack();
         DictionaryInterface<String, Value> symTable = state.getSymbolTable();
            if(symTable.isDefined(name))
            {
                throw new MyException("Variable already declared");
            }
            else
            {
                symTable.add(name, type.defaultValue());
            }

            return state;
    }
}

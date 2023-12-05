package model.expression;

import model.ADT.DictionaryInterface;
import model.ADT.HeapInterface;
import model.MyException;
import model.type.Type;
import model.value.Value;

public class VariableExpression implements Expression {
    private String id;
    public VariableExpression(String i)
    {
        id=i;
    }
    public String getId()
    {
        return id;
    }
    public String setId(String i)
    {
        return id=i;
    }
    public String toString() {
        return "VarExp{" +
                "id = '" + id + '\'' +
                '}';
    }

    @Override
    public Value eval(DictionaryInterface<String, Value> table, HeapInterface heapTable) throws MyException
    {
        return table.lookup(id);
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }


}

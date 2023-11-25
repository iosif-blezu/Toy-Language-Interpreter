package model.expression;

import model.ADT.DictionaryInterface;
import model.MyException;
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
    public Value eval(DictionaryInterface<String, Value> table) throws MyException
    {
        return table.lookup(id);
    }


}

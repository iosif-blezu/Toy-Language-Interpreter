package model.expression;

import model.ADT.DictionaryInterface;
import model.ADT.HeapInterface;
import model.MyException;
import model.type.Type;
import model.value.Value;

public class ValueExpression implements Expression {
    private Value e;
    public ValueExpression(Value e)
    {
        this.e = e;
    }
    public Value getE()
    {
        return this.e;
    }
    public void setE(Value e)
    {
        this.e = e;
    }
    @Override
    public String toString()
    {
        return "ValueExp{"+
                "e = "+e+
                '}';
    }

    @Override
    public Value eval(DictionaryInterface<String, Value> tbl, HeapInterface heapTable) throws MyException
    {
        return e;
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

}

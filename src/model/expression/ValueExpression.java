package model.expression;

import model.ADT.DictionaryInterface;
import model.MyException;
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
    public Value eval(DictionaryInterface<String, Value> tbl) throws MyException
    {
        return e;
    }

}

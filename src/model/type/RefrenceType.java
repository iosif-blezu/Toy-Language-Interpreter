package model.type;


import model.value.RefrenceValue;
import model.value.Value;

public class RefrenceType implements Type
{
    private Type inner;

    public RefrenceType(Type inner)
    {
        this.inner = inner;
    }

    public Type getInner()
    {
        return inner;
    }

    public void setInner(Type inner)
    {
        this.inner = inner;
    }

    public boolean equals(Object another)
    {
        if (another instanceof RefrenceType)
        {
            return inner.equals(((RefrenceType) another).getInner());
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public Value defaultValue()
    {
        return new RefrenceValue(0, inner);
    }
}
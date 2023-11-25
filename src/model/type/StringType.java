package model.type;

import model.value.Value;

public class StringType implements Type{

    @Override
    public boolean equals(Object anotherObject) {
        return anotherObject instanceof StringType;
    }
    public String toString()
    {
        return "String";
    }

    @Override
    public Value defaultValue()
    {
        return new model.value.StringValue("");
    }
}

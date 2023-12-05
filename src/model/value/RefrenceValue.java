package model.value;
import model.type.RefrenceType;
import model.type.Type;

public class RefrenceValue implements Value
{
    private final int address;
    private final Type locationType;


    public RefrenceValue(int address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress()
    {
        return address;
    }

    @Override
    public Type getType()
    {
        return new RefrenceType(locationType);
    }

    @Override
    public String toString()
    {
        return "RefValue{" +
                "address=" + address +
                ", locationType=" + locationType +
                '}';
    }
}

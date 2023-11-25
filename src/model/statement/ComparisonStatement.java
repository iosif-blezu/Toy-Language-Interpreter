package model.statement;

import model.MyException;
import model.ProgramState;
import model.ADT.StackInterface;

public class ComparisonStatement implements InterfaceStatement
{
    private InterfaceStatement first;
    private InterfaceStatement second;
    public ComparisonStatement(InterfaceStatement v, InterfaceStatement v2)
    {
        first= v;
        second = v2;
    }
    public InterfaceStatement getFirst()
    {
        return first;
    }
    public void setFirst(InterfaceStatement first)
    {
        this.first = first;
    }
    public InterfaceStatement getSecond()
    {
        return second;
    }
    public void setSecond(InterfaceStatement second)
    {
        this.second = second;
    }
    public String toString()
    {
        return " ("+first.toString()+"; "+ second.toString()+") ";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
        StackInterface<InterfaceStatement> exeStack=state.getExecutionStack();
        exeStack.push(second);
        exeStack.push(first);
        return state;
    }

}

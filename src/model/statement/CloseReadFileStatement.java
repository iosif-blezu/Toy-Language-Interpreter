package model.statement;

import model.ProgramState;
import model.ADT.DictionaryInterface;
import model.ADT.StackInterface;
import model.ADT.FileTableInterface;
import model.expression.Expression;
import model.value.Value;
import model.value.StringValue;
import model.MyException;
import model.type.StringType;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements InterfaceStatement {
    private Expression expression;
    public CloseReadFileStatement(Expression expression)
    {
        this.expression = expression;
    }
    public Expression getExpression()
    {
        return expression;
    }
    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }
    public String toString() {
        return "closeRFile (" +
                "expression = " + expression +
                ')';
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
        DictionaryInterface<String,Value> symbolTable = state.getSymbolTable();
        FileTableInterface<String, BufferedReader> fileTable = state.getFileTable();
        StackInterface<InterfaceStatement> stack = state.getExecutionStack();


        Value value = expression.eval(symbolTable);
        if(value.getType().equals(new StringType()))
        {
            StringValue stringValue = (StringValue)value;
            String filename = stringValue.getValue();
            BufferedReader bufferedReader = fileTable.lookup(filename);
            try
            {
                bufferedReader.close();
            }
            catch (IOException e)
            {
                throw new MyException("File "+filename+" cannot be closed");

            }
            fileTable.remove(filename);
        }
        else
        {
            throw new MyException("Invalid type");
        }
        return null;
    }
}

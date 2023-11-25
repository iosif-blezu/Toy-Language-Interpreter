package model.statement;

import model.ADT.DictionaryInterface;
import model.ADT.FileTableInterface;
import model.ADT.StackInterface;

import model.ProgramState;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;
import model.MyException;
import model.expression.Expression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class OpenReadFileStatement implements InterfaceStatement {
    private Expression expression;
    public OpenReadFileStatement(Expression givenExpression)
    {
        this.expression = givenExpression;
    }
    public String toString(){
        return "openRFile(" +
                "exp = " + expression.toString() +
                ")";
    }

    public void setExpression(Expression givenExpression)
    {
        this.expression = givenExpression;
    }
    public Expression getExpression()
    {
        return this.expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
        StackInterface<InterfaceStatement> stack = state.getExecutionStack();
        DictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        FileTableInterface<String, BufferedReader> fileTable = state.getFileTable();
        Value value = this.expression.eval(symbolTable);
        if(value.getType().equals(new StringType()))
        {
            StringValue stringValue = (StringValue)value;
            String file = stringValue.getValue();
            if(fileTable.isDefined(file))
            {
                throw new MyException("File already opened!");
            }
            else
            {
                try
                {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    fileTable.add(file, bufferedReader);
                }
                catch(IOException e)
                {
                    throw new MyException("File not found!");
                }
            }
        }
        return null;
    }
}

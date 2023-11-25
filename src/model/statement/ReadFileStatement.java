package model.statement;
import model.ADT.*;
import model.MyException;
import model.ProgramState;
import model.type.*;
import model.value.*;
import model.expression.*;
import java.io.BufferedReader;
import java.io.IOException;


public class ReadFileStatement implements InterfaceStatement
{
    private Expression expression;
    private String variable_name;
    public ReadFileStatement(Expression expression, String variable_name)
    {
        this.expression = expression;
        this.variable_name = variable_name;
    }
    public Expression getExpression()
    {
        return this.expression;
    }
    public void setVariableName(String variable_name)
    {
        this.variable_name = variable_name;
    }
    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }
    public String getVariableName()
    {
        return this.variable_name;
    }
    @Override
    public String toString(){
        return "ReadFileStatement(" +
                "expression = " + this.expression.toString() +
                ", variable_name = " + this.variable_name +'\'' +
                ')';
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
       DictionaryInterface<String, Value> symTable = state.getSymbolTable();
       FileTableInterface<String, BufferedReader> fileTable = state.getFileTable();
       StackInterface<InterfaceStatement> stack = state.getExecutionStack();

       if(symTable.isDefined(variable_name))
       {
           Value value = symTable.lookup(variable_name);
           Type type = value.getType();
           if(type.equals(new IntType()))
           {
               StringValue stringValue = (StringValue) expression.eval(symTable);
                String filename = stringValue.getValue();
                BufferedReader bufferedReader = fileTable.lookup(filename);
                try
                {
                    String line = bufferedReader.readLine();
                    IntValue intValue;
                    if(line == null)
                    {
                        intValue = new IntValue(0);
                    }
                    else
                    {
                        intValue = new IntValue(Integer.parseInt(line));
                    }
                    symTable.update(variable_name, intValue);
                }
                catch(IOException e)
                {
                    throw new MyException(e.getMessage());
                }

           }
           else
           {
               throw new MyException("Variable name is not of type int");
           }
       }
       return null;
    }

}

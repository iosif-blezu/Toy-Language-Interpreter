package model.statement;

import model.ADT.*;
import model.MyException;
import model.ProgramState;
import model.expression.Expression;
import model.type.Type;
import model.value.Value;

public class PrintStatement implements InterfaceStatement
{
    Expression expression;
    public PrintStatement(Expression givenExpression)
    {
        expression = givenExpression;
    }
    public Expression getExpression()
    {
        return expression;
    }
    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }
    public String toString()
    { return "print( " + expression.toString() + " )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
        StackInterface<InterfaceStatement> exeStack = state.getExecutionStack();
        ListInterface<Value> out = state.getOutputList();
        DictionaryInterface<String, Value> symTable = state.getSymbolTable();
        HeapInterface heapTable = state.getHeapTable();
        out.add(expression.eval(symTable, heapTable));
        return state;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }


}

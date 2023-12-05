package model.statement;
import model.ADT.*;
import model.MyException;
import model.ProgramState;
import model.expression.Expression;
import model.type.BoolType;
import model.type.Type;
import model.value.Value;
import model.value.BoolValue;
public class IfStatement implements InterfaceStatement
{
    private Expression expression;
    InterfaceStatement thenStatement;
    InterfaceStatement elseStatement;

    public IfStatement(Expression expression, InterfaceStatement thenS, InterfaceStatement elseS)
    {
        this.expression = expression;
        this.thenStatement = thenS;
        this.elseStatement = elseS;
    }
    public Expression getExpression()
    {
        return expression;
    }

    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    public InterfaceStatement getThenStatement()
    {
        return thenStatement;
    }

    public void setThenStatement(InterfaceStatement thenStatement)
    {
        this.thenStatement = thenStatement;
    }

    public InterfaceStatement getElseStatement() {
        return elseStatement;
    }

    public void setElseStatement(InterfaceStatement elseStatement)
    {
        this.elseStatement = elseStatement;
    }
    public String toString(){
        return "(IF("+ expression.toString()+") THEN(" +thenStatement.toString()
                +")ELSE("+elseStatement.toString()+"))";}

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
        StackInterface<InterfaceStatement> exeStack = state.getExecutionStack();
        DictionaryInterface<String, Value> symTable = state.getSymbolTable();
        HeapInterface heapTable = state.getHeapTable();
        Value val = expression.eval(symTable, heapTable);
        if(!val.getType().equals(new BoolType()))
            throw new MyException("Conditional expression is not a boolean");
        else
        {
            BoolValue boolVal = (BoolValue) val;
            if(boolVal.getValue())
                exeStack.push(thenStatement);
            else
                exeStack.push(elseStatement);
        }
        return state;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenStatement.typeCheck(typeEnv.deepCopy());
            elseStatement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else {
            throw new MyException("The condition of if does not the type bool!");
        }
    }


}


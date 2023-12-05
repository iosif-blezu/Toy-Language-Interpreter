package model.statement;

import model.ADT.*;
import model.MyException;
import model.ProgramState;
import model.expression.Expression;
import model.type.Type;
import model.value.Value;
public class AssignStatement implements InterfaceStatement
{
    private Expression expression;
    private String id;
    public AssignStatement(String id, Expression expression)
    {
        this.expression = expression;
        this.id = id;
    }
    public Expression getExpression()
    {
        return expression;
    }
    public String getId()
    {
        return id;
    }
    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String toString()
    {
        return id + " = " + expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        StackInterface<InterfaceStatement> exeStack = state.getExecutionStack();
        DictionaryInterface<String, Value> symTable = state.getSymbolTable();
        HeapInterface heapTable = state.getHeapTable();
        if(symTable.isDefined(id)){
            Value val = expression.eval(symTable, heapTable);
            Type typId = (symTable.lookup(id)).getType();
            if(val.getType().equals(typId))
                symTable.update(id, val);
            else
                throw new MyException("Declared type of variable " + id + " and type of the assigned expression do not match.");
        }
        else
            throw new MyException("The used variable " + id + " was not declared before");
        return state;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        Type typeVariable = typeEnv.lookup(id);
        Type typeExpression = expression.typeCheck(typeEnv);
        if (typeVariable.equals(typeExpression)) {
            return typeEnv;
        }
        else {
            throw new MyException("assignment statement: right hand side and left hand side have different types!");
        }
    }

}

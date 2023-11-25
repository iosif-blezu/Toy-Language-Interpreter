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
        if(symTable.isDefined(id)){
            Value val = expression.eval(symTable);
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

}

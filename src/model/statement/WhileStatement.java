package model.statement;

import model.ADT.DictionaryInterface;
import model.ADT.HeapInterface;
import model.ADT.StackInterface;
import model.MyException;
import model.ProgramState;
import model.expression.Expression;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class WhileStatement implements InterfaceStatement{
    private InterfaceStatement statement;

    private Expression expression;

    public WhileStatement(Expression expression, InterfaceStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public InterfaceStatement getStatement() {
        return statement;
    }

    public void setStatement(InterfaceStatement statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "(while(" + expression.toString() + ")" + statement.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        StackInterface<InterfaceStatement> stack = state.getExecutionStack();
        DictionaryInterface<String, Value> symTable = state.getSymbolTable();
        HeapInterface heap = state.getHeapTable();

        Value v = expression.eval(symTable, heap);
        if (v.getType() instanceof BoolType) {
            BoolValue boolV = (BoolValue) v;
            if (boolV.getVal()) {
                stack.push(this);
                stack.push(statement);
            }
        }
        else {
            throw new MyException("Expression type must be BoolType!");
        }
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        Type typ = expression.typeCheck(typeEnv);
        if (typ.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else {
            throw new MyException("Condition of while statement has not the type bool!");
        }
    }
}
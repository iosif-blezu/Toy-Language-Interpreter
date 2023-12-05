package model.statement;

import model.ADT.HeapInterface;
import model.ADT.DictionaryInterface;
import model.MyException;
import model.ProgramState;
import model.expression.Expression;
import model.type.RefrenceType;
import model.type.Type;
import model.value.IntValue;
import model.value.RefrenceValue;
import model.value.Value;

public class WriteHeapStatement implements InterfaceStatement{
    private String varName;
    private Expression expression;

    public WriteHeapStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "WriteHeap("+ varName+ "," +expression + ')';
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        DictionaryInterface<String, Value> symTable = state.getSymbolTable();
        HeapInterface heap = state.getHeapTable();

        Value varValue = null;
        varValue = symTable.lookup(varName);
        if (varValue == null) {
            throw new MyException("Var has not been declared!");
        }
        if (!(varValue instanceof RefrenceValue)) {
            throw new MyException("Var should be of ref type!");
        }

        RefrenceValue varRefValue = (RefrenceValue) varValue;
        int heapAddr = varRefValue.getAddress();
        if (heap.get(heapAddr) == null) {
            throw new MyException("Heap address is not valid!");
        }
        Value expValue = expression.eval(symTable, heap);
        if (! expValue.getType().equals(((RefrenceType) varRefValue.getType()).getInner())) {
            throw new MyException("Location pointed by heap address does not match the type of variable");
        }
        heap.update(heapAddr, expValue);
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typeCheck(typeEnv);
        Type typeVar = typeEnv.lookup(varName);
        if (typeVar.equals(new RefrenceType(typeExp))) {
            return typeEnv;
        }
        else {
            throw new MyException("write heap statement: right hand side and left hand side have different types!");
        }
    }
}
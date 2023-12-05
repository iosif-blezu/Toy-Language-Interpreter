package model.statement;

import model.ADT.HeapInterface;
import model.ADT.DictionaryInterface;
import model.MyException;
import model.ProgramState;
import model.expression.Expression;
import model.type.RefrenceType;
import model.type.Type;
import model.value.RefrenceValue;
import model.value.Value;

public class NewStatement implements InterfaceStatement{
    private String varName;
    private Expression expression;

    public NewStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    public String getVarName()
    {
        return varName;
    }

    public void setVarName(String varName)
    {
        this.varName = varName;
    }

    public Expression getExpression()
    {
        return expression;
    }

    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public String toString()
    {
        return "new(" + varName + "," + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        DictionaryInterface<String, Value> symTable = state.getSymbolTable();
        HeapInterface heap = state.getHeapTable();

        Value val = symTable.lookup(varName);
        if (val == null) {
            throw new MyException("Variable was not defined!");
        }
        if (!(val instanceof RefrenceValue)) {
            throw new MyException("Variable must be of RefType!");
        }

        Value expV = expression.eval(symTable, heap);
        Type locationType = ((RefrenceType) val.getType()).getInner();

        if (! (expV.getType().equals(locationType))) {
            throw new MyException("Reference location type does not match expression type!");
        }

        int heapAddr = heap.getFreeAddress();
        heap.put(heapAddr, expV);
        RefrenceValue refV = (RefrenceValue) val;
        RefrenceValue newRef = new RefrenceValue(heapAddr, ((RefrenceType) refV.getType()).getInner());
        symTable.update(varName, newRef);

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(new RefrenceType(typeExp))) {
            return typeEnv;
        }
        else {
            throw new MyException("new statement: right hand side and left hand side have different types!");
        }
    }
}
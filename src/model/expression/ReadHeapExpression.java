package model.expression;

import model.ADT.HeapInterface;
import model.ADT.DictionaryInterface;
import model.MyException;
import model.expression.Expression;
import model.type.RefrenceType;
import model.type.Type;
import model.value.RefrenceValue;
import model.value.Value;

public class ReadHeapExpression implements Expression {
    Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "ReadHeapExp(" +
                "exp=" + expression +
                ')';
    }

    @Override
    public Value eval(DictionaryInterface<String, Value> tbl, HeapInterface heap) throws MyException {
        Value v = expression.eval(tbl, heap);
        if (v instanceof RefrenceValue) {
            RefrenceValue refValue = (RefrenceValue) v;
            int addr = refValue.getAddress();
            if (heap.containsKey(addr)) {
                return heap.get(addr);
            }
            else {
                throw new MyException("location does not exist!");
            }
        }
        else {
            throw new MyException("expression must be RefValue");
        }
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        Type typ = expression.typeCheck(typeEnv);
        if (typ instanceof RefrenceType) {
            RefrenceType reft = (RefrenceType) typ;
            return reft.getInner();
        }
        else {
            throw new MyException("the rH argument is not a RefType!");
        }
    }
}
package model.expression;

import model.ADT.DictionaryInterface;
import model.ADT.HeapInterface;
import model.MyException;
import model.type.BoolType;
import model.type.Type;
import model.value.Value;

public class LogicExpression implements Expression
{
    Expression e1;
    Expression e2;
    public static final int AND = 1;
    public static final int OR = 2;

    int op; // 1-and, 2-or
    public LogicExpression(String stringOp, Expression e1, Expression e2)
    {
        this.e1 = e1;
        this.e2 = e2;
        switch(stringOp){
            case "&&" -> this.op = AND;
            case "||" -> this.op = OR;
        }
    }

    public Expression getE1()
    {
        return this.e1;
    }
    public Expression getE2()
    {
        return this.e2;
    }
    public int getOp()
    {
        return this.op;
    }
    public void setE1(Expression e1)
    {
        this.e1 = e1;
    }
    public void setE2(Expression e2)
    {
        this.e2 = e2;
    }
    public void setOp(int op)
    {
        this.op = op;
    }

    @Override
    public Value eval(DictionaryInterface<String, Value> tbl, HeapInterface heapTable) throws MyException {
        Value leftValue, rightValue;
        leftValue = e1.eval(tbl, heapTable);

        if (leftValue.getType().equals(new model.type.BoolType()))
        {
            rightValue = e2.eval(tbl, heapTable);

            if (rightValue.getType().equals(new model.type.BoolType()))
            {
                model.value.BoolValue boolValue1 = (model.value.BoolValue) leftValue;
                model.value.BoolValue boolValue2 = (model.value.BoolValue) rightValue;
                boolean leftBoolValue, rightBoolValue;
                leftBoolValue = boolValue1.getVal();
                rightBoolValue = boolValue2.getVal();

                if (op == 1) return new model.value.BoolValue(leftBoolValue && rightBoolValue);
                if (op == 2) return new model.value.BoolValue(leftBoolValue || rightBoolValue);
            } else
            {
                throw new MyException("Second operand is not a boolean");
            }
        }
        else
        {
            throw new MyException("First operand is not a boolean");
        }
        return null;
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typeCheck(typeEnv);
        typ2 = e2.typeCheck(typeEnv);
        if (typ1.equals(new BoolType()))
        {
            if (typ2.equals(new BoolType()))
            {
                return new BoolType();
            }
            else
            {
                throw new MyException("Second operand is not a boolean");
            }
        }
        else
        {
            throw new MyException("First operand is not a boolean");
        }
    }


}

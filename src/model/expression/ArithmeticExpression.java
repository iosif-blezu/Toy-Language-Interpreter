package model.expression;
import model.ADT.DictionaryInterface;
import model.ADT.HeapInterface;
import model.MyException;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class ArithmeticExpression implements Expression {
    private Expression e1;
    private Expression e2;
    public static final int ADD = 1;
    public static final int SUBTRACT = 2;
    public static final int MULTIPLY = 3;
    public static final int DIVIDE = 4;
    private int op; // 1-plus, 2-minus, 3-star, 4-divide
    public ArithmeticExpression(String stringOp, Expression e1, Expression e2)
    {
        this.e1 = e1;
        this.e2 = e2;
        switch(stringOp){
            case "+" -> this.op = ADD;
            case "-" -> this.op = SUBTRACT;
            case "*" -> this.op = MULTIPLY;
            case "/" -> this.op = DIVIDE;
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
    public String toString()
    {
        return "ArithmeticExp{"+
                "e1 = "+e1+
                ", e2 = "+e2+
                ", op = "+op+
                '}';
    }


    @Override
    public Value eval(DictionaryInterface<String, Value> symbolTable, HeapInterface heapTable) throws MyException
    {
        Value leftValue, rightValue;
        leftValue = e1.eval(symbolTable, heapTable);

        if (leftValue.getType().equals(new IntType()))
        {
            rightValue = e2.eval(symbolTable, heapTable);

            if (rightValue.getType().equals(new IntType()))
            {
                IntValue intValue1 = (IntValue) leftValue;
                IntValue intValue2 = (IntValue) rightValue;
                int leftIntValue, rightIntValue;
                leftIntValue = intValue1.getVal();
                rightIntValue = intValue2.getVal();

                if (op == ArithmeticExpression.ADD)
                {
                    return new IntValue(leftIntValue + rightIntValue);
                }
                if (op == ArithmeticExpression.SUBTRACT)
                {
                    return new IntValue(leftIntValue - rightIntValue);
                }
                if (op == ArithmeticExpression.MULTIPLY)
                {
                    return new IntValue(leftIntValue * rightIntValue);
                }
                if (op == ArithmeticExpression.DIVIDE)
                {
                    if (rightIntValue == 0)
                    {
                        throw new MyException("Division by zero");
                    }
                    else
                    {
                        return new IntValue(leftIntValue / rightIntValue);
                    }
                }
            }
            else
            {
                throw new MyException("Second operand is not an integer");
            }
        }
        else
        {
            throw new MyException("First operand is not an integer");
        }

        return null;
    }


    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new IntType()))
        {
            if (type2.equals(new IntType()))
            {
                return new IntType();
            }
            else
            {
                throw new MyException("second operand is not an integer");
            }
        }
        else
        {
            throw new MyException("first operand is not an integer");
        }
    }
}


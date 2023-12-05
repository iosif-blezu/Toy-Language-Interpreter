package model.expression;
import model.ADT.DictionaryInterface;
import model.ADT.HeapInterface;
import model.MyException;
import model.value.Value;
import model.type.Type;

public interface Expression {
    Value eval(DictionaryInterface<String,Value> symTbl, HeapInterface heapTable) throws MyException;
    Type typeCheck(DictionaryInterface<String, Type> typeEnv) throws MyException;

}

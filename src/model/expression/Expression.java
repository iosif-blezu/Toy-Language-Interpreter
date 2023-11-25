package model.expression;
import model.ADT.DictionaryInterface;
import model.MyException;
import model.value.Value;

public interface Expression {
    Value eval(DictionaryInterface<String,Value> symTbl) throws MyException;
}

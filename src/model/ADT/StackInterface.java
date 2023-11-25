package model.ADT;

import java.util.List;
public interface StackInterface<T> {
    T pop();
    void push(T valueToPush);
    boolean isEmpty();
    String toString();
    List<T> getAll();
    List<T> getReverese();

}

package model.ADT;

import java.util.List;
public interface ListInterface<V>{
    void add(V valueToAdd);
    List<V> getList();
    void clear();
}

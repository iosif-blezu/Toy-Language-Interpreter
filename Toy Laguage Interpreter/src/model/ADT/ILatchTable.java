package model.ADT;
import java.util.HashMap;



public interface ILatchTable {
    // getFree returns the first free location in the latch table
    int getFree();

    // put inserts a new entry in the latch table
    void put(int key, int value);


    // get returns the value associated with the given key
    int get(int key);

    // containsKey returns true if the given key is in the latch table
    boolean containsKey(int key);

    // update updates the value associated with the given key
    void update(int key, int value);

    // setContent sets the content of the latch table to the given HashMap
    void setContent(HashMap<Integer, Integer> latchTable);

    // getContent returns the content of the latch table
    HashMap<Integer, Integer> getContent();
}

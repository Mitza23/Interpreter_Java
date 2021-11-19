package Domain.ADT;

import Domain.Clonable;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T>{
    ArrayList<T> array;

    public MyList() {
        array = new ArrayList<T>();
    }

    @Override
    public void add(T v) {
        array.add(v);
    }

    @Override
    public T remove(int index) {
        return array.remove(index);
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("Out:\n");
        for (T e : array){
            r.append(e.toString()).append("\n");
        }
        return r.toString();
    }

    @Override
    public MyIList<T> clone() {
        MyList<T> clone = new MyList<>();
        for (T elem : array){
            if(elem instanceof Clonable) {
                clone.add(((Clonable<T>) elem).clone());
            }
        }
        return clone;
    }
}

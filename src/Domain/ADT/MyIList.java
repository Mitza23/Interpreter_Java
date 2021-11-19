package Domain.ADT;

import Domain.Clonable;

public interface MyIList<T> extends Clonable<MyIList<T>> {
    void add(T v);
    T remove(int index);
    int size();
}

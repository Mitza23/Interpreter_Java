package Domain.ADT;

import Domain.Clonable;
import Domain.Exceptions.MyException;

public interface MyIStack <T> extends Clonable<MyIStack<T>> {
    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    int getSize();
}

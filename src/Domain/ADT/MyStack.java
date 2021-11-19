package Domain.ADT;

import Domain.Clonable;
import Domain.Exceptions.MyException;

import java.util.Iterator;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }

    public MyStack(Object stack) {
        if(stack instanceof Stack) {
            this.stack = (Stack<T>) stack;
        }
    }

    @Override
    public T pop() throws MyException {
        if (stack.isEmpty()){
            throw new MyException("Stack is empty");
        }
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int getSize() {
        return stack.size();
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("ExeStack:\n");
        Stack<T> clone = (Stack<T>) stack.clone();
        while (!clone.isEmpty()){
            r.append("\t");
            r.append(clone.pop().toString()).append('\n');
        }
//        for(T val : stack){
//            r.append("\t");
//            r.append(val.toString());
//        }
        return r.toString();
    }

    @Override
    public MyIStack<T> clone() {
        MyStack<T> clone = new MyStack<T>();
        Iterator<T> it = stack.iterator();
        while (it.hasNext()){
            T elem = it.next();
            if(elem instanceof Clonable) {
                clone.push(((Clonable<T>) elem).clone());
            }
        }
        return clone;
    }
}

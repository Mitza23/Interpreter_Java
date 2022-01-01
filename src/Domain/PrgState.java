package Domain;

import Domain.ADT.*;
import Domain.Exceptions.MyException;
import Domain.Statements.IStmt;
import Domain.Types.Type;
import Domain.Values.Value;

import java.io.BufferedReader;


public class PrgState implements Clonable<PrgState> {
    static int prgCount = 0;
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<String, BufferedReader> fileTable;
    MyIHeap heap;
    int id;
    IStmt originalProgram; //optional field, but good to have

    public MyIHeap getHeap() {
        return heap;
    }

    public int getId() {
        return id;
    }

    public synchronized int getAndIncrementPrgCount() {
        prgCount++;
        return prgCount;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = getAndIncrementPrgCount();
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot, IStmt prg){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        originalProgram = prg;
//        originalProgram=deepCopy(prg);//recreate the entire original prg
        stk.push(prg);
    }

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, Value> dict, MyIList<Value> list) {
        exeStack = stack;
        symTable = dict;
        out = list;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out,
                    MyIDictionary<String, BufferedReader> fileTable, IStmt stmt) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        exeStack.push(stmt);
        this.heap = new MyHeap();
        stmt.typecheck(new MyDictionary<String, Type>());
    }

    public MyIStack<IStmt> getStk(){
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable(){
        return symTable;
    }

    public MyIList<Value> getOut(){
        return out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        r.append("ID: ")
                .append(id)
                .append("\n")
                .append(exeStack.toString())
                .append("\n")
                .append(symTable.toString())
                .append("\n")
                .append(out.toString())
                .append("\n")
                .append(fileTable.toString())
                .append("\n")
                .append(heap.toString());
        return r.toString();
    }


    @Override
    public PrgState clone() {
        MyIStack<IStmt> stack = exeStack.clone();
        MyIDictionary<String, Value> dict = symTable.clone();
        MyIList<Value> list = out.clone();
        PrgState clone = new PrgState(stack, dict, list);
        return clone;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.isEmpty()) {
            throw new MyException("prgstate stack is empty");
        }
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }
}

package Domain;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.ADT.MyIList;
import Domain.ADT.MyIStack;
import Domain.Statements.IStmt;
import Domain.Values.Value;

import java.io.BufferedReader;


public class PrgState implements Clonable<PrgState> {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<String, BufferedReader> fileTable;
    MyIHeap heap;
    IStmt originalProgram; //optional field, but good to have

    public MyIHeap getHeap() {
        return heap;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
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
        r.append(exeStack.toString());
        r.append("\n");
        r.append(symTable.toString());
        r.append("\n");
        r.append(out.toString());
        r.append("\n");
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
}

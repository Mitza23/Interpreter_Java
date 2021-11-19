package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.ADT.MyIStack;
import Domain.ADT.MyStack;
import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.PrgState;
import Domain.Values.RefValue;
import Domain.Values.Value;

public class HeapReadingStmt implements IStmt{
    Exp exp;

    @Override
    public IStmt clone() {
        return null;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if(exp.eval(symtbl) instanceof RefValue){
            int addr = ((RefValue) exp.eval(symtbl)).getAddr();
            Value v = heap.getValue(addr);
            if(v == null){
                throw new MyException("Address " + addr + " does not exist in the heap");
            }
            return state;
        }
        else
            throw new MyException("Expression is not a RefValue");
    }
}

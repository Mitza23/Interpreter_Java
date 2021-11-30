package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.PrgState;
import Domain.Values.RefValue;
import Domain.Values.Value;

public class WriteHeapStmt implements IStmt{

    String varName;
    Exp exp;

    public WriteHeapStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public IStmt clone() {
        return new WriteHeapStmt(varName, exp.clone());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
       if(symtbl.isDefined(varName)){
           Value v = symtbl.lookup(varName);
           if(v instanceof RefValue){
               int addr = ((RefValue) v).getAddr();
               MyIHeap heap = state.getHeap();
               if(heap.isDefined(addr)){
                   Value expEval = exp.eval(symtbl, heap);
                   if(expEval.getType().equals(((RefValue) v).getLocationType())){
                        heap.update(addr, expEval);
                        return null;
                   }
                   else
                       throw new MyException("Incompatible types " + expEval.getType() + " and " +
                               ((RefValue) v).getLocationType());
               } else
                   throw new MyException("Address " + addr + " is not referenced");
           } else
               throw new MyException("Variable " + varName + " it's not a RefValue");
       } else
           throw new MyException("Variable " + varName + " not defined");
    }

    @Override
    public String toString() {
        return "WriteHeapStmt(" +
                varName +
                " <- " + exp +
                ')';
    }
}

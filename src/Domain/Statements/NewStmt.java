package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.PrgState;
import Domain.Types.RefType;
import Domain.Types.Type;
import Domain.Values.RefValue;
import Domain.Values.Value;

public class NewStmt implements IStmt{
    String var_name;
    Exp exp;

    public NewStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public IStmt clone() {
        return new NewStmt(var_name, exp.clone());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if(symtbl.isDefined(var_name)){
            if(symtbl.lookup(var_name) instanceof RefValue) {
                Value val = exp.eval(symtbl, heap);
                if(val.getType().equals(((RefValue) symtbl.lookup(var_name)).getLocationType())){
                    int addr = state.getHeap().addEntry(val);
                    symtbl.update(var_name, new RefValue(addr, val.getType()));
                    return null;
                } else
                    throw new MyException("Incompatible types: " + val.getType() + " and " +
                            ((RefValue) symtbl.lookup(var_name)).getLocationType());
            } else
                throw new MyException(var_name + "is not a refference");
        } else
            throw new MyException(var_name + "is not defined");
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(var_name);
        Type expType = exp.typecheck(typeEnv);
        if (varType.equals(new RefType(expType)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types: "
                    + varType + " != " + expType);
    }

    @Override
    public String toString() {
        return "NewStmt(" + var_name + " : " + exp + ')';
    }
}

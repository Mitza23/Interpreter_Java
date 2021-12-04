package Domain.Expressions;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exceptions.MyException;
import Domain.Types.Type;
import Domain.Values.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String v) {
        id = v;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        if (tbl.isDefined(id))
            return tbl.lookup(id);
        else
            throw new MyException("Variable " + id + " not defined");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Exp clone() {
        return new VarExp(id);
    }
}

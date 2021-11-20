package Domain.Expressions;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exceptions.MyException;
import Domain.Values.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String v) {
        id = v;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        if(tbl.isDefined(id))
            return tbl.lookup(id);
        else
            throw new MyException("Variable " + id + " not defined");
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

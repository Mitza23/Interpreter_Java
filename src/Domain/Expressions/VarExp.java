package Domain.Expressions;

import Domain.ADT.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Values.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String v) {
        id = v;
    }

    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        return tbl.lookup(id);
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

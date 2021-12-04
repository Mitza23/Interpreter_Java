package Domain.Expressions;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exceptions.MyException;
import Domain.Types.Type;
import Domain.Values.Value;

public class ValueExp implements Exp{
    Value e;

    public ValueExp(Value v) {
        e = v;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Exp clone() {
        return new ValueExp(e.clone());
    }
}

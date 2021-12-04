package Domain.Expressions;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Clonable;
import Domain.Exceptions.MyException;
import Domain.Types.Type;
import Domain.Values.Value;

public interface Exp extends Clonable<Exp> {
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException;

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}

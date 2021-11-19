package Domain.Statements;

import Domain.ADT.MyIStack;
import Domain.Exceptions.MyException;
import Domain.PrgState;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;

    public CompStmt(IStmt v1, IStmt v2) {
        first = v1;
        second = v2;
    }


    @Override
    public String toString() {
        return "("+first.toString() + ";" + second.toString()+")";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        stk.push(second);
        stk.push(first);
        return state;
    }

    @Override
    public IStmt clone() {
        return new CompStmt(first.clone(), second.clone());
    }
}

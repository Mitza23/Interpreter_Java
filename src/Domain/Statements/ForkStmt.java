package Domain.Statements;

import Domain.ADT.MyStack;
import Domain.Exceptions.MyException;
import Domain.PrgState;

public class ForkStmt implements IStmt {
    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public IStmt clone() {
        return null;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyStack<IStmt> stack = new MyStack<IStmt>();
        stack.push(stmt);
        return new PrgState(stack, state.getSymTable().clone(), state.getOut(), state.getFileTable(), state.getHeap());
    }

    @Override
    public String toString() {
        return "ForkStmt(" + stmt + ')';
    }
}

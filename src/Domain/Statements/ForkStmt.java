package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyStack;
import Domain.Exceptions.MyException;
import Domain.PrgState;
import Domain.Types.Type;

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
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "ForkStmt(" + stmt + ')';
    }
}

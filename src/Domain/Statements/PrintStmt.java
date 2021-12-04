package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.PrgState;
import Domain.Types.Type;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp v) {
        exp = v;
    }

    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        state.getOut().add(exp.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    public IStmt clone() {
        return new PrintStmt(exp);
    }
}

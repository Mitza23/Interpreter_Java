package Domain.Statements;

import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.Expressions.VarExp;
import Domain.PrgState;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp v) {
        exp = v;
    }

    public String toString(){
        return "print(" + exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        state.getOut().add(exp.eval(state.getSymTable()));
        return state;
    }

    public IStmt clone(){
        return new PrintStmt(exp);
    }
}

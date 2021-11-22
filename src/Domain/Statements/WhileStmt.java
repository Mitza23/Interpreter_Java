package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.ADT.MyIStack;
import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.PrgState;
import Domain.Types.BoolType;
import Domain.Values.BoolValue;
import Domain.Values.Value;

public class WhileStmt implements IStmt {
    Exp exp;
    IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public IStmt clone() {
        return new WhileStmt(exp.clone(), stmt.clone());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIStack<IStmt> stack = state.getStk();
        MyIHeap heap = state.getHeap();
        if (exp.eval(symtbl, heap).getType().equals(new BoolType())) {
            boolean ok = ((BoolValue) exp.eval(symtbl, heap)).getVal();
            if (ok) {
                stack.push(this.clone());
                stack.push(stmt);
            }
        } else
            throw new MyException(exp + " must be evaluated to a BoolValue");
        return state;
    }

    @Override
    public String toString() {
        return "while(" + exp + "){ " + stmt + "}";
    }
}

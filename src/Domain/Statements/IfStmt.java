package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.PrgState;
import Domain.Types.BoolType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.Value;

public class IfStmt implements IStmt{
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {exp=e; thenS=t;elseS=el;}

    @Override
    public String toString() {
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString()+")ELSE("+elseS.toString()+"))";
    }

    public PrgState execute(PrgState state) throws MyException {
        Value v= exp.eval(state.getSymTable(), state.getHeap());
        if(v instanceof BoolValue){
            boolean cond = ((BoolValue) v).getVal();
            if (cond) {
                state.getStk().push(thenS);
            } else {
                state.getStk().push(elseS);
            }
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        } else
            throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public IStmt clone() {
        return new IfStmt(exp.clone(), thenS.clone(), elseS.clone());
    }
}

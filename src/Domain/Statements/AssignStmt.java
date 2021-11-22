package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.PrgState;
import Domain.Types.Type;
import Domain.Values.Value;

public class AssignStmt implements IStmt{
    String id;
    Exp exp;

    public AssignStmt(String v, Exp valueExp) {
        id = v;
        exp = valueExp;
    }

    public String toString(){
        return id + " = " + exp.toString() + ";";

    }
    public PrgState execute(PrgState state) throws MyException{
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String, Value> symTbl= state.getSymTable();

        if(symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl, state.getHeap());
            Type typId = (symTbl.lookup(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else {
                System.out.println(id + " supports " + typId);
                System.out.println(exp + " is " + val.getType());
                throw new MyException("declared type of variable " + id + " and type of the assigned expression " + exp
                        + " do not match");
            }
        }
        else throw new MyException("the used variable" +id + " was not declared before");
            return state;
    }

    @Override
    public IStmt clone() {
        return new AssignStmt(id, exp.clone());
    }
}

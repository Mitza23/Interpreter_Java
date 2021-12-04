package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.Clonable;
import Domain.Exceptions.MyException;
import Domain.PrgState;
import Domain.Types.Type;

public interface IStmt extends Clonable<IStmt> {
    public PrgState execute(PrgState state) throws MyException;
    //which is the execution method for a statement.

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}

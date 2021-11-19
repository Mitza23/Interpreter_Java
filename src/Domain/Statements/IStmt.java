package Domain.Statements;

import Domain.Clonable;
import Domain.Exceptions.MyException;
import Domain.PrgState;

import java.io.IOException;

public interface IStmt extends Clonable<IStmt> {
    public PrgState execute(PrgState state) throws MyException;
    //which is the execution method for a statement.
}

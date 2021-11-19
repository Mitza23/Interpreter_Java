package Domain.Statements;

import Domain.Exceptions.MyException;
import Domain.PrgState;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public IStmt clone() {
        return new NopStmt();
    }
}

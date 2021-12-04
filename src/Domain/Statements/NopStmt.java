package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.PrgState;
import Domain.Types.Type;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt clone() {
        return new NopStmt();
    }
}

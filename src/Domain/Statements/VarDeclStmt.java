package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.PrgState;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.Value;

public class VarDeclStmt  implements IStmt{
    String name;
    Type typ;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        typ = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        if (symtbl.isDefined(name)){
            throw new MyException("Symbol already defined");
        }
        symtbl.put(name, typ.getDefault());
        return state;
    }

    @Override
    public String toString() {
        return "" + typ + " " + name + ";";
    }

    public IStmt clone(){
        return new VarDeclStmt(name, typ);
    }
}

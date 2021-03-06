package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.PrgState;
import Domain.Types.StringType;
import Domain.Types.Type;
import Domain.Values.StringValue;
import Domain.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt{
    Exp exp;

    public OpenRFileStmt(Exp e) {
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTbl = state.getFileTable();
        if(exp.eval(symTbl, state.getHeap())instanceof StringValue){
            String name = ((StringValue) exp.eval(symTbl, state.getHeap())).getVal();
            if(!fileTbl.isDefined(name)){
                try {
                    BufferedReader buff = new BufferedReader(new FileReader(name));
                    fileTbl.put(name, buff);
                }
                catch (FileNotFoundException e){
                    throw new MyException("File not found: " + e.getMessage());
                }
            } else
                throw new MyException("File " + name + " already opened");
        } else
            throw new MyException("File name must be a string");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = exp.typecheck(typeEnv);
        if (expType.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("Expression does not evaluate to a string: " + expType);
        }
    }

    @Override
    public IStmt clone() {
        return new OpenRFileStmt(exp.clone());
    }

    @Override
    public String toString() {
        return "openFile(" + exp + ')';
    }
}

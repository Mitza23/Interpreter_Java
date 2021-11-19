package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.PrgState;
import Domain.Values.StringValue;
import Domain.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    Exp exp;

    public CloseRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public IStmt clone() {
        return new CloseRFileStmt(exp.clone());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, BufferedReader> fileTbl = state.getFileTable();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if(!(exp.eval(symTbl) instanceof StringValue)){
            throw new MyException("The expression must evaluate to a string");
        }
        else{
            String fileName = ((StringValue) exp.eval(symTbl)).getVal();
            if(!fileTbl.isDefined(fileName)){
                throw new MyException("File is not defined");
            }
            else{
                BufferedReader buff = fileTbl.lookup(fileName);
                try {
                    buff.close();
                }
                catch (IOException e){
                    throw new MyException("Error closing the file");
                }
                fileTbl.remove(fileName);
            }
        }
        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ')';
    }
}

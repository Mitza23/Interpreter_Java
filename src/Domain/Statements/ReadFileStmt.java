package Domain.Statements;

import Domain.ADT.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Expressions.Exp;
import Domain.PrgState;
import Domain.Types.IntType;
import Domain.Values.IntValue;
import Domain.Values.StringValue;
import Domain.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    Exp exp;
    String var_name;

    public ReadFileStmt(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, BufferedReader> fileTbl = state.getFileTable();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if(symTbl.isDefined(var_name)){
            if(symTbl.lookup(var_name).getType().equals(new IntType())){
                if(exp.eval(symTbl, state.getHeap()) instanceof StringValue){
                    String name = ((StringValue) exp.eval(symTbl, state.getHeap())).getVal();
                    if(!fileTbl.isDefined(name))
                        throw new MyException("File not opened");
                    BufferedReader buff = fileTbl.lookup(name);
                    try {
                        String txt = buff.readLine();
                        int val;
                        if(txt == null){
                            val = 0;
                        }
                        else{
                            val = Integer.parseInt(txt);
                        }
                        symTbl.update(var_name, new IntValue(val));
                    }
                    catch (IOException e){
                        throw new MyException(e.toString());
                    }
                }
                else{
                    throw new MyException("File name must be a string");
                }
            }
            else{
                throw new MyException(var_name + "does not store an integer value");
            }
        }
        else{
            throw new MyException(var_name + "is not defined");
        }
        return state;
    }

    @Override
    public IStmt clone() {
        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + var_name + ')';
    }
}

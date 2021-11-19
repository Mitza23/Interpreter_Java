package Domain.Expressions;

import Domain.ADT.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.IntType;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.Value;

import java.util.Objects;

public class RelationalExp implements Exp{
    Exp e1;
    Exp e2;
    String op;

    public RelationalExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Exp clone() {
        return new RelationalExp(e1.clone(), e2.clone(), op);
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1, v2;
        v1= e1.eval(tbl);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(Objects.equals(op, "<")){
                    return new BoolValue(n1 < n2);
                }
                if(Objects.equals(op, "<=")){
                    return new BoolValue(n1 <= n2);
                }
                if(Objects.equals(op, "==")){
                    return new BoolValue(n1 == n2);
                }
                if(Objects.equals(op, "!=")){
                    return new BoolValue(n1 != n2);
                }
                if(Objects.equals(op, ">")){
                    return new BoolValue(n1 > n2);
                }
                if(Objects.equals(op, ">=")){
                    return new BoolValue(n1 >= n2);
                }
                throw new MyException("Invalid operator");
            }
            else
                throw new MyException("The second expression does not evaluate to an int");
        }
        else
            throw new MyException("The first expression does not evaluate to an int");
    }
}

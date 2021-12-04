package Domain.Expressions;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exceptions.MyException;
import Domain.Types.BoolType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.Value;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    int op; // 1 - &, 2 - |, 3 - ^

    public LogicExp(Exp e1, Exp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new BoolType())){
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new BoolType())){
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean n1 = b1.getVal();
                boolean n2 = b2.getVal();
                switch (op){
                    case 1:
                        return new BoolValue(n1 & n2);
                    case 2:
                        return new BoolValue(n1 | n2);
                    case 3:
                        return new BoolValue(n1 ^ n2);
                }
            } else
                throw new MyException("Second operator is not boolean");
        } else
            throw new MyException("First operator is not boolean");
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new MyException("second operand is not an boolean");
            }
        } else {
            throw new MyException("first operand is not an boolean");
        }
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public Exp clone() {
        return new LogicExp(e1.clone(), e2.clone(), op);
    }
}

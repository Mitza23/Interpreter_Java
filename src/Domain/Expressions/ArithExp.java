package Domain.Expressions;

import Domain.ADT.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.IntType;
import Domain.Values.IntValue;
import Domain.Values.Value;

public class ArithExp implements Exp{
    Exp e1;
    Exp e2;
    char op;

    public ArithExp(char op, Exp e1, Exp e2) throws MyException {
        this.e1 = e1;
        this.e2 = e2;
//        System.out.println("Operator: " + op);
        if("+/*-".indexOf(op) == -1) {
                throw new MyException("Invalid operator!");
        }
    }

    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1, v2;
        v1= e1.eval(tbl);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
//                System.out.println("***** " + n1 + op +n2);
                switch (op){
                    case '+':
                        return new IntValue(n1 + n2);
                    case '-':
                        return new IntValue(n1 - n2);
                    case '*':
                        return new IntValue(n1 * n2);
                    case '/':
                        if (n2 == 0) throw new MyException("division by zero");
                        else return new IntValue(n1 / n2);
                    default:
                        throw new MyException("Invalid operator");
                }
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public Exp clone() {
        try {
            return new ArithExp(op, e1.clone(), e2.clone());
        } catch (MyException e) {
            return new Exp() {
                @Override
                public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
                    return null;
                }

                @Override
                public Exp clone() {
                    return null;
                }
            };
        }
    }
}

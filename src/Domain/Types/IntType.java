package Domain.Types;

import Domain.Values.IntValue;
import Domain.Values.Value;

public class IntType implements Type{
    public boolean equals(Object another){
        return another instanceof IntType;
    }
    public String toString() { return "int";}

    @Override
    public Value getDefault() {
        return new IntValue(0);
    }

    @Override
    public Type clone() {
        return new IntType();
    }
}

package Domain.Types;

import Domain.Values.BoolValue;
import Domain.Values.Value;

public class BoolType implements Type{
    public boolean equals(Object another){
        return another instanceof BoolType;
    }
    public String toString() {
        return "bool";
    }

    @Override
    public Value getDefault() {
        return new BoolValue(false);
    }

    @Override
    public Type clone(){
        return new BoolType();
    }
}

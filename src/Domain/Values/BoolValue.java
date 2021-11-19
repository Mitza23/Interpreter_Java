package Domain.Values;

import Domain.Types.BoolType;
import Domain.Types.Type;

public class BoolValue implements Value{

    boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    @Override
    public Type getType() {

        return new BoolType();
    }

    @Override
    public String toString() {
        return "bool: " + value;
    }

    public boolean getVal() {
        return value;
    }

    @Override
    public Value clone(){
        return new BoolValue(value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BoolValue){
            return ((BoolValue) obj).getVal() == value;
        }
        return false;
    }
}

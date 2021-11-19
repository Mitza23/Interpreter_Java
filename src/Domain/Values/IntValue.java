package Domain.Values;

import Domain.Types.IntType;
import Domain.Types.Type;

public class IntValue implements Value{
    int value;
    public IntValue(int v){
        value =v;
    }

    public int getVal() {
        return value;
    }
    public String toString() {
        return "int: "+ value;
    }
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value clone(){
        return new IntValue(value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof IntValue){
            return ((IntValue) obj).getVal() == value;
        }
        return false;
    }
}

package Domain.Types;

import Domain.Values.RefValue;
import Domain.Values.Value;

public class RefType implements Type{
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another) {
        if(another instanceof RefType){
            return inner.equals(((RefType) another).getInner());
        }
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref(" +inner.toString()+")";
    }


    @Override
    public Type clone() {
        return new RefType(inner.clone());
    }

    @Override
    public Value getDefault() {
        return new RefValue(0,inner);
    }
}

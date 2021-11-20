package Domain.ADT;

import Domain.Clonable;
import Domain.Types.Type;
import Domain.Values.Value;

import java.util.HashMap;

public interface MyIHeap extends Clonable<MyIHeap> {
    int nextFree();
    int addEntry(Value value);
    Value getValue(int address);
    boolean isDefined(int address);
    void update(int address, Value value);
    void setContent(HashMap<Integer, Value> map);
}

package Domain.ADT;

import Domain.Clonable;
import Domain.Values.Value;

import java.util.Map;

public interface MyIHeap extends Clonable<MyIHeap> {
    int nextFree();

    int addEntry(Value value);

    Value getValue(int address);

    boolean isDefined(int address);

    void update(int address, Value value);

    void setContent(Map<Integer, Value> map);

    Map<Integer, Value> getContent();
}

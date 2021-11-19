package Domain.ADT;

import Domain.Clonable;
import Domain.Types.Type;
import Domain.Values.Value;

public interface MyIHeap extends Clonable<MyIHeap> {
    int nextFree();
    int addEntry(Value type);
    Value getValue(int address);
}

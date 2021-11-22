package Domain.ADT;

import Domain.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{
    HashMap<Integer, Value> map;
    int position;

    public MyHeap() {
        map = new HashMap<Integer, Value>();
        this.position = 1;
    }

    public MyHeap(HashMap<Integer, Value> map, int position) {
        this.map = map;
        this.position = position;
    }

    @Override
    public int nextFree() {
        return position;
    }

    @Override
    public int addEntry(Value value) {
        int p = nextFree();
        map.put(p, value);
        position ++;
        return p;
    }

    @Override
    public Value getValue(int address) {
        return map.get(address);
    }

    @Override
    public boolean isDefined(int address) {
        return map.get(address) != null;
    }

    @Override
    public MyIHeap clone() {
        HashMap<Integer, Value> clone = new HashMap<Integer, Value>();
        for(Map.Entry<Integer, Value> entry : map.entrySet()){
            clone.put(entry.getKey(), entry.getValue().clone());
        }
        return new MyHeap(clone, position);
    }

    public void update(int address, Value value){
        map.replace(address, value);
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("Heap:\n");
        for (HashMap.Entry<Integer, Value> p : map.entrySet()) {
            r.append(p.getKey().toString() + " --> " + p.getValue().toString() + "\n");
        }
        return r.toString();
    }

    @Override
    public void setContent(HashMap<Integer, Value> map) {
        this.map = map;
    }

    @Override
    public HashMap<Integer, Value> getContent() {
        return map;
    }
}

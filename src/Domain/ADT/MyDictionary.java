package Domain.ADT;

import Domain.Clonable;
import Domain.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    HashMap<K, V> map;

    public MyDictionary() {
        map = new HashMap<K, V>();
    }

    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public void update(K key, V value) {
        map.replace(key, value);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("SymTbl:\n");
        for (HashMap.Entry<K, V> p : map.entrySet()){
            r.append(p.getKey().toString() + " --> " + p.getValue().toString() + "\n");
        }
        return r.toString();
    }

    @Override
    public MyIDictionary<K, V> clone() {
        MyDictionary<K, V> clone = new MyDictionary<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (value instanceof Clonable){
                value = ((Clonable<V>) value).clone();
            }
            clone.put(key, value);
        }
        return clone;
    }
}

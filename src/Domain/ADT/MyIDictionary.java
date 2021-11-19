package Domain.ADT;

import Domain.Clonable;

public interface MyIDictionary<K,V> extends Clonable<MyIDictionary<K, V>> {
    V lookup(K key);

    void update(K key, V value);

    void put(K key, V value);

    boolean isDefined(K key);

    void remove(K key);
}

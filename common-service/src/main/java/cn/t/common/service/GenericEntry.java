package cn.t.common.service;

/**
 * GenericEntry
 * @author <a href="mailto:yangjian@ifenxi.com">研发部-杨建</a>
 * @version V1.0
 * @since 2021-07-07 21:33
 **/
public class GenericEntry<K, V> {
    private K key;
    private V value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public GenericEntry() {
    }

    public GenericEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "GenericEntry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}

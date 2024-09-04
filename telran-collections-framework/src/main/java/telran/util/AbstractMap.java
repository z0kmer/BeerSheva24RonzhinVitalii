package telran.util;
@SuppressWarnings("unchecked")
public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected Set<Entry<K, V>> set;
    protected abstract Set<K> getEmptyKeySet();
    @Override
    public V get(Object key) {
        
        Entry<K, V> pattern = new Entry<>((K)key, null);
        Entry<K,V> entry = set.get(pattern);
        V res = null;
        if (entry != null) {
            res = entry.getValue();
        }
        return res;
    }

    @Override
    public V put(K key, V value) {
        Entry<K, V> newEntry = new Entry<>(key, value);
        Entry<K, V> existingEntry = set.get(newEntry);
        V oldValue = null;
        if (existingEntry != null) {
            oldValue = existingEntry.getValue();
            existingEntry.setValue(value);
        } else {
            set.add(newEntry);
        }
        return oldValue;
    }

    @Override
    public boolean containsKey(Object key) {
        Entry<K, V> pattern = new Entry<>((K) key, null);
        return set.contains(pattern);
    }

    @Override
    public boolean containsValue(Object value) {
        return set.stream().anyMatch(entry -> entry.getValue().equals(value));
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = getEmptyKeySet();
        for (Entry<K, V> entry : set) {
            keySet.add(entry.getKey());
        }
        return keySet;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Entry<K, V> entry : set) {
            values.add(entry.getValue());
        }
        return values;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

}

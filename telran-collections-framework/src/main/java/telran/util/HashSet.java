package telran.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class HashSet<T> implements Set<T> {
    private static final int DEFAULT_HASH_TABLE_LENGTH = 16;
    private static final float DEFAULT_FACTOR = 0.75f;
    List<T>[] hashTable;
    float factor;
    int size;

    private class HashSetIterator implements Iterator<T> {
        //Hint:
        Iterator<T> currentIterator;
        Iterator<T> prevIterator;
        int indexIterator;

        public HashSetIterator() {
            indexIterator = -1;
            moveToNextNonEmptyBucket();
        }

        private void moveToNextNonEmptyBucket() {
            currentIterator = null;
            while (++indexIterator < hashTable.length) {
                List<T> list = hashTable[indexIterator];
                if (list != null && !list.isEmpty()) {
                    currentIterator = list.iterator();
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            if (currentIterator == null) {
                return false;
            }
            if (currentIterator.hasNext()) {
                return true;
            }
            moveToNextNonEmptyBucket();
            return currentIterator != null && currentIterator.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements");
            }
            return currentIterator.next();
        }

        @Override
        public void remove() {
            if (currentIterator == null) {
                throw new IllegalStateException("No element to remove");
            }
            currentIterator.remove();
            size--;
        }
    }

    public HashSet(int hashTableLength, float factor) {
        hashTable = new List[hashTableLength];
        this.factor = factor;
    }

    public HashSet() {
        this(DEFAULT_HASH_TABLE_LENGTH, DEFAULT_FACTOR);
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            if (size >= hashTable.length * factor) {
                hashTableReallocation();
            }
            addObjInHashTable(obj, hashTable);
            size++;
        }
        return res;
    }

    private void addObjInHashTable(T obj, List<T>[] table) {
        int index = getIndex(obj, table.length);
        List<T> list = table[index];
        if (list == null) {
            list = new ArrayList<>(3);
            table[index] = list;
        }
        list.add(obj);
    }

    private int getIndex(T obj, int length) {
        int hashCode = obj.hashCode();
        return Math.abs(hashCode % length);
    }

    private void hashTableReallocation() {
        List<T>[] tempTable = new List[hashTable.length * 2];
        for (List<T> list : hashTable) {
            if (list != null) {
                list.forEach(obj -> addObjInHashTable(obj, tempTable));
            }
        }
        hashTable = tempTable;
    }

    @Override
    public boolean remove(T pattern) {
        int index = getIndex(pattern, hashTable.length);
        List<T> list = hashTable[index];
        if (list != null && list.remove(pattern)) {
            size--;
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T pattern) {
        int index = getIndex(pattern, hashTable.length);
        List<T> list = hashTable[index];
        return list != null && list.contains(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new HashSetIterator();
    }

    @Override
    public T get(Object pattern) {
        int index = getIndex((T) pattern, hashTable.length);
        List<T> list = hashTable[index];
        if (list != null) {
            for (T element : list) {
                if (element.equals(pattern)) {
                    return element;
                }
            }
        }
        return null;
    }
}
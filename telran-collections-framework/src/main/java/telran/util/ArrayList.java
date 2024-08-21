package telran.util;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private Object [] array;
    private int size;

    public ArrayList(int capacity){
        array = new Object[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public boolean add(T obj) {
        if(size == array.length){
            rellocate();
        }
        array[size++] = obj;
        return true;
    }

    private void rellocate() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
    int index = indexOf(pattern);
    if (index != -1) {
        remove(index);
        res = true;
    }
    return res;
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
        return indexOf(pattern) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }
    
    private class MyIterator implements Iterator<T> {
        private int currentIndex = 0;
    
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }
    
        @Override
        public T next() {
            return (T) array[currentIndex++];
        }
    }


    @Override
    public void add(int index, T obj) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (size == array.length) {
            rellocate();
        }

        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = obj;
        size++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removedElement = (T) array[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(array, index + 1, array, index, numMoved);
        }
        array[--size] = null;
        return removedElement;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) array[index];
    }

    @Override
    public int indexOf(T pattern) {
        return findIndex(pattern, 0, size, 1);
    }

    @Override
    public int lastIndexOf(T pattern) {
        return findIndex(pattern, size - 1, -1, -1);
    }

    private int findIndex(T pattern, int start, int end, int step) {
        int i = start;
        int resIndex = -1;
        while (i != end && resIndex == -1) {
            if ((pattern == null && array[i] == null) || (pattern != null && pattern.equals(array[i]))) {
                resIndex = i;
            }
            i += step;
        }
        return resIndex;
    }

}

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 8f30c783dc7088979be7b15044264186a756433e
package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
<<<<<<< HEAD
import java.util.Objects;
import java.util.function.Predicate;
@SuppressWarnings("unchecked")
public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private Object [] array;
    private int size = 0;
    
    public ArrayList(int capacity) {
        array = new Object[capacity];
    }
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }
=======
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 1_000_000;
    private Object[] array;
    private int size = 0;

    public ArrayList(int capacity) {
        array = new Object[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

>>>>>>> 8f30c783dc7088979be7b15044264186a756433e
    @Override
    public boolean add(T obj) {
        reallocationIfNeeded();
        array[size++] = obj;
        return true;
    }
    private void reallocationIfNeeded() {
        if(size == array.length) {
            reallocate();
        }
    }
<<<<<<< HEAD

    private void reallocate() {
          array = Arrays.copyOf(array, array.length * 2);
    }
   

    @Override
    public int size() {
       return size;
=======
    private void reallocate() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    @Override
    public int size() {
        return size;
>>>>>>> 8f30c783dc7088979be7b15044264186a756433e
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

<<<<<<< HEAD
   

=======
>>>>>>> 8f30c783dc7088979be7b15044264186a756433e
    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

<<<<<<< HEAD
=======
    private class ArrayListIterator implements Iterator<T> {
        int currentIndex = 0;
        private boolean flNext = false;
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            flNext = true;
            return (T) array[currentIndex++];
        }
        @Override
        public void remove() {
            if(!flNext) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(--currentIndex);
            flNext = false;
        }
    }

>>>>>>> 8f30c783dc7088979be7b15044264186a756433e
    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);
        reallocationIfNeeded();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = obj;
        size++;
    }

<<<<<<< HEAD
   
=======
>>>>>>> 8f30c783dc7088979be7b15044264186a756433e
    @Override
    public T remove(int index) {
        checkIndex(index, false);
        T res = (T)array[index];
        size--;
        System.arraycopy(array, index + 1, array, index, size - index);
        array[size] = null;
        return res;
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return (T) array[index];
    }

    @Override
    public int indexOf(T pattern) {
<<<<<<< HEAD
        int index = 0;
        while(index < size && !Objects.equals(array[index], pattern)) {
            index++;
        }
        return index == size ? -1 : index;
=======
        return findIndex(pattern, 0, size, 1);
>>>>>>> 8f30c783dc7088979be7b15044264186a756433e
    }

    @Override
    public int lastIndexOf(T pattern) {
<<<<<<< HEAD
        int index = size - 1;
        while(index >= 0 && !Objects.equals(array[index], pattern)) {
            index--;
        }
        return index;
    }
    @Override
    public boolean removeIf(Predicate<T> predicate) {
       int indexTo = 0;
       Predicate<T> negPred = predicate.negate(); //not to apply "!" operator at each iteration
       for(int currentIndex = 0; currentIndex < size; currentIndex++) {
        T current = (T)array[currentIndex];
            if(negPred.test(current)) {
                array[indexTo++] = current;
            }
       }
       Arrays.fill(array,indexTo, size, null);
       boolean res = indexTo < size;
       size = indexTo;
       return res;
    }

    private class ArrayListIterator implements Iterator<T> {
        int currentIndex = 0;
        private boolean flNext = false;
        @Override
        public boolean hasNext() {
           
           return currentIndex < size;
        }

 
        @Override
        public T next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            flNext = true;
            return (T) array[currentIndex++];
        }
        @Override
        public void remove() {
            if(!flNext) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(--currentIndex);
            flNext = false;
        }

        
    }

=======
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

    @Override
    public boolean removeIf(Predicate<T> predicate) {
        int indexTo = 0;
        Predicate<T> negPred = predicate.negate(); //not to apply "!" operator at each iteration
        for(int currentIndex = 0; currentIndex < size; currentIndex++) {
            T current = (T)array[currentIndex];
                if(negPred.test(current)) {
                    array[indexTo++] = current;
                }
        }
        Arrays.fill(array,indexTo, size, null);
        boolean res = indexTo < size;
        size = indexTo;
        return res;
    }
=======
package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 1_000_000;
    private Object[] array;
    private int size = 0;

    public ArrayList(int capacity) {
        array = new Object[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public boolean add(T obj) {
        reallocationIfNeeded();
        array[size++] = obj;
        return true;
    }
    private void reallocationIfNeeded() {
        if(size == array.length) {
            reallocate();
        }
    }
    private void reallocate() {
        array = Arrays.copyOf(array, array.length * 2);
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
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        int currentIndex = 0;
        private boolean flNext = false;
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            flNext = true;
            return (T) array[currentIndex++];
        }
        @Override
        public void remove() {
            if(!flNext) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(--currentIndex);
            flNext = false;
        }
    }

    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);
        reallocationIfNeeded();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = obj;
        size++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        T res = (T)array[index];
        size--;
        System.arraycopy(array, index + 1, array, index, size - index);
        array[size] = null;
        return res;
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
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

    @Override
    public boolean removeIf(Predicate<T> predicate) {
        int indexTo = 0;
        Predicate<T> negPred = predicate.negate(); //not to apply "!" operator at each iteration
        for(int currentIndex = 0; currentIndex < size; currentIndex++) {
            T current = (T)array[currentIndex];
                if(negPred.test(current)) {
                    array[indexTo++] = current;
                }
        }
        Arrays.fill(array,indexTo, size, null);
        boolean res = indexTo < size;
        size = indexTo;
        return res;
    }
>>>>>>> origin/main
>>>>>>> 8f30c783dc7088979be7b15044264186a756433e
}
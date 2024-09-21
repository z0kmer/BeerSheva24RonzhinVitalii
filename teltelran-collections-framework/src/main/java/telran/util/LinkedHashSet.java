package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedHashSet<T> implements Set<T> {
    private LinkedList<T> list = new LinkedList<>();
    private HashMap<T, LinkedList.Node<T>> map = new HashMap<>();

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            LinkedList.Node<T> node = new LinkedList.Node<>(obj);
            list.addNode(node, list.size());
            map.put(obj, node);
            res = true;
        }
        return res;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        LinkedList.Node<T> node = map.remove(pattern);
        if (node != null) {
            list.removeNode(node);
            res = true;
        }
        return res;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(T pattern) {
        return map.containsKey(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedHashSetIterator();
    }

    @Override
    public T get(Object pattern) {
        LinkedList.Node<T> node = map.get(pattern);
        return node == null ? null : node.obj;
    }

    private class LinkedHashSetIterator implements Iterator<T> {
        private LinkedList.Node<T> current = list.head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T obj = current.obj;
            current = current.next;
            return obj;
        }

        @Override
        public void remove() {
            if (current == null || current.prev == null) {
                throw new IllegalStateException();
            }
            LinkedList.Node<T> nodeToRemove = current.prev;
            current = current.next;
            LinkedHashSet.this.remove(nodeToRemove.obj);
        }
    }
}
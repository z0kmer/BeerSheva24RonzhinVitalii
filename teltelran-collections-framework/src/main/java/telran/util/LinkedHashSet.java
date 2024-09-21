<<<<<<< HEAD
package telran.util;

import java.util.Iterator;

import telran.util.LinkedList.Node;

public class LinkedHashSet<T> implements Set<T> {
    private LinkedList<T> list = new LinkedList<>();
    HashMap<T, Node<T>> map = new HashMap<>();

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            Node<T> node = new Node<>(obj);
            list.addNode(node, list.size());
            map.put(obj, node);
            res = true;

        }

        return res;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        Node<T> toBeRemovedNode = map.remove(pattern);
        if (toBeRemovedNode != null) {
            res = true;
            list.removeNode(toBeRemovedNode);
        }
        return res;

    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
       return list.size() == 0;
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
        T res = null;
        Node<T> node = map.get(pattern);
        if (node != null) {
            res = node.obj;
        }
        return res;
    }
    private class LinkedHashSetIterator implements Iterator<T> {
        Iterator<T> iterator = list.iterator();
        T lastIteratedObj = null;
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            lastIteratedObj = iterator.next();
            return lastIteratedObj;
        }
        @Override
        public void remove(){
            iterator.remove();
            map.remove(lastIteratedObj);
        }
    }

=======
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
>>>>>>> 8f30c783dc7088979be7b15044264186a756433e
}
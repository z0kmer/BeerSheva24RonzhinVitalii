package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
<<<<<<< HEAD
    public static class Node<T> {
=======
    private static class Node<T> {
>>>>>>> origin/main
        T obj;
        Node<T> next;
        Node<T> prev;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class LinkedListIterator implements Iterator<T> {
        Node<T> current = head;
        Node<T> prev = null;
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            T res = current.obj;
            prev = current;
            current = current.next;
            return res;
        }
        @Override
        public void remove(){
            if(prev == null) {
                throw new IllegalStateException();
            }
            removeNode(prev);
            prev = null;
        }
        
    }

    Node<T> head;
    Node<T> tail;
    int size = 0;

    private Node<T> getNode(int index) {
        return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

<<<<<<< HEAD
    void addNode(Node<T> node, int index) {
=======
    private void addNode(Node<T> node, int index) {
>>>>>>> origin/main
        if (index == 0) {
            addHead(node);
        } else if (index == size) {
            addTail(node);
        } else {
            addMiddle(node, index);
        }
        size++;
    }

    private void addMiddle(Node<T> nodeToInsert, int index) {
        Node<T> nodeBefore = getNode(index);
        Node<T> nodeAfter = nodeBefore.prev;
        nodeToInsert.next = nodeBefore;
        nodeToInsert.prev = nodeAfter;
        nodeBefore.prev = nodeToInsert;
        nodeAfter.next = nodeToInsert;
    }

    private void addTail(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    private void addHead(Node<T> node) {
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    @Override
    public boolean add(T obj) {
        Node<T> node = new Node<>(obj);
        addNode(node, size);
        return true;
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
        return new LinkedListIterator();
    }

    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);
        Node<T> node = new Node<>(obj);
        addNode(node, index);
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> toRemoveNode = getNode(index);
        T res = toRemoveNode.obj;
        removeNode(toRemoveNode);
        return res;
    }

<<<<<<< HEAD
    public void removeNode(Node<T> toRemoveNode) {
=======
    private void removeNode(Node<T> toRemoveNode) {
>>>>>>> origin/main
        if(toRemoveNode == head) {
            removeHead();
        } else if (toRemoveNode == tail) {
            removeTail();
        } else {
            removeMiddle(toRemoveNode);
        }
        size--;
        clearReferences(toRemoveNode);
    }

    private void clearReferences(Node<T> node) {
        node.next = null;
        node.obj = null;
        node.prev = null;
    }

    private void removeMiddle(Node<T> toRemoveNode) {
        Node<T> beforeNode = toRemoveNode.prev;
        Node<T> afterNode = toRemoveNode.next;
        beforeNode.next = afterNode;
        afterNode.prev = beforeNode;
    }

    private void removeTail() {
        tail = tail.prev;
        tail.next = null;
    }

    private void removeHead() {
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return getNode(index).obj;
    }

    @Override
    public int indexOf(T pattern) {
        return findIndex(pattern, true);
    }

    @Override
    public int lastIndexOf(T pattern) {
        return findIndex(pattern, false);
    }


    private int findIndex(T pattern, boolean fromHead) {
        Node<T> current = fromHead ? head : tail;
        int index = fromHead ? 0 : size - 1;
        int step = fromHead ? 1 : -1;
        while (current != null) {
            if (current.obj == null ? pattern == null : current.obj.equals(pattern)) {
                return index;
            }
            current = fromHead ? current.next : current.prev;
            index += step;
        }
        return -1;
    }
}
package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
    private static class Node<T> {
        T obj;
        Node<T> next;
        Node<T> prev;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = head;
        private Node<T> lastReturned = null;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = current;
            T res = current.obj;
            current = current.next;
            return res;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            removeNode(lastReturned);
            lastReturned = null;
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

    private void addNode(Node<T> node, int index) {
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
        if (nodeAfter != null) {
            nodeAfter.next = nodeToInsert;
        }
    }

    private void addTail(Node<T> node) {
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
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
    public boolean remove(T pattern) {
        Node<T> current = head;
        boolean res = false;
        while (current != null) {
            if (current.obj.equals(pattern)) {
                removeNode(current);
                res = true;
            }
            current = current.next;
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
        return indexOf(pattern) > -1;
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
        Node<T> nodeToRemove = getNode(index);
        if (nodeToRemove == null) {
            throw new NullPointerException("Node to remove is null");
        }
        removeNode(nodeToRemove);
        return nodeToRemove.obj;
    }

    private void removeNode(Node<T> toRemoveNode) {
        if (toRemoveNode == head) {
            removeHead();
        } else if (toRemoveNode == tail) {
            removeTail();
        } else {
            toRemoveNode.prev.next = toRemoveNode.next;
            toRemoveNode.next.prev = toRemoveNode.prev;
        }
        size--;
        clearReferences(toRemoveNode);
    }

    private void clearReferences(Node<T> node) {
        node.next = null;
        //node.obj = null;
        node.prev = null;
    }

    private void removeTail() {
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
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

    public void checkIndex(int index, boolean add) {
        if (index < 0 || index >= size + (add ? 1 : 0)) {
            throw new IndexOutOfBoundsException();
        }
    }
}

package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class TreeSet<T> implements Set<T> {
    private static class Node<T> {
        T obj;
        Node<T> parent;
        Node<T> left;
        Node<T> right;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class TreeSetIterator implements Iterator<T> {
        private Node<T> nextNode;
        private Node<T> lastReturned;

        public TreeSetIterator() {
            nextNode = findMin(root);
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = nextNode;
            nextNode = findSuccessor(nextNode);
            return lastReturned.obj;
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

    private Node<T> root;
    private Comparator<T> comparator;
    int size;

    public TreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public TreeSet() {
        this((Comparator<T>) Comparator.naturalOrder());
    }

    @Override
    public boolean add(T obj) {
        if (contains(obj)) {
            return false;
        }
        Node<T> node = new Node<>(obj);
        if (root == null) {
            root = node;
        } else {
            addAfterParent(node);
        }
        size++;
        return true;
    }

    private void addAfterParent(Node<T> node) {
        Node<T> parent = getParent(node.obj);
        if (comparator.compare(node.obj, parent.obj) > 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }
        node.parent = parent;
    }

    @Override
    public boolean remove(T pattern) {
        Node<T> node = getNode(pattern);
        if (node == null) {
            return false;
        }
        removeNode(node);
        return true;
    }

    private void removeNode(Node<T> node) {
        if (node.left == null && node.right == null) {
            if (node.parent == null) {
                root = null;
            } else if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        } else if (node.left != null && node.right != null) {
            Node<T> predecessor = findMax(node.left);
            node.obj = predecessor.obj;
            removeNode(predecessor);
        } else {
            Node<T> child = (node.left != null) ? node.left : node.right;
            if (node.parent == null) {
                root = child;
            } else if (node == node.parent.left) {
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
            child.parent = node.parent;
        }
        size--;
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
        return getNode(pattern) != null;
    }

    @Override
    public T get(Object pattern) {
        Node<T> node = getNode((T) pattern);
        return node == null ? null : node.obj;
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeSetIterator();
    }

    private Node<T> getParentOrNode(T pattern) {
        Node<T> current = root;
        Node<T> parent = null;
        int compRes = 0;
        while (current != null && (compRes = comparator.compare(pattern, current.obj)) != 0) {
            parent = current;
            current = compRes > 0 ? current.right : current.left;
        }
        return current == null ? parent : current;
    }

    private Node<T> getNode(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        if (res != null) {
            int compRes = comparator.compare(pattern, res.obj);
            res = compRes == 0 ? res : null;
        }
        return res;
    }

    private Node<T> getParent(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        int compRes = comparator.compare(pattern, res.obj);
        return compRes == 0 ? null : res;
    }

    private Node<T> findMin(Node<T> node) {
        while (node != null && node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node<T> findMax(Node<T> node) {
        while (node != null && node.right != null) {
            node = node.right;
        }
        return node;
    }

    private Node<T> findSuccessor(Node<T> node) {
        if (node.right != null) {
            return findMin(node.right);
        }
        Node<T> parent = node.parent;
        while (parent != null && node == parent.right) {
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    @Override
    public boolean removeIf(Predicate<T> predicate) {
        boolean removed = false;
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            T obj = it.next();
            if (predicate.test(obj)) {
                it.remove();
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public void clear() {
        clearNode(root);
        root = null;
        size = 0;
    }

    private void clearNode(Node<T> node) {
        if (node != null) {
            clearNode(node.left);
            clearNode(node.right);
            node.left = null;
            node.right = null;
            node.parent = null;
            node.obj = null;
        }
    }
}
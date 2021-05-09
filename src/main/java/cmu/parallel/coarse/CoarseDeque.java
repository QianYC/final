package cmu.parallel.coarse;

/**
 * @author ycqian
 * @description double linked-list based deque
 */
public class CoarseDeque<E> {
    private Node<E> head, tail;
    private int size;

    private void checkNonNull(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
    }

    public synchronized void addFirst(E data) {
        checkNonNull(data);
        Node<E> node = new Node<>(data, null, head);
        if (head != null) {
            head.prev = node;
        } else {
            tail = node;
        }
        head = node;
        size++;
    }

    public synchronized void addLast(E data) {
        checkNonNull(data);
        Node<E> node = new Node<>(data, tail, null);
        if (tail != null) {
            tail.next = node;
        } else {
            head = node;
        }
        tail = node;
        size++;
    }

    public synchronized E removeFirst() {
        if (head != null) {
            E data = head.data;
            head = head.next;
            if (head != null) {
                head.prev.next = null;
                head.prev = null;
            }
            size--;
            return data;
        } else {
            return null;
        }
    }

    public synchronized E removeLast() {
        if (tail != null) {
            E data = tail.data;
            tail = tail.prev;
            if (tail != null) {
                tail.next.prev = null;
                tail.next = null;
            }
            size--;
            return data;
        } else {
            return null;
        }
    }

    public synchronized E peekFirst() {
        if (head != null) {
            return head.data;
        }
        return null;
    }

    public synchronized E peekLast() {
        if (tail != null) {
            return tail.data;
        }
        return null;
    }

    public synchronized int size() {
        return size;
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }

    public void printAll() {
        System.out.print("[");
        for (Node<E> node = head; node != null; node = node.next) {
            System.out.print(node.toString() + ", ");
        }
        System.out.println("]");
    }

    private class Node<E> {
        public volatile E data;
        public volatile Node<E> prev, next;

        public Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public String toString() {
            return "{" + data.toString() + "}";
        }
    }
}

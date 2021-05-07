package cmu.parallel.coarse;

/**
 * @author ycqian
 * @description double linked-list based deque
 */
public class MyDeque<E> {
    private Node<E> head, tail;
    private int size;

    private void checkNonNull(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
    }

    public synchronized void addFirst(E data) {
        checkNonNull(data);
        if (head == null) {
            tail = head = new Node<E>(data, null, null);
        } else {
            Node<E> node = new Node<>(data, null, head);
            head.prev = node;
            head = node;
        }
        size++;
    }

    public synchronized void addLast(E data) {
        checkNonNull(data);
        if (tail == null) {
            head = tail = new Node<E>(data, null, null);
        } else {
            Node<E> node = new Node<>(data, tail, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public synchronized E removeFirst() {
        if (head != null) {
            E data = head.data;
            Node next = head.next;
            if (next != null) {
                head.next = null;
                next.prev = null;
                head = next;
            } else {
                head = tail = null;
            }
            size--;
            return data;
        }
        return null;
    }

    public synchronized E removeLast() {
        if (tail != null) {
            E data = tail.data;
            Node prev = tail.prev;
            if (prev != null) {
                tail.prev = null;
                prev.next = null;
                tail = prev;
            } else {
                head = tail = null;
            }
            size--;
            return data;
        }
        return null;
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
        public E data;
        public Node<E> prev, next;

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

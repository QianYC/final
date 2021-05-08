package cmu.parallel.fine;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ycqian
 * @description double linked-list based deque
 */
public class MyDeque<E> {
    private Node<E> head, tail;
    private int size;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    private void checkNonNull(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
    }

    private void insertHead(E data) {
        Node<E> node = new Node<>(data, null, head);
        if (head != null) {
            head.prev = node;
        } else {
            tail = node;
        }
        head = node;
    }

    public void addFirst(E data) {
        checkNonNull(data);
        lock.writeLock().lock();
        insertHead(data);
        size++;
        lock.writeLock().unlock();
    }

    public void addLast(E data) {
        checkNonNull(data);
        lock.writeLock().lock();
        insertTail(data);
        size++;
        lock.writeLock().unlock();
    }

    private void insertTail(E data) {
        Node<E> node = new Node<>(data, tail, null);
        if (tail != null) {
            tail.next = node;
        } else {
            head = node;
        }
        tail = node;
    }

    public E removeFirst() {
        E data;
        lock.writeLock().lock();
        data = head == null ? null : head.data;
        if (head == tail) {
            head = tail = null;
            size = 0;
        } else {
            head = head.next;
            head.prev.next = null;
            head.prev = null;
            size--;
        }
        lock.writeLock().unlock();
        return data;
    }

    public E removeLast() {
        E data;
        lock.writeLock().lock();
        data = tail == null ? null : tail.data;
        if (head == tail) {
            head = tail = null;
            size = 0;
        } else {
            tail = tail.prev;
            tail.next.prev = null;
            tail.next = null;
            size--;
        }
        lock.writeLock().unlock();
        return data;
    }

    public E peekFirst() {
        E data;
        lock.readLock().lock();
        data = head == null ? null : head.data;
        lock.readLock().unlock();
        return data;
    }

    public E peekLast() {
        E data;
        lock.readLock().lock();
        data = tail == null ? null : tail.data;
        lock.readLock().unlock();
        return data;
    }

    public int size() {
        int size;
        lock.readLock().lock();
        size = this.size;
        lock.readLock().unlock();
        return size;
    }

    public boolean isEmpty() {
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
//        public ReentrantLock lock = new ReentrantLock(true);

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

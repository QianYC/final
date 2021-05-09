package cmu.parallel.fine;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ycqian
 * @description
 */
public class FineDeque<E> {
    private Node<E> headGuard, tailGuard;
    private ReentrantLock queue = new ReentrantLock();

    public FineDeque() {
        headGuard = new Node<>();
        tailGuard = new Node<>();
        headGuard.next = tailGuard;
        tailGuard.prev = headGuard;
    }

    public void addFirst(E data) {
        queue.lock();
        headGuard.lock.lock();
        Node next = headGuard.next;
        next.lock.lock();
        queue.unlock();
        Node<E> node = new Node<>(data, headGuard, next);
        headGuard.next = node;
        next.prev = node;
        next.lock.unlock();
        headGuard.lock.unlock();
    }

    public E removeFirst() {
        E data = null;
        queue.lock();
        headGuard.lock.lock();
        Node<E> next = headGuard.next;
        next.lock.lock();
        if (next != tailGuard) {
            next.next.lock.lock();
            queue.unlock();
            data = next.data;
            headGuard.next = next.next;
            headGuard.next.prev = headGuard;
            next.next.lock.unlock();
        } else {
            queue.unlock();
        }
        next.lock.unlock();
        headGuard.lock.unlock();
        return data;
    }

    public void addLast(E data) {
        queue.lock();
        tailGuard.lock.lock();
        Node prev = tailGuard.prev;
        prev.lock.lock();
        queue.unlock();
        Node<E> node = new Node<>(data, prev, tailGuard);
        prev.next = node;
        tailGuard.prev = node;
        prev.lock.unlock();
        tailGuard.lock.unlock();
    }

    public E removeLast() {
        E data = null;
        queue.lock();
        tailGuard.lock.lock();
        Node<E> prev = tailGuard.prev;
        prev.lock.lock();
        if (prev != headGuard) {
            prev.prev.lock.lock();
            queue.unlock();
            data = prev.data;
            tailGuard.prev = prev.prev;
            tailGuard.prev.next = tailGuard;
            prev.prev.lock.unlock();
        } else {
            queue.unlock();
        }
        prev.lock.unlock();
        tailGuard.lock.unlock();
        return data;
    }

    public E peekFirst() {
        E data = null;
        queue.lock();
        headGuard.lock.lock();
        Node<E> next = headGuard.next;
        next.lock.lock();
        queue.unlock();
        data = next.data;
        next.lock.unlock();
        headGuard.lock.unlock();
        return data;
    }

    public E peekLast() {
        E data = null;
        queue.lock();
        tailGuard.lock.lock();
        Node<E> prev = tailGuard.prev;
        prev.lock.lock();
        queue.unlock();
        data = prev.data;
        prev.lock.unlock();
        tailGuard.lock.unlock();
        return data;
    }

    public void printAll() {
        System.out.print("[");
        for (Node<E> node = headGuard.next; node != tailGuard; node = node.next) {
            System.out.print(node.toString() + ", ");
        }
        System.out.println("]");
    }

    private class Node<E> {
        public E data;
        public Node prev, next;
        public ReentrantLock lock = new ReentrantLock();

        public Node() {}

        public Node(E data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public String toString() {
            return "{" + data.toString() + "}";
        }
    }
}

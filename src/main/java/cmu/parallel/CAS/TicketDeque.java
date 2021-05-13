package cmu.parallel.CAS;

import cmu.parallel.fine.FineDeque;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ycqian
 * @description
 */
public class TicketDeque<E> {
    private Node<E> headGuard, tailGuard;
    private TicketLock queue = new TicketLock();

    public TicketDeque() {
        headGuard = new Node<>();
        tailGuard = new Node<>();
        headGuard.next = tailGuard;
        tailGuard.prev = headGuard;
    }

    public void addFirst(E data) {
        int tk = queue.lock();
        int htk = headGuard.lock.lock();
        Node next = headGuard.next;
        int ntk = next.lock.lock();
        queue.unlock(tk);
        Node<E> node = new Node<>(data, headGuard, next);
        headGuard.next = node;
        next.prev = node;
        next.lock.unlock(ntk);
        headGuard.lock.unlock(htk);
    }

    public E removeFirst() {
        E data = null;
        int tk = queue.lock();
        int htk = headGuard.lock.lock();
        Node<E> next = headGuard.next;
        int ntk = next.lock.lock();
        if (next != tailGuard) {
            int nntk = next.next.lock.lock();
            queue.unlock(tk);
            data = next.data;
            headGuard.next = next.next;
            headGuard.next.prev = headGuard;
            next.next.lock.unlock(nntk);
        } else {
            queue.unlock(tk);
        }
        next.lock.unlock(ntk);
        headGuard.lock.unlock(htk);
        return data;
    }

    public void addLast(E data) {
        int tk = queue.lock();
        int ttk = tailGuard.lock.lock();
        Node prev = tailGuard.prev;
        int ptk = prev.lock.lock();
        queue.unlock(tk);
        Node<E> node = new Node<>(data, prev, tailGuard);
        prev.next = node;
        tailGuard.prev = node;
        prev.lock.unlock(ptk);
        tailGuard.lock.unlock(ttk);
    }

    public E removeLast() {
        E data = null;
        int tk = queue.lock();
        int ttk = tailGuard.lock.lock();
        Node<E> prev = tailGuard.prev;
        int ptk = prev.lock.lock();
        if (prev != headGuard) {
            int pptk = prev.prev.lock.lock();
            queue.unlock(tk);
            data = prev.data;
            tailGuard.prev = prev.prev;
            tailGuard.prev.next = tailGuard;
            prev.prev.lock.unlock(pptk);
        } else {
            queue.unlock(tk);
        }
        prev.lock.unlock(ptk);
        tailGuard.lock.unlock(ttk);
        return data;
    }

    public E peekFirst() {
        E data = null;
        int tk = queue.lock();
        int htk = headGuard.lock.lock();
        Node<E> next = headGuard.next;
        int ntk = next.lock.lock();
        queue.unlock(tk);
        data = next.data;
        next.lock.unlock(ntk);
        headGuard.lock.unlock(htk);
        return data;
    }

    public E peekLast() {
        E data = null;
        int tk = queue.lock();
        int ttk = tailGuard.lock.lock();
        Node<E> prev = tailGuard.prev;
        int ptk = prev.lock.lock();
        queue.unlock(tk);
        data = prev.data;
        prev.lock.unlock(ptk);
        tailGuard.lock.unlock(ttk);
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
        public TicketLock lock = new TicketLock();

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

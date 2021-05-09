package cmu.parallel;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class CASDeque<T> {
    private final AtomicReference<Anchor> anchor;
//    private final AtomicInteger size;

    private final static int STABLE = 1;
    private final static int RT_PUSH = 2;
    private final static int LH_PUSH = 3;

    public CASDeque() {
        anchor = new AtomicReference<>(new Anchor());
//        size = new AtomicInteger();
//        size.set(0);
        System.out.println(anchor.get().status);
    }

    public void addFirst(T data) {
        Node<T> addedNode = new Node<>(data);

        while (true){
            Anchor<T> anchorCopy = anchor.get();

            if (anchorCopy.head == null){
                if (anchor.compareAndSet(anchorCopy, new Anchor<T>(addedNode, addedNode, STABLE))){
                    return;
                }
            } else if (anchorCopy.status == STABLE) {
                addedNode.next = anchorCopy.head;
                Anchor<T> newAnchor = new Anchor<T>(addedNode, anchorCopy.tail, LH_PUSH);
                if (anchor.compareAndSet(anchorCopy, newAnchor)){
                    stabilizeLeft(newAnchor);
                    return;
                }
            } else {
                stabilize(anchorCopy);
            }
        }
    }

    // base
    public void addLast(T data) {
        Node<T> addedNode = new Node<>(data);

        while (true){
            Anchor<T> anchorCopy = anchor.get();

            if (anchorCopy.tail == null){
                if (anchor.compareAndSet(anchorCopy, new Anchor<T>(addedNode, addedNode, STABLE))){
                    return;
                }
            } else if (anchorCopy.status == STABLE) {
                addedNode.previous = anchorCopy.tail;
                Anchor<T> newAnchor = new Anchor<T>(anchorCopy.head, addedNode, RT_PUSH);
                if (anchor.compareAndSet(anchorCopy, newAnchor)){
                    stabilizeRight(newAnchor);
                    return;
                }
            } else {
                stabilize(anchorCopy);
            }
        }
    }

    public T removeFirst() {
        Anchor<T> anchorCopy = null;

        while (true) {
            anchorCopy = this.anchor.get();
            if (anchorCopy.tail == null)
                return null;

            if (anchorCopy.head == anchorCopy.tail) {
                if (anchor.compareAndSet(anchorCopy, new Anchor<T>()))
                    break;
            } else if (anchorCopy.status == STABLE){
                if (!anchor.get().equals(anchorCopy))
                    continue;

                Node<T> next = anchorCopy.head.next;
                if (this.anchor.compareAndSet(anchorCopy, new Anchor<T>(next, anchorCopy.tail, anchorCopy.status)))
                    break;
            } else {
                stabilize(anchorCopy);
            }
        }

        return anchorCopy.head.value;
    }

    // base
    public T removeLast() {
        Anchor<T> anchorCopy = null;

        while (true) {
            anchorCopy = this.anchor.get();
            if (anchorCopy.tail == null)
                return null;

            if (anchorCopy.head == anchorCopy.tail) {
                if (anchor.compareAndSet(anchorCopy, new Anchor<T>()))
                    break;
            } else if (anchorCopy.status == STABLE){
                if (!anchor.get().equals(anchorCopy))
                    continue;

                Node<T> prev = anchorCopy.tail.previous;
                if (this.anchor.compareAndSet(anchorCopy, new Anchor<T>(anchorCopy.head, prev, anchorCopy.status)))
                    break;
            } else {
                stabilize(anchorCopy);
            }
        }

        return anchorCopy.tail.value;
    }


    public T peekFirst() {
        Anchor<T> anchorCopy = null;

        while (true) {
            anchorCopy = this.anchor.get();
            if (anchorCopy.tail == null)
                return null;

            if (anchorCopy.head == anchorCopy.tail) {
                break;
            } else if (anchorCopy.status == STABLE){
                if (!anchor.get().equals(anchorCopy))
                    continue;

                break;
            } else {
                stabilize(anchorCopy);
            }
        }

        return anchorCopy.head.value;
    }

    public T peekLast() {
        Anchor<T> anchorCopy = null;

        while (true) {
            anchorCopy = this.anchor.get();
            if (anchorCopy.tail == null)
                return null;

            if (anchorCopy.head == anchorCopy.tail) {
                break;
            } else if (anchorCopy.status == STABLE){
                if (!anchor.get().equals(anchorCopy))
                    continue;

                break;
            } else {
                stabilize(anchorCopy);
            }
        }

        return anchorCopy.tail.value;
    }


    private void stabilize(Anchor<T> anchor){
        if (anchor.status == RT_PUSH)
            stabilizeRight(anchor);
        if (anchor.status == LH_PUSH)
            stabilizeLeft(anchor);
    }

    // base
    private void stabilizeRight(Anchor<T> anchor){
        Node<T> prev = anchor.tail.previous;
        if (!anchor.equals(this.anchor.get()))
            return;

        Node<T> prevNext = prev.next;
        if (prevNext == null || !prevNext.equals(anchor.tail)) {
            if (!anchor.equals(this.anchor.get()))
                return;

            AtomicReferenceFieldUpdater<Node, Node> updater
                    = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class,"next");
//            AtomicReference<Node<T>> atomicPrevNext = new AtomicReference<>(prev.next);
//            if (!atomicPrevNext.compareAndSet(prevNext, anchor.tail))
            if (!updater.compareAndSet(prev, prevNext, anchor.tail))
                return;
        }

        this.anchor.compareAndSet(anchor, new Anchor<T>(anchor.head, anchor.tail, STABLE));
    }

    private void stabilizeLeft(Anchor<T> anchor){
        Node<T> next = anchor.head.next;
        if (!anchor.equals(this.anchor.get()))
            return;

        Node<T> nextPrev = next.previous;
        if (nextPrev == null || !nextPrev.equals(anchor.head)) {
            if (!anchor.equals(this.anchor.get()))
                return;

//            AtomicReference<Node<T>> atomicNextPrev = new AtomicReference<>(next.previous);
//            if (!atomicNextPrev.compareAndSet(nextPrev, anchor.head))
//                return;
            AtomicReferenceFieldUpdater<Node, Node> updater
                    = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class,"previous");
            if (!updater.compareAndSet(next, nextPrev, anchor.head))
                return;
        }

        this.anchor.compareAndSet(anchor, new Anchor<T>(anchor.head, anchor.tail, STABLE));
    }

    private static class Node<T> {
        public volatile T value;
        public volatile Node<T> next;
        public volatile Node<T> previous;

        public Node(T value) {
            this.value = value;
            this.next = null;
            this.previous = null;
        }
    }

    private static class Anchor<T> {
        public volatile Node<T> head;
        public volatile Node<T> tail;
        public volatile int status;

        public Anchor(){
            this.head = null;
            this.tail = null;
            this.status = STABLE;
        }

        public Anchor(Node<T> head, Node<T> tail, int status){
            this.head = head;
            this.tail = tail;
            this.status = status;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Anchor<?> anchor = (Anchor<?>) o;
            return status == anchor.status &&
                    Objects.equals(head, anchor.head) &&
                    Objects.equals(tail, anchor.tail);
        }

        @Override
        public int hashCode() {
            return Objects.hash(head, tail, status);
        }
    }


    public static void main(String[] args){
        CASDeque<Integer> deque = new CASDeque<>();
        deque.addLast(2);
        deque.addLast(1);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addLast(334);
        deque.addFirst(12);
        System.out.println("finish");
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        int a = deque.removeFirst();
        System.out.println(a);
    }
}

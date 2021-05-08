//package cmu.parallel.stm;
//
//import org.multiverse.api.StmUtils;
//import org.multiverse.api.references.TxnInteger;
//import org.multiverse.api.references.TxnRef;
//import scala.concurrent.stm.Ref;
//import scala.concurrent.stm.japi.STM;
//
///**
// * @author ycqian
// * @description
// */
//public class MyDeque<E> {
//    private TxnRef<Node<E>> head = StmUtils.newTxnRef(), tail = StmUtils.newTxnRef();
//    private TxnInteger size = StmUtils.newTxnInteger(0);
//
//    private void checkNonNull(E e) {
//        if (e == null) {
//            throw new NullPointerException();
//        }
//    }
//
//    public void addFirst(E data) {
//        checkNonNull(data);
//
//        STM.atomic(() -> {
//
//        });
//
//        StmUtils.atomic(() -> {
//            Node<E> node = new Node<>(data, null, head.get());
//            if (head.get() != null) {
//                head.get().prev = node;
//            } else {
//                tail.set(node);
//            }
//            head.set(node);
//            size.increment(1);
//        });
//    }
//
//    public void addLast(E data) {
//        checkNonNull(data);
//        StmUtils.atomic(() -> {
//            Node<E> node = new Node<>(data, tail.get(), null);
//            if (tail.get() != null) {
//                tail.get().next = node;
//            } else {
//                head.set(node);
//            }
//            tail.set(node);
//            size.increment(1);
//        });
//    }
//
//    public E removeFirst() {
//        return StmUtils.atomic(() -> {
//            if (head.get() != null) {
//                E data = head.get().data;
//                head.set(head.get().next);
//                if (head.get() != null) {
//                    head.get().prev.next = null;
//                    head.get().prev = null;
//                }
//                size.decrement(1);
//                return data;
//            } else {
//                return null;
//            }
//        });
//    }
//
//    public E removeLast() {
//        return StmUtils.atomic(() -> {
//            if (tail.get() != null) {
//                E data = tail.get().data;
//                tail.set(tail.get().prev);
//                if (tail.get() != null) {
//                    tail.get().next.prev = null;
//                    tail.get().next = null;
//                }
//                size.decrement(1);
//                return data;
//            } else {
//                return null;
//            }
//        });
//    }
//
//    public E peekFirst() {
//        return StmUtils.atomic(() -> {
//            if (head.get() != null) {
//                return head.get().data;
//            }
//            return null;
//        });
//    }
//
//    public E peekLast() {
//        return StmUtils.atomic(() -> {
//            if (tail.get() != null) {
//                return tail.get().data;
//            }
//            return null;
//        });
//    }
//
//    public int size() {
//        return size.atomicGet();
//    }
//
//    public boolean isEmpty() {
//        return size.atomicGet() == 0;
//    }
//
//    public void printAll() {
//        System.out.print("[");
//        for (Node<E> node = head.get(); node != null; node = node.next) {
//            System.out.print(node.toString() + ", ");
//        }
//        System.out.println("]");
//    }
//
//    private class Node<E> {
//        public volatile E data;
//        public volatile Ref<Node<E>> prev, next;
//
//        public Node(E data, Node<E> prev, Node<E> next) {
//            this.data = data;
//            STM.newRef(prev);
//            this.prev = prev;
//            this.next = next;
//        }
//
//        public String toString() {
//            return "{" + data.toString() + "}";
//        }
//    }
//}

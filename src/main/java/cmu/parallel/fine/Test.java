package cmu.parallel.fine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ycqian
 * @description
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        FineDeque<Integer> deque = new FineDeque<>();
        ExecutorService pool1 = Executors.newFixedThreadPool(4);
        ExecutorService pool2 = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 5; i++) {
            final int val = i;
            pool1.submit(() -> deque.addFirst(val));
            pool2.submit(() -> System.out.println(deque.removeFirst()));
            pool1.submit(() -> System.out.println(deque.peekLast()));
            pool2.submit(() -> deque.addLast(val));
            pool1.submit(() -> System.out.println(deque.removeLast()));
            pool2.submit(() -> System.out.println(deque.peekFirst()));
        }
        System.out.println("wait");
        pool1.awaitTermination(1, TimeUnit.SECONDS);
        pool2.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("finish");
        deque.printAll();
        pool1.shutdown();
        pool2.shutdown();
    }
}

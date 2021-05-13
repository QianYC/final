package cmu.parallel.CAS;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicketLockTest {
    @Test
    public void testLock() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        Counter counter = new Counter();
        TicketLock lock = new TicketLock();
        for (int i = 0; i < 10000; i++) {
            pool.submit(() -> {
                int tk = lock.lock();
                counter.inc();
                lock.unlock(tk);
            });
        }
        pool.awaitTermination(1, TimeUnit.SECONDS);
        assertEquals(10000, counter.counter);
    }

    @Test
    public void invalidUnlock() {
        TicketLock lock = new TicketLock();
        int ticket = lock.lock();
        Exception exception = assertThrows(IllegalStateException.class, () -> lock.unlock(ticket + 1));
        System.out.println(exception.getMessage());
    }

    class Counter {
        int counter = 0;

        public void inc() {
            counter++;
        }
    }

}
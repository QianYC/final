package cmu.parallel.CAS;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ycqian
 * @description
 */
public class TicketLock {
    private volatile AtomicInteger ticket, currentServed;

    public TicketLock() {
        ticket = new AtomicInteger(0);
        currentServed = new AtomicInteger(0);
    }

    public int lock() {
        int tk = ticket.getAndIncrement();
        while (tk != currentServed.get()) {}
        return tk;
    }

    public void unlock(int tk) {
        if (tk != currentServed.get()) {
            throw new IllegalStateException(String.format("lock not held by %s", Thread.currentThread().getName()));
        }
        currentServed.incrementAndGet();
    }
}

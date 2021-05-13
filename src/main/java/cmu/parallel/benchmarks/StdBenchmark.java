package cmu.parallel.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author ycqian
 * @description
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StdBenchmark {
    ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();

    @Benchmark
    //@Threads(30)
    @Fork(value = 1, warmups = 1)
    @OperationsPerInvocation(2)
    public void reader(Blackhole blackhole) {
        blackhole.consume(deque.peekFirst());
        blackhole.consume(deque.peekLast());
    }

    @Benchmark
    //@Threads(2)
    @Fork(value = 1, warmups = 1)
    @OperationsPerInvocation(4)
    public void writer(Blackhole blackhole) {
        deque.addFirst(1);
        deque.addLast(2);
        blackhole.consume(deque.removeFirst());
        blackhole.consume(deque.removeLast());
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OperationsPerInvocation(8)
    public void r3w1(Blackhole blackhole) {
        blackhole.consume(deque.peekFirst());
        deque.addFirst(1);
        blackhole.consume(deque.peekFirst());
        blackhole.consume(deque.peekFirst());
        blackhole.consume(deque.peekLast());
        blackhole.consume(deque.removeLast());
        blackhole.consume(deque.peekLast());
        blackhole.consume(deque.peekLast());
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OperationsPerInvocation(8)
    public void r2w2(Blackhole blackhole) {
        blackhole.consume(deque.peekFirst());
        deque.addFirst(1);
        blackhole.consume(deque.peekFirst());
        deque.addFirst(1);
        blackhole.consume(deque.removeLast());
        blackhole.consume(deque.peekLast());
        blackhole.consume(deque.removeFirst());
        blackhole.consume(deque.peekLast());
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OperationsPerInvocation(8)
    public void r1w3(Blackhole blackhole) {
        blackhole.consume(deque.peekFirst());
        deque.addFirst(1);
        deque.addFirst(1);
        deque.addFirst(1);
        blackhole.consume(deque.peekFirst());
        blackhole.consume(deque.removeFirst());
        blackhole.consume(deque.removeFirst());
        blackhole.consume(deque.removeFirst());
    }
}

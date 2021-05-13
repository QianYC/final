package cmu.parallel.benchmarks;

import cmu.parallel.CAS.CASDeque;
import cmu.parallel.fine.FineDeque;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * @author ycqian
 * @description
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class CASBenchmark {
    CASDeque<Integer> deque = new CASDeque<>();

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
}

package cmu.parallel;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author ycqian
 * @description
 */
@State(Scope.Group)
@Threads(32)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BaselineBenchmark {
    private int loop = 10000;

    private ConcurrentLinkedDeque<Integer> free = new ConcurrentLinkedDeque<>();

    /**
     * most read test case
     */
    @Benchmark
    @Group("free_most_read")
    @GroupThreads(30)
    @OperationsPerInvocation(20000)
    public void freeMostReadRead(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            blackhole.consume(free.peekFirst());
            blackhole.consume(free.peekLast());
        }
    }

    @Benchmark
    @Group("free_most_read")
    @GroupThreads(2)
    @OperationsPerInvocation(40000)
    public void freeMostReadWrite(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            free.addFirst(48578);
            free.addLast(485567);
            blackhole.consume(free.removeLast());
            blackhole.consume(free.removeFirst());
        }
    }

    /**
     * most write test case
     */
    @Benchmark
    @Group("free_most_write")
    @GroupThreads(2)
    @OperationsPerInvocation(20000)
    public void freeMostWriteRead(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            blackhole.consume(free.peekFirst());
            blackhole.consume(free.peekLast());
        }
    }

    @Benchmark
    @Group("free_most_write")
    @GroupThreads(30)
    @OperationsPerInvocation(40000)
    public void freeMostWriteWrite(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            free.addFirst(48578);
            free.addLast(485567);
            blackhole.consume(free.removeLast());
            blackhole.consume(free.removeFirst());
        }
    }
}

package cmu.parallel;

import cmu.parallel.fine.FineDeque;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * @author ycqian
 * @description
 */
@State(Scope.Group)
@Threads(32)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class FineBenchmark {

    private int loop = 10000;
    private FineDeque<Integer> fine = new FineDeque<>();

    /**
     * most read test case
     */
    @Benchmark
    @Group("fine_most_read")
    @GroupThreads(30)
    @OperationsPerInvocation(20000)
    public void fineMostReadRead(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            blackhole.consume(fine.peekFirst());
            blackhole.consume(fine.peekLast());
        }
    }

    @Benchmark
    @Group("fine_most_read")
    @GroupThreads(2)
    @OperationsPerInvocation(40000)
    public void fineMostReadWrite(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            fine.addFirst(48578);
            fine.addLast(485567);
            blackhole.consume(fine.removeLast());
            blackhole.consume(fine.removeFirst());
        }
    }

    /**
     * most write test case
     */
    @Benchmark
    @Group("fine_most_write")
    @GroupThreads(2)
    @OperationsPerInvocation(20000)
    public void fineMostWriteRead(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            blackhole.consume(fine.peekFirst());
            blackhole.consume(fine.peekLast());
        }
    }

    @Benchmark
    @Group("fine_most_write")
    @GroupThreads(30)
    @OperationsPerInvocation(40000)
    public void fineMostWriteAddFirst(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            fine.addFirst(48578);
            fine.addLast(485567);
            blackhole.consume(fine.removeLast());
            blackhole.consume(fine.removeFirst());
        }
    }
}

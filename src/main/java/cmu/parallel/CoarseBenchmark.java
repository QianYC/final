package cmu.parallel;

import cmu.parallel.coarse.CoarseDeque;
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
public class CoarseBenchmark {
    private int loop = 10000;
    private CoarseDeque<Integer> coarse = new CoarseDeque<>();

    /**
     * most read test case
     */
    @Benchmark
    @Group("coarse_most_read")
    @GroupThreads(30)
    @OperationsPerInvocation(20000)
    public void coarseMostReadRead(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            blackhole.consume(coarse.peekFirst());
            blackhole.consume(coarse.peekLast());
        }
    }

    @Benchmark
    @Group("coarse_most_read")
    @GroupThreads(2)
    @OperationsPerInvocation(40000)
    public void coarseMostReadWrite(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            coarse.addFirst(48578);
            coarse.addLast(485567);
            blackhole.consume(coarse.removeLast());
            blackhole.consume(coarse.removeFirst());
        }
    }

    /**
     * most write test case
     */
    @Benchmark
    @Group("coarse_most_write")
    @GroupThreads(2)
    @OperationsPerInvocation(20000)
    public void coarseMostWriteRead(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            blackhole.consume(coarse.peekFirst());
            blackhole.consume(coarse.peekLast());
        }
    }

    @Benchmark
    @Group("coarse_most_write")
    @GroupThreads(30)
    @OperationsPerInvocation(40000)
    public void coarseMostWriteWrite(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            coarse.addFirst(48578);
            coarse.addLast(485567);
            blackhole.consume(coarse.removeLast());
            blackhole.consume(coarse.removeFirst());
        }
    }
}

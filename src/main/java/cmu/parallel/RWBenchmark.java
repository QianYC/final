package cmu.parallel;

import cmu.parallel.fine.RWDeque;
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
public class RWBenchmark {
    private int loop = 10000;
    private RWDeque<Integer> rw = new RWDeque<>();

    /**
     * most read test case
     */
    @Benchmark
    @Group("rw_most_read")
    @GroupThreads(30)
    @OperationsPerInvocation(20000)
    public void rwMostReadRead(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            blackhole.consume(rw.peekFirst());
            blackhole.consume(rw.peekLast());
        }
    }

    @Benchmark
    @Group("rw_most_read")
    @GroupThreads(2)
    @OperationsPerInvocation(40000)
    public void rwMostReadWrite(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            rw.addFirst(48578);
            rw.addLast(485567);
            blackhole.consume(rw.removeLast());
            blackhole.consume(rw.removeFirst());
        }
    }

    /**
     * most write test case
     */
    @Benchmark
    @Group("rw_most_write")
    @GroupThreads(2)
    @OperationsPerInvocation(20000)
    public void rwMostWriteRead(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            blackhole.consume(rw.peekFirst());
            blackhole.consume(rw.peekLast());
        }
    }
    
    @Benchmark
    @Group("rw_most_write")
    @GroupThreads(30)
    @OperationsPerInvocation(40000)
    public void rwMostWriteWrite(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            rw.addFirst(48578);
            rw.addLast(485567);
            blackhole.consume(rw.removeLast());
            blackhole.consume(rw.removeFirst());
        }
    }
}

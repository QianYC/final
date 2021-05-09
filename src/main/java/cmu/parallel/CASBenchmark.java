package cmu.parallel;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * @author ycqian
 * @description
 */
@State(Scope.Group)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class CASBenchmark {
    private int loop = 10000;
    private cmu.parallel.CASDeque<Integer> cas = new cmu.parallel.CASDeque<>();

    /**
     * most read test case
     */
    @Benchmark
    @Group("cas_most_read")
    @GroupThreads(30)
    @OperationsPerInvocation(20000)
    public void casMostReadRead(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            blackhole.consume(cas.peekFirst());
            blackhole.consume(cas.peekLast());
        }
    }

    @Benchmark
    @Group("cas_most_read")
    @GroupThreads(2)
    @OperationsPerInvocation(40000)
    public void casMostReadWrite(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            cas.addFirst(48578);
            cas.addLast(485567);
            blackhole.consume(cas.removeLast());
            blackhole.consume(cas.removeFirst());
        }
    }

    /**
     * most write test case
     */
    @Benchmark
    @Group("cas_most_write")
    @GroupThreads(2)
    @OperationsPerInvocation(20000)
    public void casMostWriteRead(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            blackhole.consume(cas.peekFirst());
            blackhole.consume(cas.peekLast());
        }
    }

    @Benchmark
    @Group("cas_most_write")
    @GroupThreads(30)
    @OperationsPerInvocation(40000)
    public void casMostWriteWrite(Blackhole blackhole) {
        for (int i = 0; i < loop; i++) {
            cas.addFirst(48578);
            cas.addLast(485567);
            blackhole.consume(cas.removeLast());
            blackhole.consume(cas.removeFirst());
        }
    }
}

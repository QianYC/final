package cmu.parallel;

import cmu.parallel.coarse.MyDeque;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * @author ycqian
 * @description
 */
@State(Scope.Group)
@Threads(32)
public class CoarseBenchmark {
    private MyDeque<Integer> coarse = new MyDeque<>();

    /**
     * most read test case
     */
    @Benchmark
    @Group("coarse_most_read")
    @GroupThreads(8)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostReadPeekFirst(Blackhole blackhole) {
        blackhole.consume(coarse.peekFirst());
    }

    @Benchmark
    @Group("coarse_most_read")
    @GroupThreads(8)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostReadPeekLast(Blackhole blackhole) {
        blackhole.consume(coarse.peekLast());
    }

    @Benchmark
    @Group("coarse_most_read")
    @GroupThreads(8)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostReadSize(Blackhole blackhole) {
        blackhole.consume(coarse.size());
    }

    @Benchmark
    @Group("coarse_most_read")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostReadAddFirst(Blackhole blackhole) {
        coarse.addFirst(48578);
    }

    @Benchmark
    @Group("coarse_most_read")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostReadAddLast(Blackhole blackhole) {
        coarse.addLast(485567);
    }

    @Benchmark
    @Group("coarse_most_read")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostReadRemoveFirst(Blackhole blackhole) {
        blackhole.consume(coarse.removeFirst());
    }

    @Benchmark
    @Group("coarse_most_read")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostReadRemoveLast(Blackhole blackhole) {
        blackhole.consume(coarse.removeLast());
    }

    /**
     * most write test case
     */
    @Benchmark
    @Group("coarse_most_write")
    @GroupThreads(1)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostWritePeekFirst(Blackhole blackhole) {
        blackhole.consume(coarse.peekFirst());
    }

    @Benchmark
    @Group("coarse_most_write")
    @GroupThreads(1)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostWritePeekLast(Blackhole blackhole) {
        blackhole.consume(coarse.peekLast());
    }

    @Benchmark
    @Group("coarse_most_write")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostWriteSize(Blackhole blackhole) {
        blackhole.consume(coarse.size());
    }

    @Benchmark
    @Group("coarse_most_write")
    @GroupThreads(7)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostWriteAddFirst(Blackhole blackhole) {
        coarse.addFirst(48578);
    }

    @Benchmark
    @Group("coarse_most_write")
    @GroupThreads(7)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostWriteAddLast(Blackhole blackhole) {
        coarse.addLast(485567);
    }

    @Benchmark
    @Group("coarse_most_write")
    @GroupThreads(7)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostWriteRemoveFirst(Blackhole blackhole) {
        blackhole.consume(coarse.removeFirst());
    }

    @Benchmark
    @Group("coarse_most_write")
    @GroupThreads(7)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void coarseMostWriteRemoveLast(Blackhole blackhole) {
        blackhole.consume(coarse.removeLast());
    }
}

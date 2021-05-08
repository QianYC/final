package cmu.parallel;

import cmu.parallel.fine.MyDeque;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * @author ycqian
 * @description
 */
@State(Scope.Group)
@Threads(32)
public class FineBenchmark {
    private MyDeque<Integer> fine = new MyDeque<>();

    /**
     * most read test case
     */
    @Benchmark
    @Group("fine_most_read")
    @GroupThreads(8)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostReadPeekFirst(Blackhole blackhole) {
        blackhole.consume(fine.peekFirst());
    }

    @Benchmark
    @Group("fine_most_read")
    @GroupThreads(8)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostReadPeekLast(Blackhole blackhole) {
        blackhole.consume(fine.peekLast());
    }

    @Benchmark
    @Group("fine_most_read")
    @GroupThreads(8)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostReadSize(Blackhole blackhole) {
        blackhole.consume(fine.size());
    }

    @Benchmark
    @Group("fine_most_read")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostReadAddFirst(Blackhole blackhole) {
        fine.addFirst(48578);
    }

    @Benchmark
    @Group("fine_most_read")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostReadAddLast(Blackhole blackhole) {
        fine.addLast(485567);
    }

    @Benchmark
    @Group("fine_most_read")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostReadRemoveFirst(Blackhole blackhole) {
        blackhole.consume(fine.removeFirst());
    }

    @Benchmark
    @Group("fine_most_read")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostReadRemoveLast(Blackhole blackhole) {
        blackhole.consume(fine.removeLast());
    }

    /**
     * most write test case
     */
    @Benchmark
    @Group("fine_most_write")
    @GroupThreads(1)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostWritePeekFirst(Blackhole blackhole) {
        blackhole.consume(fine.peekFirst());
    }

    @Benchmark
    @Group("fine_most_write")
    @GroupThreads(1)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostWritePeekLast(Blackhole blackhole) {
        blackhole.consume(fine.peekLast());
    }

    @Benchmark
    @Group("fine_most_write")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostWriteSize(Blackhole blackhole) {
        blackhole.consume(fine.size());
    }

    @Benchmark
    @Group("fine_most_write")
    @GroupThreads(7)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostWriteAddFirst(Blackhole blackhole) {
        fine.addFirst(48578);
    }

    @Benchmark
    @Group("fine_most_write")
    @GroupThreads(7)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostWriteAddLast(Blackhole blackhole) {
        fine.addLast(485567);
    }

    @Benchmark
    @Group("fine_most_write")
    @GroupThreads(7)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostWriteRemoveFirst(Blackhole blackhole) {
        blackhole.consume(fine.removeFirst());
    }

    @Benchmark
    @Group("fine_most_write")
    @GroupThreads(7)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fineMostWriteRemoveLast(Blackhole blackhole) {
        blackhole.consume(fine.removeLast());
    }
}

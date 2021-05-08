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
public class BaselineBenchmark {
    private ConcurrentLinkedDeque<Integer> free = new ConcurrentLinkedDeque<>();

    /**
     * most read test case
    */
    @Benchmark
    @Group("free_most_read")
    @GroupThreads(30)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void freeMostReadRead(Blackhole blackhole) {
        blackhole.consume(free.peekFirst());
        blackhole.consume(free.peekLast());
        blackhole.consume(free.size());
    }

//    @Benchmark
//    @Group("free_most_read")
//    @GroupThreads(8)
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void freeMostReadPeekLast(Blackhole blackhole) {
//        blackhole.consume(free.peekLast());
//    }
//
//    @Benchmark
//    @Group("free_most_read")
//    @GroupThreads(8)
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void freeMostReadSize(Blackhole blackhole) {
//        blackhole.consume(free.size());
//    }

    @Benchmark
    @Group("free_most_read")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void freeMostReadWrite(Blackhole blackhole) {
        free.addFirst(48578);
        free.addLast(485567);
        blackhole.consume(free.removeLast());
        blackhole.consume(free.removeFirst());
    }

//    @Benchmark
//    @Group("free_most_read")
//    @GroupThreads(2)
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void freeMostReadAddLast(Blackhole blackhole) {
//        free.addLast(485567);
//    }
//
//    @Benchmark
//    @Group("free_most_read")
//    @GroupThreads(2)
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void freeMostReadRemoveFirst(Blackhole blackhole) {
//        blackhole.consume(free.removeFirst());
//    }
//
//    @Benchmark
//    @Group("free_most_read")
//    @GroupThreads(2)
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void freeMostReadRemoveLast(Blackhole blackhole) {
//        blackhole.consume(free.removeLast());
//    }

    /**
     * most write test case
    */
    @Benchmark
    @Group("free_most_write")
    @GroupThreads(2)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void freeMostWriteRead(Blackhole blackhole) {
        blackhole.consume(free.peekFirst());
        blackhole.consume(free.peekLast());
        blackhole.consume(free.size());
    }

//    @Benchmark
//    @Group("free_most_write")
//    @GroupThreads(1)
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void freeMostWritePeekLast(Blackhole blackhole) {
//        blackhole.consume(free.peekLast());
//    }
//
//    @Benchmark
//    @Group("free_most_write")
//    @GroupThreads(2)
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void freeMostWriteSize(Blackhole blackhole) {
//        blackhole.consume(free.size());
//    }

    @Benchmark
    @Group("free_most_write")
    @GroupThreads(30)
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void freeMostWriteWrite(Blackhole blackhole) {
        free.addFirst(48578);
        free.addLast(485567);
        blackhole.consume(free.removeLast());
        blackhole.consume(free.removeFirst());
    }

//    @Benchmark
//    @Group("free_most_write")
//    @GroupThreads(7)
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void freeMostWriteAddLast(Blackhole blackhole) {
//        free.addLast(485567);
//    }
//
//    @Benchmark
//    @Group("free_most_write")
//    @GroupThreads(7)
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void freeMostWriteRemoveFirst(Blackhole blackhole) {
//        blackhole.consume(free.removeFirst());
//    }
//
//    @Benchmark
//    @Group("free_most_write")
//    @GroupThreads(7)
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void freeMostWriteRemoveLast(Blackhole blackhole) {
//        blackhole.consume(free.removeLast());
//    }
}

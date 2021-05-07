/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package cmu.parallel;

import cmu.parallel.coarse.MyDeque;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

public class MyBenchmark {

    @State(Scope.Group)
    @Threads(32)
    public static class MyState {
        public MyDeque<Integer> coarse = new MyDeque<>();
        public ConcurrentLinkedDeque<Integer> baseline = new ConcurrentLinkedDeque<>();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Group("baseline_most_read")
    @GroupThreads(30)
    public void baselineMostReadRead(MyState state, Blackhole blackhole) {
        blackhole.consume(state.baseline.size());
        blackhole.consume(state.baseline.isEmpty());
        blackhole.consume(state.baseline.peekFirst());
        blackhole.consume(state.baseline.peekLast());
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Group("baseline_most_read")
    @GroupThreads(2)
    public void baselineMostReadWrite(MyState state, Blackhole blackhole) {
        state.baseline.addFirst(34534);
        state.baseline.addLast(97844);
        blackhole.consume(state.baseline.removeFirst());
        blackhole.consume(state.baseline.removeLast());
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Group("coarse_most_read")
    @GroupThreads(30)
    public void coarseMostReadRead(MyState state, Blackhole blackhole) {
        blackhole.consume(state.coarse.size());
        blackhole.consume(state.coarse.isEmpty());
        blackhole.consume(state.coarse.peekFirst());
        blackhole.consume(state.coarse.peekLast());
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Group("coarse_most_read")
    @GroupThreads(2)
    public void coarseMostReadWrite(MyState state, Blackhole blackhole) {
        state.coarse.addFirst(34534);
        state.coarse.addLast(97844);
        blackhole.consume(state.coarse.removeFirst());
        blackhole.consume(state.coarse.removeLast());
    }
}

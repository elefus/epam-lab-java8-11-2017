package spliterator.part1.example.example1;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
public class WhileDoIntArraySpliteratorBenchmark {

    @Param({"100000000", "10000000"})
    public int length;

    public int[] data;

    @Setup
    public void setup() {
        data = ThreadLocalRandom.current().ints(length).toArray();
    }


    @Benchmark
    public long baselineSequential() {
        return Arrays.stream(data)
                     .sequential()
                     .asLongStream()
                     .sum();
    }

    @Benchmark
    public long baselineParallel() {
        return Arrays.stream(data)
                     .parallel()
                     .asLongStream()
                     .sum();
    }

    @Benchmark
    public long arrayIntSpliteratorSeq() {
        return StreamSupport.intStream(new WhileDoIntArraySpliterator(data), false)
                            .asLongStream()
                            .sum();
    }

    @Benchmark
    public long arrayIntSpliteratorParallel() {
        return StreamSupport.intStream(new WhileDoIntArraySpliterator(data), true)
                            .asLongStream()
                            .sum();
    }
}

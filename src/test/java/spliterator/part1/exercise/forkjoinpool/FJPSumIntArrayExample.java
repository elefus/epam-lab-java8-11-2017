package spliterator.part1.exercise.forkjoinpool;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class FJPSumIntArrayExample {

    @Test
    public void test() {
        int[] data = IntStream.generate(() -> 1)
                .limit(100)
                .toArray();

        // TODO реализация
        int result = new SumTask(data, 0, data.length).compute();
        assertEquals(100, result);
    }
}


class SumTask extends RecursiveTask<Integer> {

    private static final int SEQUENTAL_THRESHOLD = 10;
    private final int[] data;
    private final int from;
    private final int to;

    public SumTask(int[] data, int from, int to) {
        this.data = data;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from < SEQUENTAL_THRESHOLD) {
            return Arrays.stream(data).skip(from).limit(to - from).sum();
        }
        int midle = partition(from, to);
        SumTask left = new SumTask(data, from, midle);
        SumTask right = new SumTask(data, midle, to);
        left.fork();

        return right.compute() + left.join();
    }

    private int partition(int from, int to) {
        return (from + to) / 2;
    }
}
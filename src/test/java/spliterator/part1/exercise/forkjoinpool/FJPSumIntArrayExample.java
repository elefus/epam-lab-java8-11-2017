package spliterator.part1.exercise.forkjoinpool;

import org.junit.Test;
import spliterator.part1.example.forkjoinpool.FJPQuickSortExample;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class FJPSumIntArrayExample {
    private static int threadCount = 0;
    @Test
    public void test() {
        int[] data = IntStream.generate(() -> 1)
                              .limit(100)
                              .toArray();

        // TODO реализация
        int result = new ForkJoinPool().invoke(new ForkJoinSumArrayTask(data, 0, data.length - 1));
        assertEquals(100, result);
        System.out.println("threadCount = " + threadCount);
    }

    private static class ForkJoinSumArrayTask extends RecursiveTask<Integer> {
        private static final int SEQUENTIAL_THRESHOLD = 10;

        private int[] data;
        private int fromInclusive;
        private int toInclusive;

        public ForkJoinSumArrayTask(int[] data, int fromInclusive, int toInclusive) {
            this.data = data;
            this.fromInclusive = fromInclusive;
            this.toInclusive = toInclusive;
            threadCount++;
            System.out.println(Thread.currentThread());
        }

        @Override
        protected Integer compute() {
            if (toInclusive - fromInclusive < SEQUENTIAL_THRESHOLD) {
                return Arrays.stream(data).skip(fromInclusive).limit(toInclusive - fromInclusive + 1).sum();
            } else {
                int pivot = (fromInclusive + toInclusive)/2;
                FJPSumIntArrayExample.ForkJoinSumArrayTask left = new FJPSumIntArrayExample.ForkJoinSumArrayTask(data, fromInclusive, pivot);
                left.fork();

                FJPSumIntArrayExample.ForkJoinSumArrayTask right = new FJPSumIntArrayExample.ForkJoinSumArrayTask(data, pivot + 1, toInclusive);
                return left.join() + right.compute();
            }
        }
    }
}

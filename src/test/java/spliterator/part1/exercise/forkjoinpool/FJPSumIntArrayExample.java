package spliterator.part1.exercise.forkjoinpool;

import org.junit.Test;

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

        int result = (int) new ForkJoinPool().invoke(new ForkJoinQuicksortTask(data, 0, data.length - 1));
        assertEquals(100, result);
    }


    private static class ForkJoinQuicksortTask extends RecursiveTask {
        private static final int SEQUENTIAL_THRESHOLD = 20;

        private int[] data;
        private int fromInclusive;
        private int toInclusive;

        private ForkJoinQuicksortTask(int[] data, int fromInclusive, int toInclusive) {
            this.fromInclusive = fromInclusive;
            this.toInclusive = toInclusive;
            this.data = data;
        }

        @Override
        protected Object compute() {
            int sum = 0;
            if (toInclusive - fromInclusive < SEQUENTIAL_THRESHOLD) {
                for (int i = fromInclusive; i <= toInclusive; i++) {
                    sum += data[i];
                }
            } else {
                int pivot = (fromInclusive + toInclusive) / 2;
                ForkJoinQuicksortTask left = new ForkJoinQuicksortTask(data, fromInclusive, pivot);
                left.fork();

                ForkJoinQuicksortTask right = new ForkJoinQuicksortTask(data, pivot + 1, toInclusive);
                sum += (int) right.compute();
                sum += (int) left.join();
            }
            return sum;
        }
    }
}

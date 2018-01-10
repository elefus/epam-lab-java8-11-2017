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

        int result = new ForkJoinPool().invoke(new SumArrayTask(data, 0, data.length - 1));
        assertEquals(100, result);
    }

    private static class SumArrayTask extends RecursiveTask<Integer> {
        private static final int SEQUENTIAL_THRESHOLD = 5;

        private int[] data;
        private int fromInclusive;
        private int toInclusive;

        SumArrayTask(int[] data, int fromInclusive, int toInclusive) {
            this.fromInclusive = fromInclusive;
            this.toInclusive = toInclusive;
            this.data = data;
        }

        @Override
        protected Integer compute() {
            if (toInclusive - fromInclusive < SEQUENTIAL_THRESHOLD) {
                int res = 0;
                for (int i = fromInclusive; i <= toInclusive; i++) {
                    res += data[i];
                }
                return res;
            } else {
                int pivot = (fromInclusive + toInclusive) / 2;
                SumArrayTask left = new SumArrayTask(data, fromInclusive, pivot);
                SumArrayTask right = new SumArrayTask(data, pivot + 1, toInclusive);
                left.fork();
                return right.compute() + left.join();
            }
        }
    }
}

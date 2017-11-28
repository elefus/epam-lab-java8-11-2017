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


        int result = new ForkJoinPool().invoke(new ForkJoinSumArrayTask(data, 0, data.length));
        assertEquals(100, result);
    }

    private static class ForkJoinSumArrayTask extends RecursiveTask<Integer> {

        private static final int SEQUENTIAL_THRESHOLD = 30;

        private int[] data;
        private int fromInclusive;
        private int toExclusive;

        public ForkJoinSumArrayTask(int[] data, int fromInclusive, int toExclusive) {
            this.fromInclusive = fromInclusive;
            this.toExclusive = toExclusive;
            this.data = data;
        }

        @Override
        protected Integer compute() {
            if (toExclusive - fromInclusive < SEQUENTIAL_THRESHOLD) {
                return Arrays.stream(data)
                             .skip(fromInclusive)
                             .limit(toExclusive - fromInclusive)
                             .sum();
            }


            int mid = (fromInclusive + toExclusive) / 2;

            FJPSumIntArrayExample.ForkJoinSumArrayTask left = new FJPSumIntArrayExample.ForkJoinSumArrayTask(data, fromInclusive, mid);
            left.fork();


            return new FJPSumIntArrayExample.ForkJoinSumArrayTask(data, mid, toExclusive).compute() + left.join();
        }
    }
}




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
        int result = new ForkJoinPool().invoke(new ForkJoinPoolSumArrayTask(data, 0, data.length - 1));
        assertEquals(100, result);
    }

    private static class ForkJoinPoolSumArrayTask extends RecursiveTask<Integer>{

        private static final int SEQUENTIAL_THRESHOLD = 10;

        private int[] data;
        private int fromInclusive;
        private int toInclusive;

        public ForkJoinPoolSumArrayTask(int[] data, int fromInclusive, int toInclusive){
            this.data = data;
            this.fromInclusive = fromInclusive;
            this.toInclusive = toInclusive;
        }

        @Override
        protected Integer compute() {
            if(toInclusive - fromInclusive < SEQUENTIAL_THRESHOLD){

                return Arrays.stream(data).skip(fromInclusive).limit(toInclusive - fromInclusive + 1).sum();

            } else {

                int pivot = (fromInclusive + toInclusive)/2;
                FJPSumIntArrayExample.ForkJoinPoolSumArrayTask left = new FJPSumIntArrayExample.ForkJoinPoolSumArrayTask(data, fromInclusive, pivot);
                left.fork();

                FJPSumIntArrayExample.ForkJoinPoolSumArrayTask right = new FJPSumIntArrayExample.ForkJoinPoolSumArrayTask(data, pivot + 1, toInclusive);
                return left.join() + right.compute();
            }
        }
    }
}

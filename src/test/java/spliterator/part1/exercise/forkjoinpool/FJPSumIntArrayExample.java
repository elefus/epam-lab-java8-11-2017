package spliterator.part1.exercise.forkjoinpool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;

public class FJPSumIntArrayExample {

    private static class Asum extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 1000;

        int[] data;
        int lowBound;
        int highBound;

        Asum(int[] data) {
            this(data, 0, data.length - 1);
        }

        Asum(int[] data, int lowBound, int highBound) {
            this.data = data;
            this.lowBound = lowBound;
            this.highBound = highBound;
        }

        @Override
        protected Integer compute() {
            int result;

            if (lowBound == highBound) {
                result = data[lowBound];
            } else if (highBound - lowBound <= THRESHOLD) {
                result = 0;
                for (int i = lowBound; i<=highBound; i++)
                    result += data[i];
            }
            else {
                int mid = (lowBound + highBound) >> 1;

                Asum leftSUM = new Asum(data, lowBound, mid);
                Asum rightSUM = new Asum(data, mid + 1, highBound);

                rightSUM.fork();

                result = leftSUM.compute() + rightSUM.join();
            }

            return result;
        }
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        int[] data = IntStream.generate(() -> 1)
                              .limit(100)
                              .toArray();

        Asum asum = new Asum(data);
        ForkJoinPool.commonPool().invoke(asum);
        int result = asum.get();
        assertEquals(100, result);
    }
}

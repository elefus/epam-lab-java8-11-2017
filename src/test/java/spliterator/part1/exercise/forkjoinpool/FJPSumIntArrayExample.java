package spliterator.part1.exercise.forkjoinpool;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class FJPSumIntArrayExample {

    private static class Asum extends RecursiveAction {
        int[] data;
        int lowBound;
        int highBound;
        int sum;

        Asum(int[] data) {
            this(data, 0, data.length - 1);
        }

        Asum(int[] data, int lowBound, int highBound) {
            this.data = data;
            this.lowBound = lowBound;
            this.highBound = highBound;
        }

        @Override
        protected void compute() {
            if (lowBound == highBound) {
                sum = data[lowBound];
            } else if (lowBound > highBound) {
                sum = 0;
            } else {
                int mid = (lowBound + highBound) >> 1;

                Asum leftSUM = new Asum(data, lowBound, mid);
                Asum rightSUM = new Asum(data, mid + 1, highBound);

                leftSUM.fork();
                rightSUM.compute();
                leftSUM.join();

                sum = leftSUM.sum + rightSUM.sum;
            }
        }
    }

    @Test
    public void test() {
        int[] data = IntStream.generate(() -> 1)
                              .limit(100)
                              .toArray();

        Asum asum = new Asum(data);
        new ForkJoinPool().invoke(asum);
        assertEquals(100, asum.sum);
    }
}

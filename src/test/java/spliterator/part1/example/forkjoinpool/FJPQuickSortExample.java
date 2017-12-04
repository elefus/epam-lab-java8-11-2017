package spliterator.part1.example.forkjoinpool;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertArrayEquals;

public class FJPQuickSortExample {

    @Test
    public void test() {
        int[] data = ThreadLocalRandom.current()
                                      .ints(40, 0, 100)
                                      .toArray();

        int[] expected = Arrays.copyOf(data, data.length);
        Arrays.sort(expected);

        new ForkJoinPool().invoke(new ForkJoinQuicksortAction(data, 0, data.length - 1));
        assertArrayEquals(expected, data);
    }

    private static class ForkJoinQuicksortAction extends RecursiveAction {
        private static final int SEQUENTIAL_THRESHOLD = 100;

        private int[] data;
        private int fromInclusive;
        private int toInclusive;

        private ForkJoinQuicksortAction(int[] data, int fromInclusive, int toInclusive) {
            this.fromInclusive = fromInclusive;
            this.toInclusive = toInclusive;
            this.data = data;
        }

        @Override
        protected void compute() {
            if (toInclusive - fromInclusive < SEQUENTIAL_THRESHOLD) {
                Arrays.sort(data, fromInclusive, toInclusive + 1);
            } else {
                int pivot = partition(data, fromInclusive, toInclusive);
                ForkJoinQuicksortAction left = new ForkJoinQuicksortAction(data, fromInclusive, pivot);
                left.fork();

                ForkJoinQuicksortAction right = new ForkJoinQuicksortAction(data, pivot + 1, toInclusive);
                right.compute();
                left.join();
            }
        }

        private int partition(int[] array, int fromInclusive, int toExclusive) {
            int pivot = array[fromInclusive];
            int i = fromInclusive - 1;
            int j  = toExclusive + 1;
            while (true){
                do {
                    i++;
                }
                while (array[i] < pivot);

                do {
                    j--;
                }
                while (array[j] > pivot);

                if (i >= j) {
                    return j;
                }

                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
    }
}



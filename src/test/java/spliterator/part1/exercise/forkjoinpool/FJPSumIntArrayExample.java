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


        int result = new ForkJoinPool().invoke(new FJSumArrayTask(data, 0, 99));
        assertEquals(100, result);
    }

    private static class FJSumArrayTask extends RecursiveTask<Integer> {
        private static final int SEQUENTIAL_THRESHOLD = 30;
        int[] data;
        int from;
        int to;

        public FJSumArrayTask(int[] data, int from, int to){
            this.data = data;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Integer compute() {
            if ((to - from) < SEQUENTIAL_THRESHOLD){
                int result = 0;
                for (int i = from; i < to + 1; i++) {
                    result += data[i];
                }
                return result;

            }
            else {
                int partition = (to + from) / 2;
                FJSumArrayTask left = new FJSumArrayTask(data, from, partition);
                left.fork();

                FJSumArrayTask right = new FJSumArrayTask(data, partition + 1, to);
                return right.compute() + left.join();
            }

        }
    }
}

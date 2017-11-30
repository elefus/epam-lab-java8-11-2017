package spliterator.part1.exercise.forkjoinpool;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class FJPSumIntArrayExample {

    @Test
    public void test() {
        int[] data = IntStream.generate(() -> 1)
                              .limit(100)
                              .toArray();

        // TODO реализация
        int result = new ForkJoinPool().invoke(null);
        assertEquals(100, result);
    }
}

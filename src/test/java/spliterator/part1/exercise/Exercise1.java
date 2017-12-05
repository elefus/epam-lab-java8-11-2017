package spliterator.part1.exercise;

import org.junit.Test;

import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class Exercise1 {

    @Test
    public void testSumUsingSpliterator() {
        int numRows = 10;
        int numColumns = 5;

        int[][] data = Stream.generate(() -> IntStream.generate(() -> 1)
                                                      .limit(numColumns)
                                                      .toArray())
                             .limit(numRows)
                             .toArray(int[][]::new);

        IntStream stream = StreamSupport.intStream(new RectangleSpliterator(data), false);

        assertEquals(50, stream.sum());
    }

    @Test
    public void testMinUsingSpliterator() {
        int[][] data = Stream.generate(() -> IntStream.generate(() -> 42)
                                                      .limit(5)
                                                      .toArray())
                             .limit(10)
                             .toArray(int[][]::new);
        data[0][0] = 0;

        IntStream stream = StreamSupport.intStream(new RectangleSpliterator(data), false);

        assertEquals(0, stream.min().orElseThrow(IllegalStateException::new));
    }

    @Test
    public void testMaxUsingSpliterator() {
        int[][] data = Stream.generate(() -> IntStream.generate(() -> 42)
                                                      .limit(5)
                                                      .toArray())
                             .limit(10)
                             .toArray(int[][]::new);
        data[0][0] = 50;

        IntStream stream = StreamSupport.intStream(new RectangleSpliterator(data), false);

        assertEquals(50, stream.max().orElseThrow(IllegalStateException::new));
    }

    private static class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

        private final int[][] array;

        public RectangleSpliterator(int[][] array) {
            super(0, 0);
            throw new UnsupportedOperationException();
        }

        @Override
        public OfInt trySplit() {
            // TODO реализация
            throw new UnsupportedOperationException();
        }

        @Override
        public long estimateSize() {
            // TODO реализация
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            // TODO реализация
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(IntConsumer action) {
            // TODO реализация
            throw new UnsupportedOperationException();
        }
    }
}

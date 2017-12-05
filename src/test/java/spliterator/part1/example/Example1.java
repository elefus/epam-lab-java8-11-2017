package spliterator.part1.example;

import org.junit.Test;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class Example1 {

    @Test
    public void testSumUsingSpliterator() {
        int[] data = IntStream.generate(() -> 1)
                              .limit(10_000)
                              .toArray();

        IntStream stream = StreamSupport.intStream(new ArrayIntSpliterator(data), false);

        assertEquals(10_000, stream.sum());
    }

    @Test
    public void testSumUsingParallelSpliterator() {
        int[] data = IntStream.generate(() -> 1)
                              .limit(10_000)
                              .toArray();

        IntStream stream = StreamSupport.intStream(new ArrayIntSpliterator(data), true);

        assertEquals(10_000, stream.sum());
    }

    @Test
    public void testAverageUsingSpliterator() {
        int[] data = IntStream.generate(() -> 1)
                              .limit(10_000)
                              .toArray();

        IntStream stream = StreamSupport.intStream(new ArrayIntSpliterator(data), false);

        assertEquals(1.0, stream.average().orElseThrow(IllegalStateException::new), 0.001);
    }

    private static class ArrayIntSpliterator extends Spliterators.AbstractIntSpliterator {

        private static final int SEQUENTIAL_THRESHOLD = 30;


        private ArrayIntSpliterator(int[] array) {
            this(array, 0, array.length);
        }

        private ArrayIntSpliterator(int[] array, int fromInclusive, int toExclusive) {
            super(0, 0);
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public OfInt trySplit() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long estimateSize() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(IntConsumer action) {
            throw new UnsupportedOperationException();
        }
    }
}

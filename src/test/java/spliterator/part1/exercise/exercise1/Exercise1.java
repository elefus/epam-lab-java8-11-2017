package spliterator.part1.exercise.exercise1;

import org.junit.Test;

import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class Exercise1 {

    @Test
    public void testSumUsingSpliterator() {
        int[][] data = Stream.generate(() -> IntStream.generate(() -> 1)
                                                      .limit(5)
                                                      .toArray())
                             .limit(10)
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
        data[3][3] = 50;

        IntStream stream = StreamSupport.intStream(new RectangleSpliterator(data), false);

        assertEquals(50, stream.max().orElseThrow(IllegalStateException::new));
    }

    @Test
    public void testEstimateSize50() {
        int[][] data = Stream.generate(() -> IntStream.generate(() -> 1)
                                                      .limit(5)
                                                      .toArray())
                             .limit(10)
                             .toArray(int[][]::new);

        IntStream stream = StreamSupport.intStream(new RectangleSpliterator(data), false);

        assertEquals(50, stream.spliterator().estimateSize());
    }

    @Test
    public void testTrySplit() {
        int[][] data = Stream.generate(() -> IntStream.generate(() -> 1)
                                                      .limit(5)
                                                      .toArray())
                             .limit(10)
                             .toArray(int[][]::new);

        IntStream stream = StreamSupport.intStream(new RectangleSpliterator(data), false);
        Spliterator.OfInt spl = stream.spliterator();
        assertEquals(50, spl.estimateSize());

        Spliterator.OfInt splittedOnce = spl.trySplit();
        assertEquals(25, splittedOnce.estimateSize());
        assertEquals(25, spl.estimateSize());

        Spliterator.OfInt splittedTwice = splittedOnce.trySplit();
        assertEquals(12, splittedTwice.estimateSize());
        assertEquals(13, splittedOnce.estimateSize());

        Spliterator.OfInt splittedOnceAgain = splittedOnce.trySplit();
        assertEquals(6, splittedOnceAgain.estimateSize());
        assertEquals(7, splittedOnce.estimateSize());

        Spliterator.OfInt splittedTwiceAgain = splittedTwice.trySplit();
        assertEquals(6, splittedTwiceAgain.estimateSize());
        assertEquals(6, splittedTwice.estimateSize());
    }

    @Test
    public void testForEachRemaining() {
        int[][] data = Stream.generate(() -> IntStream.generate(() -> 1)
                                                      .limit(5)
                                                      .toArray())
                             .limit(10)
                             .toArray(int[][]::new);
        data[3][3] = 50;

        IntStream stream = StreamSupport.intStream(new RectangleSpliterator(data), false);
        Spliterator.OfInt spl = stream.spliterator();
        spl.tryAdvance((IntConsumer) i -> System.out.println("TryAdvance 1 i = " + i));
        spl.tryAdvance((IntConsumer) i -> System.out.println("TryAdvance 2 i = " + i));
        spl.forEachRemaining((IntConsumer) System.out::println);
        System.out.println("Est size = " + spl.estimateSize());
    }
}

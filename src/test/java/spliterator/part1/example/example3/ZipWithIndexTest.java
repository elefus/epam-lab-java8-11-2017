package spliterator.part1.example.example3;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ZipWithIndexTest {

    @Test
    public void indexedArraySpliterator() {
        String[] source = new String[]{"a", "b", "c", "d", "e", "f"};
        Stream<String> sourceStream = Arrays.stream(source);

        long result = new AdvancedStreamImpl<>(sourceStream).zipWithIndex()
                                                            .mapToLong(IndexedPair::getValue1)
                                                            .sum();

        assertEquals(15, result);
    }

    @Test
    public void intStreamRangeSequential() {
        Stream<Integer> sourceStream = IntStream.range(0, 1000)
                                                .boxed();

        long result = new AdvancedStreamImpl<>(sourceStream).zipWithIndex()
                                                            .mapToLong(IndexedPair::getValue1)
                                                            .sum();

        assertEquals(499_500, result);
    }

    @Test
    public void intStreamRangeParallel() {
        Stream<Integer> sourceStream = IntStream.range(0, 1000)
                                                .boxed()
                                                .parallel();

        long result = new AdvancedStreamImpl<>(sourceStream).zipWithIndex()
                                                            .mapToLong(IndexedPair::getValue1)
                                                            .sum();

        assertEquals(499_500, result);
    }
}

package spliterator.part1.example.example3;

import org.junit.Test;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ZipTest {

    @Test
    public void zipStringAndInteger() {
        Stream<String> a = Stream.of("a", "b", "c", "d", "e", "f");
        Stream<Integer> b = Stream.of(5, 4, 3, 2, 1, 0);

        String result = new AdvancedStreamImpl<>(a).zip(b)
                                                   .max(Comparator.comparingInt(Pair::getValue2))
                                                   .orElseThrow(IllegalStateException::new)
                                                   .getValue1();

        assertEquals("a", result);
    }

    @Test
    public void zipLongAndIntegerSequential() {
        Stream<Long> a = Stream.of(0L, 0L, 1L, 1L, 2L, 2L);
        Stream<Integer> b = Stream.of(5, 4, 3, 2, 1, 0);

        long result = new AdvancedStreamImpl<>(a).zip(b)
                                                 .mapToLong(pair -> 2 * pair.getValue1() + pair.getValue2())
                                                 .sum();

        assertEquals(27, result);
    }

    @Test
    public void zipIntegersParallel() {
        Stream<Integer> a = IntStream.range(0, 1000)
                                     .boxed()
                                     .parallel();
        Stream<Integer> b = IntStream.range(2000, 3000)
                                     .boxed();

        int result = new AdvancedStreamImpl<>(a).zip(b).filter(pair -> pair.getValue1() == 1500)
                                                .findFirst()
                                                .orElseThrow(IllegalStateException::new)
                                                .getValue2();

        assertEquals(2500, result);
    }
}

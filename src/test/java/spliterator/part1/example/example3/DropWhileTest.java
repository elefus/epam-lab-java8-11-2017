package spliterator.part1.example.example3;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class DropWhileTest {

    @Test
    public void dropLessThanC() {
        Stream<Character> sourceStream = Stream.of('a', 'b', 'c', 'd', 'e', 'f', 'g');

        long count = new AdvancedStreamImpl<>(sourceStream).dropWhile(elem -> elem < 'c')
                                                           .count();

        assertEquals(5, count);
    }
    @Test
    public void dropLessThanCUnsorted() {
        Stream<Character> sourceStream = Stream.of('a', 'b', 'd', 'a', 'a', 'a');

        long count = new AdvancedStreamImpl<>(sourceStream).dropWhile(elem -> elem < 'c')
                                                           .count();

        assertEquals(4, count);
    }
}

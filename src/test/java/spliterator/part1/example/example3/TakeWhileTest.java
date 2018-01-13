package spliterator.part1.example.example3;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TakeWhileTest {

    @Test
    public void takeLessThanD() {
        Stream<Character> sourceStream = Stream.of('a', 'b', 'c', 'd', 'e', 'f');

        long count = new AdvancedStreamImpl<>(sourceStream).takeWhile(elem -> elem < 'd')
                                                           .count();

        assertEquals(3, count);
    }

    @Test
    public void takeLessThanDUnsorted() {
        Stream<Character> sourceStream = Stream.of('a', 'd', 'b', 'c', 'e', 'f');

        long count = new AdvancedStreamImpl<>(sourceStream).takeWhile(elem -> elem < 'd')
                                                           .count();

        assertEquals(1, count);
    }
}

package spliterator.part1.example.example2;

import org.junit.Test;

import static org.junit.Assert.*;

public class SumTwiceTest {

    @Test
    public void sumTwice() {
        assertEquals(100000010000000L, SumTwiceNaiveBenchmark.sumTwice(SumTwiceNaiveBenchmark.UPPER_BOUND));
    }

    @Test
    public void sumTwiceStream() {
        assertEquals(100000010000000L, SumTwiceNaiveBenchmark.sumTwiceStream(SumTwiceNaiveBenchmark.UPPER_BOUND));
    }
}
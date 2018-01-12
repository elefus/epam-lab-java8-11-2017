package spliterator.part1.example.example2;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SumTwicePerfect {

    static final int UPPER_BOUND = 10_000_000;

    public static void main(String[] args) {
        long startSimple = System.nanoTime();
        long resultSimple = sumTwicePerfectly(UPPER_BOUND);
        System.out.println("Simple result = " + resultSimple + ", time = " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startSimple));
    }

    static long sumTwicePerfectly(int upperBound) {
        return upperBound * (upperBound + 1L);
    }
}

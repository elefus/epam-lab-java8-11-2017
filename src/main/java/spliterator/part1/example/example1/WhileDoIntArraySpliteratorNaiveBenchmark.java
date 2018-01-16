package spliterator.part1.example.example1;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.StreamSupport;

public class WhileDoIntArraySpliteratorNaiveBenchmark {

    public static void main(String[] args) {
        int[] data = ThreadLocalRandom.current().ints(100_000_000).toArray();

        long start = System.currentTimeMillis();
        long result = StreamSupport.intStream(new WhileDoIntArraySpliterator(data), false)
                                   .asLongStream()
                                   .sum();
        System.out.println(result + " - " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        result = 0;
        for (int value : data) {
            result += value;
        }
        System.out.println(result + " - " + (System.currentTimeMillis() - start));
    }
}

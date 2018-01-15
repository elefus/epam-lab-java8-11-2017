package spliterator.part1.example.example1;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class IntArraySpliterator extends Spliterators.AbstractIntSpliterator {

    private final int[] data;
    private int startInclusive;
    private int endExclusive;

    public IntArraySpliterator(int[] array) {
        this(array, 0, array.length);
    }

    private IntArraySpliterator(int[] array, int startInclusive, int endExclusive) {
        super(array.length, Spliterator.IMMUTABLE | Spliterator.ORDERED | Spliterator.SIZED | Spliterator.NONNULL);
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        data = array;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (startInclusive == endExclusive) {
            return false;
        }
        action.accept(data[startInclusive++]);
        return true;
    }

    @Override
    public long estimateSize() {
        return endExclusive - startInclusive;
    }

    @Override
    public OfInt trySplit() {
        int mid = startInclusive + (int)(estimateSize() / 2);
        return new IntArraySpliterator(data, startInclusive, startInclusive = mid);
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        while (startInclusive != endExclusive) {
            action.accept(data[startInclusive++]);
        }
    }
}

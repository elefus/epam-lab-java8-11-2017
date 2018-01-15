package spliterator.part1.exercise.exercise1;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private int[][] data;
    private int startInclusive;
    private int endExclusive;

    private RectangleSpliterator(int[][] data, int startInclusive, int endExclusive) {
        super(endExclusive - startInclusive,
                Spliterator.IMMUTABLE | Spliterator.ORDERED | Spliterator.SIZED | Spliterator.NONNULL);
        this.data = data;
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
    }

    RectangleSpliterator(int[][] data) {
        this(data, 0, size(data));
    }

    @Override
    public OfInt trySplit() {
        int mid = startInclusive + (endExclusive - startInclusive) / 2;
        int leftStart = startInclusive;
        startInclusive += mid;
        return new RectangleSpliterator(data, leftStart, startInclusive);
    }

    @Override
    public long estimateSize() {
        return endExclusive - startInclusive;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (startInclusive >= endExclusive) {
            return false;
        }
        action.accept(at(data, startInclusive++));
        return true;
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        for (int i = startInclusive; i < endExclusive; i++) {
            action.accept(at(data, i));
        }
    }

    private static int size(int[][] data) {
        int size = 0;
        for (int[] row : data) {
            size += row.length;
        }
        return size;
    }

    private static int at(int[][] data, int num) {
        int i;
        for (i = 0; num >= data[i].length; i++) {
            num -= data[i].length;
        }
        return data[i][num];
    }
}

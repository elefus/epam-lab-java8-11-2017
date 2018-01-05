package spliterator.part1.exercise.exercise1;

import lombok.AllArgsConstructor;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    @AllArgsConstructor
    private static class Pair {
        private final int[][] data;
        int _1;
        int _2;

        void inc() {
            _2++;
            if (_2 == data[_1].length) {
                _1++;
                _2 = 0;
            }
        }

        boolean moreThan(Pair other) {
            return _1 > other._1 || _1 == other._1 && _2 > other._2;
        }
    }

    private final int[][] data;
    private final Pair start;
    private final Pair end;

    private RectangleSpliterator(int[][] data, Pair start, Pair end) {
        super(calculateSize(data),
                Spliterator.IMMUTABLE | Spliterator.ORDERED | Spliterator.SIZED | Spliterator.NONNULL);
        this.data = data;
        this.start = start;
        this.end = end;
    }

    RectangleSpliterator(int[][] data) {
        this(data, new Pair(data,0, 0), new Pair(data,data.length - 1, data[data.length - 1].length - 1));
    }

    @Override
    public OfInt trySplit() {
        long shift = estimateSize() / 2;
        Pair oldStart = new Pair(data, start._1, start._2);
        for (int i = 0; i < shift - 1; i++) {
            start.inc();
        }
        OfInt left = new RectangleSpliterator(data, oldStart, start);
        start.inc();
        return left;
    }

    @Override
    public long estimateSize() {
        return calculateSize(data, start, end);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (start.moreThan(end)) {
            return false;
        }
        action.accept(data[start._1][start._2]);
        start.inc();
        return true;
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        //noinspection StatementWithEmptyBody
        do {} while (tryAdvance(action));
    }

    private static long calculateSize(int[][] data, Pair start, Pair end) {
        long size = 0;
        if (start._1 < end._1) {
            size += data[start._1].length - start._2;
            for (int i = start._1 + 1; i < end._1; i++) {
                size += data[i].length;
            }
            size += end._2 + 1;
        } else {
            size += end._2 - start._2 + 1;
        }
        return size;
    }

    private static long calculateSize(int[][] data) {
        return calculateSize(data, new Pair(data,0, 0),
                new Pair(data,data.length - 1, data[data.length - 1].length - 1));
    }

}

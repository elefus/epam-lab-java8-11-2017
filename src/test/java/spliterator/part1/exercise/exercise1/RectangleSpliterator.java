package spliterator.part1.exercise.exercise1;

import lombok.AllArgsConstructor;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    @AllArgsConstructor
    private static class MatrixPointer {
        private final int[][] data;
        int row;
        int col;

        void inc(int inc) {
            col += inc;
            int rowLength;
            while (row < data.length && col >= (rowLength = data[row].length)) {
                col -= rowLength;
                row++;
            }
        }

        void inc() {
            inc(1);
        }

        boolean moreThan(MatrixPointer other) {
            return row > other.row || row == other.row && col > other.col;
        }
    }

    private final int[][] data;
    private final MatrixPointer start;
    private final MatrixPointer end;

    private RectangleSpliterator(int[][] data, MatrixPointer start, MatrixPointer end) {
        super(calculateSize(data),
                Spliterator.IMMUTABLE | Spliterator.ORDERED | Spliterator.SIZED | Spliterator.NONNULL);
        this.data = data;
        this.start = start;
        this.end = end;
    }

    RectangleSpliterator(int[][] data) {
        this(data, new MatrixPointer(data, 0, 0), new MatrixPointer(data, data.length - 1, data[data.length - 1].length - 1));
    }

    @Override
    public OfInt trySplit() {
        long shift = estimateSize() / 2;
        MatrixPointer oldStart = new MatrixPointer(data, start.row, start.col);
        start.inc((int) shift - 1);
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
        action.accept(data[start.row][start.col]);
        start.inc();
        return true;
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        while (!start.moreThan(end)) {
            action.accept(data[start.row][start.col]);
            start.inc();
        }
    }

    private static long calculateSize(int[][] data, MatrixPointer start, MatrixPointer end) {
        long size = 0;
        if (start.row < end.row) {
            size += data[start.row].length - start.col;
            for (int i = start.row + 1; i < end.row; i++) {
                size += data[i].length;
            }
            size += end.col + 1;
        } else {
            size += end.col - start.col + 1;
        }
        return size;
    }

    private static long calculateSize(int[][] data) {
        return calculateSize(data, new MatrixPointer(data, 0, 0),
                new MatrixPointer(data, data.length - 1, data[data.length - 1].length - 1));
    }

}

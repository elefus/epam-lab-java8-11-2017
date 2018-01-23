package spliterator.part1.exercise;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {
    private final int[][] array;
    private final Element start;
    private final Element end;

    private static class Element {
        final int[][] array;
        int row;
        int col;

        Element(int[][] array, int row, int col) {
            this.array = array;
            this.row = row;
            this.col = col;
        }

        void increment(int number) {
            col += number;
            while (row < array.length && col >= array[row].length) {
                col -= array[row].length;
                row++;
            }
        }

        void increment() {
            increment(1);
        }
        boolean after(Element secondElement) {
            return row > secondElement.row;
        }
    }

    public RectangleSpliterator(int[][] array) {
        this(array, new Element(array, 0, 0), new Element(array, array.length - 1, array[array.length - 1].length - 1));
    }

    private RectangleSpliterator(int[][] array, Element start, Element end) {
        super(calculateSize(array), Spliterator.IMMUTABLE | Spliterator.SIZED | Spliterator.NONNULL | Spliterator.ORDERED);
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public OfInt trySplit() {
        long rightSpliteratorStart = estimateSize() / 2;
        start.increment((int) rightSpliteratorStart);
        return new RectangleSpliterator(array, new Element(array, start.row, start.col), start);
    }

    @Override
    public long estimateSize() {
        return calculateSize(array);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (start.after(end)) {
            return false;
        }
        action.accept(array[start.row][start.col]);
        start.increment();
        return true;
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        while (!start.after(end)) {
            action.accept(array[start.row][start.col]);
            start.increment();
        }
    }

    private static int calculateSize(int[][] array) {
        return calculateSize(array, new Element(array, 0, 0),
                new Element(array, array.length - 1, array[array.length - 1].length - 1));
    }

    private static int calculateSize(int[][] array, Element start, Element end) {
        int size = 0;

        for (int i = start.row; i < end.row; i++) {
            size += array[i].length;
        }

        size += end.col + 1;
        return size;
    }
}

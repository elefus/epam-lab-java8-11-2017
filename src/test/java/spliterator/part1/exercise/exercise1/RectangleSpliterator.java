package spliterator.part1.exercise.exercise1;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private final int[][] data;
    private Coordinate first;
    private Coordinate last;

    private static final class Coordinate {

        private final int[][] data;
        private int line;
        private int column;

        Coordinate(int[][] data, int line, int column) {
            this.data = data;
            this.line = line;
            this.column = column;
        }

        int[][] getData() {
            return data;
        }

        Coordinate moveToNext() {
            column++;
            if (column == data[line].length) {
                column = 0;
                line++;
            }
            return this;
        }

        boolean exceeds(Coordinate another) {
            return (this.line > another.line) || (this.line == another.line && this.column > another.column);
        }

        static Coordinate getVeryFirst(int[][] array) {
            return new Coordinate(array, 0, 0);
        }

        static Coordinate getVeryLast(int[][] array) {
            return new Coordinate(array, array.length - 1, array[array.length - 1].length - 1);
        }
    }

    public RectangleSpliterator(int[][] array) {
        this(array, Coordinate.getVeryFirst(array), Coordinate.getVeryLast(array));
    }

    public RectangleSpliterator(int[][] array, Coordinate first, Coordinate last) {
        super(findNumberOfElements(first, last),
                Spliterator.IMMUTABLE |
                        Spliterator.ORDERED |
                        Spliterator.SIZED |
                        Spliterator.NONNULL);
        this.first = first;
        this.last = last;
        this.data = array;
    }

    @Override
    public OfInt trySplit() {
        long halfQuantity = findNumberOfElements(first, last) / 2L;
        Coordinate initialFirst = new Coordinate(data, first.line, first.column);
        for (int i = 1; i < halfQuantity; i++) first.moveToNext();
        OfInt result = new RectangleSpliterator(data, initialFirst, new Coordinate(data, first.line, first.column));
        first.moveToNext();
        return result;
    }

    @Override
    public long estimateSize() {
        return findNumberOfElements(first, last);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (first.exceeds(last)) return false;
        action.accept(data[first.line][first.column]);
        first.moveToNext();
        return true;
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        while (tryAdvance(action)) {
        }
    }

    private static long findNumberOfElements(Coordinate first, Coordinate last) {
        if (first.line > last.line) return 0L;
        if (first.line == last.line) return first.column <= last.column ? (last.column - first.column + 1) : 0L;
        int[][] array = first.getData();
        long number = array[first.line].length - first.column + last.column + 1;
        for (int i = first.line + 1; i < last.line; i++) number += array[i].length;
        return number;
    }
}

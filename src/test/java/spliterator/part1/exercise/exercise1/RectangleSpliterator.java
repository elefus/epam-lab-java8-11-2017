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
        private int row;
        private int column;

        Coordinate(int[][] data, int row, int column) {
            this.data = data;
            this.row = row;
            this.column = column;
        }

        int[][] getData() {
            return data;
        }

        Coordinate moveToNext() {
            column++;
            if (column == data[row].length) {
                column = 0;
                row++;
            }
            return this;
        }

        boolean exceeds(Coordinate another) {
            return (this.row > another.row) || (this.row == another.row && this.column > another.column);
        }

        static Coordinate getVeryFirst(int[][] array) {
            return new Coordinate(array, 0, 0);
        }

        static Coordinate getVeryLast(int[][] array) {
            return new Coordinate(array, array.length - 1,array[array.length - 1].length - 1);
        }
    }


    private RectangleSpliterator(int[][] array, Coordinate first, Coordinate last) {
        super(findNumberOfElements(first, last),
                Spliterator.IMMUTABLE |
                        Spliterator.ORDERED |
                        Spliterator.SIZED |
                        Spliterator.NONNULL);
        this.first = first;
        this.last = last;
        this.data = array;
    }

    public RectangleSpliterator(int[][] array) {
        this(array, Coordinate.getVeryFirst(array), Coordinate.getVeryLast(array));
    }

    @Override
    public OfInt trySplit() {
        long halfQuantity = findNumberOfElements(first, last) / 2L;
        Coordinate initialFirst = new Coordinate(data, first.row, first.column);
        for (int i = 1; i < halfQuantity; i++) first.moveToNext();
        OfInt result = new RectangleSpliterator(data, initialFirst, new Coordinate(data, first.row, first.column));
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
        action.accept(data[first.row][first.column]);
        first.moveToNext();
        return true;
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        while (tryAdvance(action)) {}
    }

    private static long findNumberOfElements(Coordinate first, Coordinate last) {
        if (first.row > last.row) return 0L;
        if (first.row == last.row) return first.column <= last.column ? (last.column - first.column + 1) : 0L;
        int[][] array = first.getData();
        long number = array[first.row].length - first.column + last.column + 1;
        for (int i = first.row + 1; i < last.row; i++) number += array[i].length;
        return number;
    }
}

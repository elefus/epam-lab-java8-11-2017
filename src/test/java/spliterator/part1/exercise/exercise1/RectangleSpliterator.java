package spliterator.part1.exercise.exercise1;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private static class Position {

        private int row;
        private int column;
        private final int rowLength;

        Position(Position another) {
            row = another.row;
            column = another.column;
            rowLength = another.rowLength;
        }

        Position(int row, int column, int rowLength) {
            this.row = row;
            this.column = column;
            this.rowLength = rowLength;
        }

        void move(int positions) {
            column += positions;
            row += column / rowLength;
            column %= rowLength;
        }

        void move() {
            move(1);
        }

        int movesTo(Position another) {
            return (another.row - row) * rowLength + another.column - column + 1;
        }
    }

    private final int[][] data;
    private Position first;
    private Position last;

    public RectangleSpliterator(int[][] array) {
        this(
                array,
                new Position(0, 0, array[0].length),
                new Position(array.length - 1, array[array.length - 1].length - 1,array[0].length)
        );
    }

    private RectangleSpliterator(int[][] data, Position current, Position last) {
        super(
                current.movesTo(last),
        Spliterator.IMMUTABLE |
                Spliterator.ORDERED |
                Spliterator.SIZED |
                Spliterator.NONNULL
        );
        this.first = current;
        this.last = last;
        this.data = data;
    }

    @Override
    public OfInt trySplit() {
        Position mid = new Position(first);
        mid.move(first.movesTo(last)/2);
        RectangleSpliterator half = new RectangleSpliterator(data, first, mid);
        first = mid;
        return half;
    }

    @Override
    public long estimateSize() {
        return first.movesTo(last);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (first.row == last.row && first.column == last.column) {
            return false;
        }
        action.accept(data[first.row][first.column]);
        first.move();
        return true;
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        int movesToLast = first.movesTo(last);
        for (int i = 0; i < movesToLast; ++i) {
            action.accept(data[first.row][first.column]);
            first.move();
        }
    }
}
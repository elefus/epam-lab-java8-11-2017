package spliterator.part1.exercise.exercise1;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private final int[][] source;
    private Index currentIndex;
    private Index lastIndex;

    private static class Index implements Cloneable {
        private int[][] source;
        private int x;
        private int y;
        private boolean canIncrement;
        int rowLength;
        int colLength;

        Index(int[][] array, int x, int y) {
            this.source = array;
            this.x = x;
            this.y = y;
            colLength = source.length;
            rowLength = source[0].length;
            canIncrement = x < rowLength && y < colLength;
        }

        boolean canIncrement() {
            return canIncrement;
        }

        void increment() {
            if (x == rowLength - 1) {
                x = 0;
                y++;
            } else {
                x++;
            }
            if (y == colLength) {
                canIncrement = false;
            }
        }

        Index median(Index lastIndex) {
            int offset = (lastIndex.diff(this) + 1) / 2;
            int newX = (x + offset - 1) % rowLength;
            int newY = y + (x + offset - 1) / rowLength;
            return new Index(source, newX, newY);
        }

        int diff(Index fromIndex) {
            return x - fromIndex.x + rowLength * (y - fromIndex.y);
        }

        @Override
        @SneakyThrows
        final protected Object clone() {
            return super.clone();
        }
    }

    public RectangleSpliterator(@NonNull int[][] array) {
        this(array, new Index(array, 0, 0), new Index(array, array[0].length - 1, array.length - 1));
    }

    public RectangleSpliterator(@NonNull int[][] array, Index from, Index to) {
        super(to.diff(from) + 1, IMMUTABLE|NONNULL|SIZED|SUBSIZED);
        this.source = array;
        this.currentIndex = (Index) from.clone();
        this.lastIndex = (Index) to.clone();
    }

    @Override
    public OfInt trySplit() {
        Index medianIndex = currentIndex.median(lastIndex);
        RectangleSpliterator newSpliterator = new RectangleSpliterator(source, currentIndex, medianIndex);
        currentIndex = (Index) medianIndex.clone();
        currentIndex.increment();
        return newSpliterator;
    }

    @Override
    public long estimateSize() {
        return lastIndex.diff(currentIndex) + 1;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (currentIndex.canIncrement()) {
            action.accept(source[currentIndex.y][currentIndex.x]);
            currentIndex.increment();
            return true;
        } else {
            return false;
        }
    }

//    @Override
//    public void forEachRemaining(IntConsumer action) {
//        // TODO реализация
//        throw new UnsupportedOperationException();
//    }
}

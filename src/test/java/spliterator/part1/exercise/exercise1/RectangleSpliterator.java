package spliterator.part1.exercise.exercise1;

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    public RectangleSpliterator(int[][] array) {
        super(0, 0);
        throw new UnsupportedOperationException();
    }

    @Override
    public OfInt trySplit() {
        // TODO реализация
        throw new UnsupportedOperationException();
    }

    @Override
    public long estimateSize() {
        // TODO реализация
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        // TODO реализация
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        // TODO реализация
        throw new UnsupportedOperationException();
    }
}

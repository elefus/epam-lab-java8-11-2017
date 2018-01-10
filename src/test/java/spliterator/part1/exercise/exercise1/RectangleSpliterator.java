package spliterator.part1.exercise.exercise1;

import java.util.Arrays;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private int[][] data;
    private int index;
    private int fence;

    public RectangleSpliterator(int[][] array) {
        super(array.length, 0);

        this.data = array;
        this.index = 0;
        this.fence = array.length;
    }

    @Override
    public OfInt trySplit() {
        throw new UnsupportedOperationException("We don't use this spliterator in parallel stream");
    }

    @Override
    public long estimateSize() {
        return data.length;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (index >= fence)
            return false;

        IntStream stream = Arrays.stream(data[index++]);
        stream.forEach(action);
        return true;
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        super.forEachRemaining(action);
    }
}

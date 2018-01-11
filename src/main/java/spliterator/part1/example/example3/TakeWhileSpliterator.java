package spliterator.part1.example.example3;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TakeWhileSpliterator<T> extends Spliterators.AbstractSpliterator<T> {

    private final Spliterator<T> source;
    private final Predicate<? super T> predicate;

    public TakeWhileSpliterator(Spliterator<T> source, Predicate<? super T> predicate) {
        super(source.estimateSize(), source.characteristics());
        this.predicate = predicate;
        this.source = source;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        throw new UnsupportedOperationException();
    }
}

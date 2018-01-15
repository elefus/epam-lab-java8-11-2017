package spliterator.part1.example.example3;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DropWhileSpliterator<T> extends Spliterators.AbstractSpliterator<T> {

    private final Spliterator<T> source;
    private final Predicate<? super T> predicate;
    private boolean ifPredicateWasFalseOnce = false;

    public DropWhileSpliterator(Spliterator<T> source, Predicate<? super T> predicate) {
        super(source.estimateSize(), source.characteristics());
        this.predicate = predicate;
        this.source = source;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        Consumer<T> dropDownAction = t -> {
            if (ifPredicateWasFalseOnce || !predicate.test(t)) {
                ifPredicateWasFalseOnce = true;
                action.accept(t);
            }
        };
        return source.tryAdvance(dropDownAction);
    }
}

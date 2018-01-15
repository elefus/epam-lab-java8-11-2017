package spliterator.part1.example.example3;

import java.util.function.Predicate;
import java.util.stream.Stream;

// Stream<String> a = {A B C D E...}
// Stream<String> b = {QW ER TY UI II...}
public interface AdvancedStream<T> extends Stream<T> {

    // zipped = a.zip(b);
    // zipped == {(A, QW) (B, ER) (C, TY) ...}
    AdvancedStream<IndexedPair<T>> zipWithIndex();

    // indexed = a.zipWithIndex();
    // indexed == {(1, A) (2, B) (3, C) ...}
    <U> AdvancedStream<Pair<T, U>> zip(Stream<U> other);

    // lessD = a.takeWhile(elem -> elem < 'D');
    // lessD == {A, B, C}
    AdvancedStream<T> takeWhile(Predicate<? super T> predicate);

    // greaterC = a.dropWhile(elem -> elem < 'C');
    // greaterC == {D, E...}
    AdvancedStream<T> dropWhile(Predicate<? super T> predicate);
}

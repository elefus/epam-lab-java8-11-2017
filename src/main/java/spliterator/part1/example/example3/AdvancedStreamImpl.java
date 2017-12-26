package spliterator.part1.example.example3;

import lombok.Data;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@Data
public class AdvancedStreamImpl<T> implements AdvancedStream<T> {

    private final Stream<T> stream;

    @Override
    public AdvancedStream<IndexedPair<T>> zipWithIndex() {
        return new AdvancedStreamImpl<>(StreamSupport.stream(new IndexedSpliterator<>(stream.spliterator()), stream.isParallel()));
    }

    @Override
    public <U> AdvancedStream<Pair<T, U>> zip(Stream<U> other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AdvancedStream<T> takeWhile(Predicate<? super T> predicate) {
        List<T> listFromStream = stream.collect(Collectors.toList());
        List<T> result = new ArrayList<>(listFromStream.size());
        for (T t : listFromStream) {
            if (predicate.test(t)) {
                result.add(t);
            } else {
                break;
            }
        }
        return new AdvancedStreamImpl<>(result.stream());
    }

    @Override
    public AdvancedStream<T> dropWhile(Predicate<? super T> predicate) {
        List<T> listFromStream = stream.collect(Collectors.toList());
        List<T> result = new ArrayList<>(listFromStream.size());
        Iterator<T> iter = listFromStream.iterator();
        while (iter.hasNext()) {
            T elem = iter.next();
            if (!predicate.test(elem)) {
                result.add(elem);
                while (iter.hasNext()) {
                    result.add(iter.next());
                }
            }
        }
        return new AdvancedStreamImpl<>(result.stream());
    }

    @Override
    public void close() {
        stream.close();
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return stream.onClose(closeHandler);
    }

    @Override
    public Stream<T> unordered() {
        return stream.unordered();
    }

    @Override
    public Stream<T> parallel() {
        return stream.parallel();
    }

    @Override
    public Stream<T> sequential() {
        return stream.sequential();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public Spliterator<T> spliterator() {
        return stream.spliterator();
    }

    @Override
    public Iterator<T> iterator() {
        return stream.iterator();
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return stream.filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return stream.map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return stream.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return stream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return stream.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return stream.flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    @Override
    public Stream<T> distinct() {
        return stream.distinct();
    }

    @Override
    public Stream<T> sorted() {
        return stream.sorted();
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return stream.sorted(comparator);
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return stream.peek(action);
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return stream.limit(maxSize);
    }

    @Override
    public Stream<T> skip(long n) {
        return stream.skip(n);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        stream.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return stream.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return stream.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return stream.collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return stream.min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return stream.max(comparator);
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return stream.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return stream.findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return stream.findAny();
    }
}

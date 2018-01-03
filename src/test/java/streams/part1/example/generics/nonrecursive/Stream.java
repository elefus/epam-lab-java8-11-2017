package streams.part1.example.generics.nonrecursive;

public interface Stream<T> extends BaseStream<T> {

    Stream<T> distinct();
    Stream<T> sorted();
}
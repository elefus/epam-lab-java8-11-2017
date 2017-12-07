package streams.part1.example.generics.nonrecursive;

public interface BaseStream<T> {

    BaseStream<T> sequential();
    BaseStream<T> parallel();
}

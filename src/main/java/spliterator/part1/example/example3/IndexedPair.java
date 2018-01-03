package spliterator.part1.example.example3;

public class IndexedPair<T> extends Pair<Long, T> {

    public IndexedPair(Long index, T value) {
        super(index, value);
    }

    @Override
    public String toString() {
        return "[" + getValue1() + "] = " + getValue2();
    }
}

package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise3 {

    private static class LazyMapHelper<T, R> {

        private final List<T> source;
        private final Function<T, R> map;

        public LazyMapHelper(List<T> source, Function<T, R> map) {
            this.source = source;
            this.map = map;
        }

        public static <T> LazyMapHelper<T, T> from(List<T> list) {
            return new LazyMapHelper<>(list, t -> t);
        }

        public List<R> force() {
            return source.stream()
                    .map(this.map)
                    .collect(Collectors.toList());
        }

        public <R2> LazyMapHelper<T, R2> map(Function<R, R2> mapping) {
            return new LazyMapHelper<>(
                    this.source,
                    this.map.andThen(mapping)
            );
        }

        public List<R> getMapped() {
            return source.stream().map(this.map).collect(Collectors.toList());
        }
    }

    @Test
    public void mapEmployeesToLengthOfTheirFullNamesUsingLazyMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> lengths = LazyMapHelper.from(employees)
                .map(Employee::getPerson)
                .map(Person::getFullName)
                .map(String::length)
                .getMapped();

        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}

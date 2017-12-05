package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise3 {

    private static class LazyMapHelper<T, R> {

        private final List<T> source;
        private final Function<T, R> func;

        public LazyMapHelper(List<T> list, Function<T, R> func) {
            this.source = list;
            this.func = func;
        }

        public static <T> LazyMapHelper<T, T> from(List<T> list) {
            // TODO реализация
            return new LazyMapHelper<>(list, f -> f);
        }

        public List<R> force() {
            // TODO реализация
            List<R> newList = new ArrayList<>();
            source.forEach(elem -> newList.add(func.apply(elem)));

            return newList;
        }

        public <R2> LazyMapHelper<T, R2> map(Function<R, R2> mapping) {
            // TODO реализация3
            return new LazyMapHelper<>(source, func.andThen(mapping));
        }
    }

    @Test
    public void mapEmployeesToLengthOfTheirFullNamesUsingLazyMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> lengths = LazyMapHelper.from(employees)
                                             .map(Employee::getPerson)
                                             .map(Person::getFullName)
                                             .map(String::length)
                                             .force();
        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}

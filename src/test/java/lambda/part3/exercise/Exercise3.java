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
        private final Function<T, R> rule;

        private LazyMapHelper(List<T> source, Function<T, R> rule) {
            this.source = source;
            this.rule = rule;
        }


        static <T> LazyMapHelper<T, T> from(List<T> list) {
            return new LazyMapHelper<>(list, l -> l);
        }

        List<R> force() {
            List<R> newSource = new ArrayList<>();
            for (T s : source) {
                newSource.add(rule.apply(s));
            }
            return newSource;
        }

        public <R2> LazyMapHelper<T, R2> map(Function<R, R2> mapping) {
            return new LazyMapHelper<>(source, rule.andThen(mapping));
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

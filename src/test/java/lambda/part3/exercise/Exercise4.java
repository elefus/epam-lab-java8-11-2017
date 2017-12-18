package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyFlatMapHelper<T, R> {

        private final List<T> source;
        private final Function<T, List<R>> rule;

        private LazyFlatMapHelper(List<T> source, Function<T, List<R>> rule) {
            this.source = source;
            this.rule = rule;
        }

        static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            return new LazyFlatMapHelper<>(list, Arrays::asList);
        }

        <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            return new LazyFlatMapHelper<>(source, compose(rule, flatMapping));
        }

        <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(source, compose(rule, t -> Collections.singletonList(mapping.apply(t))));
        }

        private <U> Function<T, List<U>> compose(Function<T, List<R>> function1, Function<R, List<U>> function2) {
            return list -> {
                List<U> result = new ArrayList<>();
                function1.apply(list).forEach(r -> result.addAll(function2.apply(r)));
                return result;
            };
        }

        List<R> force() {
            List<R> newSource = new ArrayList<>();
            for (T s : source) {
                newSource.addAll(rule.apply(s));
            }
            return newSource;
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyFlatMapHelper.from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(string -> {
                    List<Character> characters = new ArrayList<>();
                    for (char c : string.toCharArray()) {
                        characters.add(c);
                    }
                    return characters;
                })
                .map(c -> (int) c)
                .force();
        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev"), codes);
    }

    private static List<Integer> calcCodes(String... strings) {
        List<Integer> codes = new ArrayList<>();
        for (String string : strings) {
            for (char letter : string.toCharArray()) {
                codes.add((int) letter);
            }
        }
        return codes;
    }
}

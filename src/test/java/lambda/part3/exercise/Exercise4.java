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

        //map
        <R2> LazyFlatMapHelper<T, R2> flatMap(Function<R, List<R2>> flatMapping) {
            return new LazyFlatMapHelper<>(source, compose(rule, flatMapping));
        }
        //flatMap
        <R2> LazyFlatMapHelper<T, R2> map(Function<R, R2> mapping) {
            return new LazyFlatMapHelper<>(source, compose(rule, t -> Collections.singletonList(mapping.apply(t))));
        }
        //compose
        private <R2> Function<T, List<R2>> compose(Function<T, List<R>> function1, Function<R, List<R2>> function2) {
            return list -> iterationHelper(function1.apply(list), function2);
        }
        //force
        List<R> force() {
            return iterationHelper(source, rule);
        }
        //iterationHelper for compose and force
        private <R3, R4> List<R4> iterationHelper(final List<R3> list, Function<R3, List<R4>> function) {
            List<R4> newList = new ArrayList<>();
            list.forEach(s -> newList.addAll(function.apply(s)));
            return newList;
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

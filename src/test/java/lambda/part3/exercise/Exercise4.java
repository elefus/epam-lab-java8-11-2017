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
        private final Function<T, List<R>> flatMapping;

        private LazyFlatMapHelper(List<T> source, Function<T, List<R>> flatMapping) {
            this.source = source;
            this.flatMapping = flatMapping;
        }

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            return new LazyFlatMapHelper<>(list, Collections::singletonList);
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            return new LazyFlatMapHelper<>(source, t -> {
                List<U> result = new ArrayList<>();
                for (R r : this.flatMapping.apply(t)) {
                    result.addAll(flatMapping.apply(r));
                }
                return result;
            });
        }

        public List<R> force() {
            final List<R> result = new ArrayList<>();
            for (T t : source) {
                result.addAll(flatMapping.apply(t));
            }
            return result;
        }

        public <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(source, t -> {
                List<U> result = new ArrayList<>();
                for (R r : this.flatMapping.apply(t)) {
                    result.add(mapping.apply(r));
                }
                return result;
            });
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyFlatMapHelper.from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(string -> {
                    List<Character> chars = new ArrayList<>();
                    for (Character character : string.toCharArray()) {
                        chars.add(character);
                    }
                    return chars;
                })
                .map(character -> (int) character)
                .force();
        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "QA"), codes);
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

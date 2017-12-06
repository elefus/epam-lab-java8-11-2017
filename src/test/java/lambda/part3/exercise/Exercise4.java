package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static List<Integer> calcCodes(String... strings) {
        List<Integer> codes = new ArrayList<>();
        for (String string : strings) {
            for (char letter : string.toCharArray()) {
                codes.add((int) letter);
            }
        }
        return codes;
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyFlatMapHelper.from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(s -> {
                    List<Character> list = new ArrayList<>();
                    for (char c : s.toCharArray()) {
                        list.add(c);
                    }
                    return list;
                })
                .map(Integer::valueOf).force();

        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev"), codes);
    }

    private static class LazyFlatMapHelper<T, R> {

        List<T> source;
        Function<T, List<R>> flatMapping;

        LazyFlatMapHelper(List<T> source, Function<T, List<R>> mapping) {
            this.source = source;
            this.flatMapping = mapping;
        }


        static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            return new LazyFlatMapHelper<>(list, Arrays::asList);
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

        <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            return new LazyFlatMapHelper<>(source, t -> {
                List<U> result = new ArrayList<>();
                for (R r : this.flatMapping.apply(t)) {
                    result.addAll(flatMapping.apply(r));
                }
                return result;
            });
        }

        List<R> force() {
            List<R> result = new ArrayList<>();
            source.forEach(t -> result.addAll(flatMapping.apply(t)));
            return result;
        }
    }
}

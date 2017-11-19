package lambda.part3.exercise;

import com.google.common.collect.Lists;
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

        private final List<T> list;
        private final Function<T, List<R>> flatMapping;

        private LazyFlatMapHelper(List<T> list, Function<T, List<R>> flatMapping) {
            this.list = list;
            this.flatMapping = flatMapping;
        }

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            // TODO реализация
            return new LazyFlatMapHelper<>(list, it -> Collections.singletonList(it));
        }

        public <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(list, it -> {
                List<U> results = new ArrayList<>();
                for (R element : flatMapping.apply(it)) results.add(mapping.apply(element));
                return results;
            });
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            // TODO реализация
            return new LazyFlatMapHelper<>(list, it -> {
                List<U> results = new ArrayList<>();
                for (R element : this.flatMapping.apply(it)) results.addAll(flatMapping.apply(element));
                return results;
            });
        }

        public List<R> force() {
            // TODO реализация
            List<R> result = new ArrayList<>();
            for (T element : list) result.addAll(flatMapping.apply(element));
            return result;
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyFlatMapHelper.from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(Lists::charactersOf)
                .map(character -> (int) character)
                .force();
        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "QA"), codes);
    }

    private static List<Integer> calcCodes(String...strings) {
        List<Integer> codes = new ArrayList<>();
        for (String string : strings) {
            for (char letter : string.toCharArray()) {
                codes.add((int) letter);
            }
        }
        return codes;
    }
}

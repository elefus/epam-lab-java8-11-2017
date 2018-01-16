package lambda.part3.exercise;

import com.google.common.collect.Lists;
import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyFlatMapHelper<T, R> {

        private List<T> list;
        private Function<T, List<R>> flatMapping;

        public LazyFlatMapHelper(List<T> list, Function<T, List<R>> flatMapping) {
            this.list = list;
            this.flatMapping = flatMapping;
        }

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            // TODO реализация
            return new LazyFlatMapHelper<>(list, element -> Collections.singletonList(element));
        }

        public <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(list, element -> {
                List<U> results = new ArrayList<>();
                for (R r : flatMapping.apply(element)) {
                    results.add(mapping.apply(r));
                }
                return results;
            });
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            // TODO реализация
            return new LazyFlatMapHelper<>(list, element -> {
                List<U> result = new ArrayList<>();
                for (R r : this.flatMapping.apply(element)) {
                    result.addAll(flatMapping.apply(r));
                }
                return result;
            });
        }

        public List<R> force() {
            // TODO реализация
            List<R> result = new ArrayList<>();
            for (T t : list) {
                result.addAll(flatMapping.apply(t));
            }
            return result;
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyFlatMapHelper
                .from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(Lists::charactersOf)
                .map(character -> (int) character)
                .force();
        // TODO                 LazyFlatMapHelper.from(employees)
        // TODO                                  .flatMap(Employee -> JobHistoryEntry)
        // TODO                                  .map(JobHistoryEntry -> String(position))
        // TODO                                  .flatMap(String -> Character(letter))
        // TODO                                  .map(Character -> Integer(code letter)
        // TODO                                  .getMapped();
        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev"), codes);
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

package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyFlatMapHelper<T, R> {

        private List<T> source;
        private Function<T, List<R>> function;

        private LazyFlatMapHelper(List<T> source, Function<T, List<R>> function) {
            this.source = source;
            this.function = function;
        }

        static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            return new LazyFlatMapHelper<>(list, Collections::singletonList);
        }

        <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            Function<List<R>, List<U>> mapping = list ->
                    list.stream()
                            .map(flatMapping)
                            .flatMap(List::stream)
                            .collect(Collectors.toList());
            return new LazyFlatMapHelper<>(source, function.andThen(mapping));
        }

        <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(source, function.andThen(list ->
                    list.stream()
                            .map(mapping)
                            .collect(Collectors.toList())));
        }

        List<R> force() {
            List<R> result = new ArrayList<>();
            source.forEach(t -> result.addAll(function.apply(t)));
            return result;
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyFlatMapHelper.from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(Exercise4::calcCodes)
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

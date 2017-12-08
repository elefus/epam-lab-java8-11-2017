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
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyFlatMapHelper<T, R> {

        private List<T> source;
        private Function<T, List<R>> flatMapFunc;

        public LazyFlatMapHelper(List<T> source, Function<T, List<R>> flatMapFunc) {
            this.source = source;
            this.flatMapFunc = flatMapFunc;
        }

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            return new LazyFlatMapHelper<>(list, Collections::singletonList);
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            Function<T, List<U>> func = elem -> flatMapFunc.apply(elem).stream()
                    .flatMap(r -> flatMapping.apply(r).stream())
                    .collect(Collectors.toList());

            return new LazyFlatMapHelper<>(source, func);
        }

        public <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapFunc) {
            Function<T, List<U>> func = elem -> flatMapFunc.apply(elem).stream()
                    .map(mapFunc)
                    .collect(Collectors.toList());

            return new LazyFlatMapHelper<>(source, func);
        }

        public List<R> force() {
            return source.stream()
                    .flatMap(elem -> flatMapFunc.apply(elem).stream())
                    .collect(Collectors.toList());
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyFlatMapHelper.from(employees).
                flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(Lists::charactersOf)
                .map(Integer::new)
                .force();

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

package lambda.part3.exercise;

import com.google.common.collect.Lists;
import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
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

        public LazyFlatMapHelper<T, R> map(Function<T, R> mapping) {
            return new LazyFlatMapHelper<>(source, entity ->
                    applyToList(this.flatMapping.apply(entity),
                            (element, result) -> result.add(mapping.apply(element))));
        }

        public LazyFlatMapHelper<T, R> flatMap(Function<T, List<R>> flatMapping) {
            return new LazyFlatMapHelper<>(source, entity ->
                    applyToList(this.flatMapping.apply(entity),
                            (element, result) -> result.addAll(flatMapping.apply(element))));
        }

        public List<R> force() {
            return applyToList(source, (element, result) -> result.addAll(flatMapping.apply(element)));
        }

        public <U> List<R> applyToList(List<R> source, BiFunction<T, List<R>, Boolean> function){
            List<R> result = new ArrayList<>();
            source.forEach(entity -> function.apply(entity, result));
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
                .map(Integer::valueOf)
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

package lambda.part3.exercise;

import com.google.common.collect.Lists;
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

    private static class LazyFlatMapHelper<T, R> {

        private final List<T> source;
        private final Function<T, List<R>> mapping;

        private LazyFlatMapHelper(List<T> source, Function<T, List<R>> mapping) {
            this.source = source;
            this.mapping = mapping;
        }

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            return new LazyFlatMapHelper<>(list, Arrays::asList);
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            return new LazyFlatMapHelper<>(source, t -> {
                List<U> result = new ArrayList<>();
                List<R> listR = mapping.apply(t);
                for(R r: listR){
                    result.addAll(flatMapping.apply(r));
                }
                return result;
            });
        }

        public <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(source, t -> {
                List<U> result = new ArrayList<>();
                for (R r : this.mapping.apply(t)) {
                    result.add(mapping.apply(r));
                }
                return result;
            });
        }

        public List<R> force() {
            List<R> result = new ArrayList<>();
            for (T value : source) {
                result.addAll(mapping.apply(value));
            }
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
                         .map(c -> (int) c)
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

package lambda.part3.exercise;

import com.google.common.primitives.Chars;
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

        private final List<T> source;
        private final Function<T, List<R>> mapping;

        public LazyFlatMapHelper(List<T> source, Function<T, List<R>> mapping) {
            this.source = source;
            this.mapping = mapping;
        }

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            // TODO реализация
            return new LazyFlatMapHelper<>(list, Collections::singletonList);
        }

        public <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapFunc) {
            return new LazyFlatMapHelper<>(source, elem -> {
                List<U> list = new ArrayList<>();
                mapping.apply(elem).forEach(newElem -> list.add(mapFunc.apply(newElem)));

                return list;
            });
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> mapFunc) {
            // TODO реализация
            return new LazyFlatMapHelper<>(source, elem -> {
                List<U> list = new ArrayList<>();
                mapping.apply(elem).forEach(newElem -> list.addAll(mapFunc.apply(newElem)));

                return list;
            });
        }

        public List<R> force() {
            // TODO реализация
            List<R> list = new ArrayList<>();
            source.forEach(elem -> list.addAll(mapping.apply(elem)));

            return list;
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyFlatMapHelper.from(employees)
                                          .flatMap(Employee::getJobHistory)
                                          .map(JobHistoryEntry::getPosition)
                                          .flatMap(position -> Chars.asList(position.toCharArray()))
                                          .map(Integer::valueOf)
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

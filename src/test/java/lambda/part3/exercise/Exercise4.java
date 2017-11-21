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

@SuppressWarnings({"unused", "ConstantConditions", "WeakerAccess"})
public class Exercise4 {

    private static class LazyFlatMapHelper<T, R> {

        private final List<T> source;
        private final Function<T, List<R>> flatMapping;

        private LazyFlatMapHelper(final List<T> source, final Function<T, List<R>> mapping) {
            this.source = source;
            this.flatMapping = mapping;
        }

        public static <T> LazyFlatMapHelper<T, T> from(final List<T> list) {
            // TODO реализация
            return new LazyFlatMapHelper<>(list, Collections::singletonList);
        }

        /**
         * Flatmaps arbitrary base List
         */
        private <FROM, TO> List<TO> applyFlatMapping(final List<FROM> base, final Function<FROM, List<TO>> flatMapping) {
            final List<TO> result = new ArrayList<>();
            for (final FROM r : base) {
                result.addAll(flatMapping.apply(r));
            }
            return result;
        }

        /**
         * Common code for flatMap() and map(). Constructs new mapping to use as constructor arg
         * creating new instance of LazyFlatMapHelper.
         * @param mapping new mapping to be combined tith this.flatMapping
         * @param <U>
         * @return combination of this.flatMapping and mapping
         */
        private <U> Function<T, List<U>> getNewMapping(final Function<R, List<U>> mapping) {
            return (T t) -> applyFlatMapping(flatMapping.apply(t), mapping);
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(final Function<R, List<U>> mapping) {
            // TODO реализация
            return new LazyFlatMapHelper<>(source, getNewMapping(mapping));
        }

        public <U> LazyFlatMapHelper<T, U> map(final Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(source, getNewMapping(e -> Collections.singletonList(mapping.apply(e))));
        }

        public List<R> force() {
            // TODO реализация
            return applyFlatMapping(source, flatMapping);
        }
    }

    private final List<Employee> employees = Example1.getEmployees();
    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {

        final List<Integer> codes = LazyFlatMapHelper.from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(Lists::charactersOf)
                .map(Integer::valueOf)
                .force();
        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "QA"), codes);
    }

    @Test
    public void employeesMatchThemselves() {
        assertEquals(LazyFlatMapHelper.from(employees).force(), employees);
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

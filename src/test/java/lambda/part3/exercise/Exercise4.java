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

        public static <T> LazyFlatMapHelper<T, T> from(final List<T> sourceList) {
            // TODO реализация
            return new LazyFlatMapHelper<>(sourceList, Collections::singletonList);
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(final Function<R, List<U>> flatMapping) {
            // TODO реализация
            return new LazyFlatMapHelper<>(source, andThen(flatMapping));
        }

        public <U> LazyFlatMapHelper<T, U> map(final Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(source, andThen(e -> Collections.singletonList(mapping.apply(e))));
        }

        public List<R> force() {
            // TODO реализация
            return applyFlatMapping(source, flatMapping);
        }

        /**
         * Constructs new flatMapping, combining current and new one.
         * Created to be used as LazyFlatMapHelper constructor arg.
         */
        private <U> Function<T, List<U>> andThen(final Function<R, List<U>> flatMapping) {
            return (T t) -> applyFlatMapping(this.flatMapping.apply(t), flatMapping);
        }

        /**
         * Flatmaps arbitrary base List. Helper method to be used in andThen() and force()
         */
        private <FROM, TO> List<TO> applyFlatMapping(final List<FROM> base, final Function<FROM, List<TO>> flatMapping) {
            final List<TO> result = new ArrayList<>();
            for (final FROM b : base) {
                result.addAll(flatMapping.apply(b));
            }
            return result;
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

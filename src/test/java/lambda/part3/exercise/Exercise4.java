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

        private final List<T> source;
        private final Function<T, List<R>> flatMapper;

        public LazyFlatMapHelper(List<T> source, Function<T, List<R>> flatMapper) {
            this.source = source;
            this.flatMapper = flatMapper;
        }

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            // TODO реализация
            return new LazyFlatMapHelper<>(list, Collections::singletonList);

        }

        public <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            // TODO реализация
            return new LazyFlatMapHelper<>(source, t -> {
                                List<U> list = new ArrayList<>();
                                for (R r : flatMapper.apply(t)) {
                                    list.addAll(flatMapping.apply(r));
                                    }
                                return list;
        });
        }



        public List<R> force() {
            // TODO реализация
            List<R> list = new ArrayList<>();
            for (T t :
                    source) {
                list.addAll(flatMapper.apply(t));
            }
            return list;
        }

        public <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(source, t -> {
                List<U> result = new ArrayList<>();
                for (R r : flatMapper.apply(t)) {
                    result.add(mapping.apply(r));
                }
                return result;
            });
        }
    }



    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();


        // TODO                 LazyFlatMapHelper.from(employees)
        // TODO                                  .flatMap(Employee -> JobHistoryEntry)
        // TODO                                  .map(JobHistoryEntry -> String(position))
        // TODO                                  .flatMap(String -> Character(letter))
        // TODO                                  .map(Character -> Integer(code letter)
        // TODO                                  .getMapped();
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

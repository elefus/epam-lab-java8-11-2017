package lambda.part3.exercise;

import com.google.common.collect.Lists;
import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions"})
public class Exercise2 {

    private static class MapHelper<T> {

        private final List<T> source;

        private MapHelper(List<T> source) {
            this.source = source;
        }

        public static <T> MapHelper<T> from(List<T> source) {
            return new MapHelper<>(source);
        }

        public List<T> getMapped() {
            return new ArrayList<>(source);
        }

        /**
         * Создает объект для маппинга, передавая ему новый список, построенный на основе исходного.
         * Для добавления в новый список каждый элемент преобразовывается с использованием заданной функции.
         * ([T], (T -> R)) -> [R]
         * @param mapping Функция преобразования элементов.
         */
        public <R> MapHelper<R> map(Function<T, R> mapping) {
            // TODO реализация
            ArrayList<R> result = new ArrayList<>();
            for (T elem : source) {
                result.add(mapping.apply(elem));
            }
            return new MapHelper<>(result);
        }

        /**
         * Создает объект для маппинга, передавая ему новый список, построенный на основе исходного.
         * Для добавления в новый список каждый элемент преобразовывается в список с использованием заданной функции.
         * ([T], (T -> [R])) -> [R]
         * @param flatMapping Функция преобразования элементов.
         */
        public <R> MapHelper<R> flatMap(Function<T, List<R>> flatMapping) {
            // TODO реализация
            ArrayList<R> result = new ArrayList<>();
            for (T elem : source) {
                result.addAll(flatMapping.apply(elem));
            }
            return new MapHelper<>(result);
        }
    }

    @Test
    public void mapEmployeesToLengthOfTheirFullNamesUsingMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> lengths = MapHelper.from(employees)
                .map(Employee::getPerson)
                .map(Person::getFullName)
                .map(String::length)
                .getMapped();
        assertEquals(Arrays.asList(14, 19, 14, 14), lengths);
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = MapHelper.from(employees)
                 .flatMap(Employee::getJobHistory)
                 .map(JobHistoryEntry::getPosition)
                 .flatMap(Lists::charactersOf)
                 .map(Integer::new)
                 .getMapped();
        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "QA"), codes);
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
package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyFlatMapHelper<T, R> {

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            // TODO реализация
            throw new UnsupportedOperationException();
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            // TODO реализация
            throw new UnsupportedOperationException();
        }

        public List<R> force() {
            // TODO реализация
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = null;
        // TODO                 LazyFlatMapHelper.from(employees)
        // TODO                                  .flatMap(Employee -> JobHistoryEntry)
        // TODO                                  .map(JobHistoryEntry -> String(position))
        // TODO                                  .flatMap(String -> Character(letter))
        // TODO                                  .map(Character -> Integer(code letter)
        // TODO                                  .getMapped();
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

package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Exercise1 {

    @Test
    public void mapEmployeesToLengthOfTheirFullNames() {
        List<Employee> employees = Example1.getEmployees();
        ArrayList lengths = new ArrayList<Integer>();

        Function<Employee, String> fullNameExtractor = e -> e.getPerson().getFullName();

        Function<String, Integer> stringLengthExtractor = String::length;

        Function<Employee, Integer> fullNameLengthExtractor = e -> fullNameExtractor.andThen(stringLengthExtractor).apply(e);

        employees.forEach(e -> lengths.add(fullNameLengthExtractor.apply(e)));

        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}

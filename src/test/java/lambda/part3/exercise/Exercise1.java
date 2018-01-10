package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Exercise1 {

    private Function<Employee, String> fullNameExtractor = employee -> {
        Person person = employee.getPerson();
        return String.format("%s %s", person.getFirstName(), person.getLastName());
    };

    private Function<String, Integer> stringLengthExtractor = String::length;

    @Test
    public void mapEmployeesToLengthOfTheirFullNames() {
        List<Employee> employees = Example1.getEmployees();
        List<Integer> lengths = new ArrayList<>(employees.size());

        for(Employee employee: employees) {
            lengths.add(fullNameExtractor.andThen(stringLengthExtractor).apply(employee));
        }

        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}

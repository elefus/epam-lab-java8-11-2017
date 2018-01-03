package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Exercise1 {

    private String fullNameExtractor(Employee employee) {
        Person person = employee.getPerson();
        return String.format("%s %s", person.getFirstName(), person.getLastName());
    }

    private Integer stringLengthExtractor(String s) {
        return s.length();
    }

    private Integer fullNameLengthExtractor(Employee employee) {
        return stringLengthExtractor(fullNameExtractor(employee));
    }

    @Test
    public void mapEmployeesToLengthOfTheirFullNames() {
        List<Employee> employees = Example1.getEmployees();
        List<Integer> lengths = new ArrayList<>(employees.size());

        for(Employee employee: employees) {
            lengths.add(fullNameLengthExtractor(employee));
        }

        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}

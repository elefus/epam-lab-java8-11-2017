package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Exercise1 {

    @Test
    public void mapEmployeesToLengthOfTheirFullNames() {
        List<Employee> employees = Example1.getEmployees();

        Function<List<Employee>, List<Integer>> fullNameLengthExtractor = emps -> emps.stream()
                .map(Employee::getPerson)
                .map(Person::getFullName)
                .map(String::length)
                .collect(Collectors.toList());

        List<Integer> lengths = fullNameLengthExtractor.apply(employees);

        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}

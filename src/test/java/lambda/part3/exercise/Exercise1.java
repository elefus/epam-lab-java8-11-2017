package lambda.part3.exercise;

import lambda.data.Employee;
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


        // TODO функция извлечения полного имени из сотрудника fullNameExtractor: Employee -> String
        Function<Employee, String> fullNameExtractor = employee -> employee.getPerson().getFullName();

        // TODO функция извлечения длины из строки stringLengthExtractor: String -> Integer
        Function<String, Integer> stringLengthExtractor = String::length;

        // TODO функция извлечения длины полного имени из сотрудника fullNameLengthExtractor: Employee -> Integer
        Function<Employee, Integer> fullNameLengthExtractor = fullNameExtractor.andThen(stringLengthExtractor);

        // TODO преобразование списка employees в lengths используя fullNameLengthExtractor
        List<Integer> lengths = employees.stream()
                                         .map(fullNameLengthExtractor)
                                         .collect(Collectors.toList());
        
        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}

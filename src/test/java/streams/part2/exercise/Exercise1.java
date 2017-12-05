package streams.part2.exercise;

import com.google.common.collect.Sets;
import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonPositionPair;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise1 {

    @Test
    public void calcTotalYearsSpentInEpam() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Long hours = employees.stream()
                              .flatMap(employee -> employee.getJobHistory().stream())
                              .filter(jobHistoryEntry -> "EPAM".equals(jobHistoryEntry.getEmployer()))
                              .mapToLong(JobHistoryEntry::getDuration)
                              .sum();

        assertEquals(18, hours.longValue());
    }

    @Test
    public void findPersonsWithQaExperience() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Set<Person> workedAsQa = employees.stream()
                                          .filter(employee -> employee.getJobHistory().stream()
                                                            .map(JobHistoryEntry::getPosition)
                                                            .anyMatch("QA"::equals))
                                          .map(Employee::getPerson)
                                          .collect(Collectors.toSet());

        Set<Person> expected = new HashSet<>(Arrays.asList(
                employees.get(2).getPerson(),
                employees.get(4).getPerson(),
                employees.get(5).getPerson()
        ));
        assertEquals(expected, workedAsQa);
    }

    @Test
    public void composeFullNamesOfEmployeesUsingLineSeparatorAsDelimiter() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        String result = employees.stream()
                                 .map(Employee::getPerson)
                                 .map(Person::getFullName)
                                 .collect(Collectors.joining("\n"));

        String expected = "Иван Мельников\n"
                        + "Александр Дементьев\n"
                        + "Дмитрий Осинов\n"
                        + "Анна Светличная\n"
                        + "Игорь Толмачёв\n"
                        + "Иван Александров";
        assertEquals(expected, result);
    }

    @Test
    public void groupPersonsByFirstPositionUsingToMap() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Function<Employee, PersonPositionPair> employeeToPersonPositionPair =
                employee -> new PersonPositionPair(employee.getPerson(), employee.getJobHistory().get(0).getPosition());

        BinaryOperator<Set<Person>> mergeSets = (firstSet, secondSet) -> {
            Set<Person> result = new HashSet<>(firstSet);
            result.addAll(secondSet);
            return result;
        };

        Map<String, Set<Person>> result = employees.stream()
                                                   .map(employeeToPersonPositionPair)
                                                   .collect(toMap(PersonPositionPair::getPosition,
                                                           pair -> new HashSet<>(Collections.singleton(pair.getPerson())),
                                                           mergeSets));

        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("dev", Collections.singleton(employees.get(0).getPerson()));
        expected.put("tester", new HashSet<>(Arrays.asList(
                employees.get(1).getPerson(),
                employees.get(3).getPerson(),
                employees.get(4).getPerson()))
        );
        expected.put("QA", new HashSet<>(Arrays.asList(employees.get(2).getPerson(), employees.get(5).getPerson())));
        assertEquals(expected, result);
    }

    @Test
    public void groupPersonsByFirstPositionUsingGroupingByCollector() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Function<Employee, String> getFirstPosition = employee -> employee.getJobHistory().get(0).getPosition();
        Map<String, Set<Person>> result = employees.stream()
                                                   .collect(groupingBy(getFirstPosition, mapping(Employee::getPerson, toSet())));

        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("dev", Collections.singleton(employees.get(0).getPerson()));
        expected.put("tester", new HashSet<>(Arrays.asList(
                employees.get(1).getPerson(),
                employees.get(3).getPerson(),
                employees.get(4).getPerson()))
        );
        expected.put("QA", new HashSet<>(Arrays.asList(employees.get(2).getPerson(), employees.get(5).getPerson())));
        assertEquals(expected, result);
    }
}

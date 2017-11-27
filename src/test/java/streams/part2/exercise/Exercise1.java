package streams.part2.exercise;

import com.google.common.collect.Sets;
import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonPositionPair;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise1 {

    @Test
    public void calcTotalYearsSpentInEpam() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Function<Employee, Stream<Integer>> durationInEpamStreamExtractor =
                e -> e.getJobHistory().stream()
                        .filter(jhe -> "EPAM".equals(jhe.getEmployer()))
                        .map(JobHistoryEntry::getDuration);
        Long hours = employees.stream()
                .flatMap(durationInEpamStreamExtractor)
                .mapToLong(Long::valueOf).sum();

        assertEquals(18, hours.longValue());
    }

    @Test
    public void findPersonsWithQaExperience() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Predicate<Employee> hasQaExperience = e -> e.getJobHistory().stream()
                .map(JobHistoryEntry::getPosition)
                .anyMatch("QA"::equals);
        Set<Person> workedAsQa = employees.stream()
                .filter(hasQaExperience)
                .map(Employee::getPerson).collect(toSet());

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
                .collect(joining("\n"));

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
        Function<Employee, PersonPositionPair> employeeToPairOfPersonAndFirstPosition =
                e -> new PersonPositionPair(e.getPerson(), e.getJobHistory().get(0).getPosition());
        Map<String, Set<Person>> result = employees.stream()
                .map(employeeToPairOfPersonAndFirstPosition)
                .collect(toMap(PersonPositionPair::getPosition, PersonPositionPair::getPersonAsSet, Sets::union));

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
        final Function<Employee, String> firstPosition = e -> e.getJobHistory().get(0).getPosition();
        final Collector<Employee, ?, Set<Person>> epmloyeesToSetOfPersons = mapping(Employee::getPerson, toSet());
        Map<String, Set<Person>> result = employees.stream()
                .collect(groupingBy(firstPosition, epmloyeesToSetOfPersons));

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

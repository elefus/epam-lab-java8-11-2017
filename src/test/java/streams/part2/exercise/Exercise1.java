package streams.part2.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonPositionPair;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise1 {

    @Test
    public void calcTotalYearsSpentInEpam() {
        List<Employee> employees = Example1.getEmployees();

        Long hours = employees.stream()
                .flatMapToLong(employee -> employee.getJobHistory().stream()
                        .filter(jobHistoryEntry -> jobHistoryEntry.getEmployer().equals("EPAM"))
                        .mapToLong(JobHistoryEntry::getDuration))
                .sum();

        assertEquals(18, hours.longValue());
    }

    @Test
    public void findPersonsWithQaExperience() {
        List<Employee> employees = Example1.getEmployees();

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


        String result = employees.stream()
                .map(employee -> employee.getPerson().getFullName())
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

        Map<String, Set<Person>> result = employees.stream()
                .map(employee -> new PersonPositionPair(employee.getPerson(), employee.getJobHistory().get(0).getPosition()))
                .collect(Collectors.toMap(PersonPositionPair::getPosition,
                        pair -> new HashSet<>(Collections.singleton(pair.getPerson())),
                        (set1, set2) -> {
                            set2.addAll(set1);
                            return set2;
                        }
                ));

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

        Map<String, Set<Person>> result = employees.stream()
                .map(employee -> new PersonPositionPair(employee.getPerson(), employee.getJobHistory().get(0).getPosition()))
                .collect(Collectors.groupingBy(PersonPositionPair::getPosition, Collectors.mapping(PersonPositionPair::getPerson, Collectors.toSet())));


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

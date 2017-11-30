package streams.part2.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonPositionPair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise1 {

    @Test
    public void calcTotalYearsSpentInEpam() {
        List<Employee> employees = Example1.getEmployees();

        Long hours = employees.stream()
                              .map(Employee::getJobHistory)
                              .flatMap(Collection::stream)
                              .filter(JobHistoryEntry -> JobHistoryEntry.getEmployer().equals("EPAM"))
                              .map(JobHistoryEntry::getDuration)
                              .reduce((d1, d2) -> (d1 + d2))
                              .get().longValue();


        assertEquals(18, hours.longValue());
    }

    @Test
    public void findPersonsWithQaExperience() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Set<Person> workedAsQa = employees.stream()
                                          .filter(employee -> employee.getJobHistory().stream()
                                                                      .map(JobHistoryEntry::getPosition)
                                                                      .anyMatch(position -> position.equals("QA")))
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
        Map<String, Set<Person>> result = employees.stream()
                                                   .map(getPair)
                                                   .collect(Collectors.toMap(PersonPositionPair::getPosition,
                                                           pair -> new HashSet<>(Collections.singleton(pair.getPerson())),
                                                           (set1, set2) -> {
                                                               set1.addAll(set2);
                                                               return set1;
                                                           }));

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
        Map<String, Set<Person>> result = employees.stream().map(getPair)
                                                   .collect(Collectors.groupingBy(PersonPositionPair::getPosition,
                                                           Collectors.collectingAndThen(Collectors.toSet(),
                                                                   a -> a.stream().map(PersonPositionPair::getPerson)
                                                                         .collect(Collectors.toSet()))));

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

    private Function<Employee, PersonPositionPair> getPair = employee ->
            new PersonPositionPair(employee.getPerson(), employee.getJobHistory().get(0).getPosition());
}

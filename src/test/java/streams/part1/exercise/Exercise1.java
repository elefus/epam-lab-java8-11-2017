package streams.part1.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise1 {

    @Test
    public void findPersonsEverWorkedInEpam() {
        List<Employee> employees = Example1.getEmployees();

        Predicate<Employee> predEverWorked = employee -> employee.getJobHistory()
                .stream()
                .anyMatch(job -> "EPAM".equalsIgnoreCase(job.getEmployer()));

        List<Person> personsEverWorkedInEpam = employees.stream()
                .filter(predEverWorked)
                .map(Employee::getPerson)
                .collect(Collectors.toList());

        List<Person> expected = Arrays.asList(
            employees.get(0).getPerson(),
            employees.get(1).getPerson(),
            employees.get(4).getPerson(),
            employees.get(5).getPerson());
        assertEquals(expected, personsEverWorkedInEpam);
    }

    @Test
    public void findPersonsBeganCareerInEpam() {
        List<Employee> employees = Example1.getEmployees();

        Predicate<Employee> firstJobInEpam = employee -> "EPAM".equalsIgnoreCase(
                employee.getJobHistory().stream().findFirst().get().getEmployer()
        );

        List<Person> startedFromEpam = employees.stream()
                .filter(firstJobInEpam)
                .map(Employee::getPerson)
                .collect(Collectors.toList());

        List<Person> expected = Arrays.asList(
                employees.get(0).getPerson(),
                employees.get(1).getPerson(),
                employees.get(4).getPerson());
        assertEquals(expected, startedFromEpam);
    }

    @Test
    public void findAllCompanies() {
        List<Employee> employees = Example1.getEmployees();

        Set<String> companies = employees.stream()
                .flatMap(emp -> emp.getJobHistory().stream())
                .map(JobHistoryEntry::getEmployer)
                .collect(Collectors.toSet());

        Set<String> expected = new HashSet<>();
        expected.add("EPAM");
        expected.add("google");
        expected.add("yandex");
        expected.add("mail.ru");
        expected.add("T-Systems");
        assertEquals(expected, companies);
    }

    @Test
    public void findMinimalAgeOfEmployees() {
        List<Employee> employees = Example1.getEmployees();

        Integer minimalAge = employees.stream()
                .map(emp -> emp.getPerson().getAge())
                .min(Integer::compareTo)
                .get();

        assertEquals(21, minimalAge.intValue());
    }
}

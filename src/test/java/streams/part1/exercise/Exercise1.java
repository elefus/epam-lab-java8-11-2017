package streams.part1.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise1 {

    @Test
    public void findPersonsEverWorkedInEpam() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        List<Person> personsEverWorkedInEpam = employees
                .stream()
                .filter(employee -> employee
                        .getJobHistory()
                        .stream()
                        .map(JobHistoryEntry::getEmployer)
                        .anyMatch("EPAM"::equals))
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

        // TODO реализация
        List<Person> startedFromEpam = employees
                        .stream()
                        .filter(employee -> employee.getJobHistory().get(0).getEmployer().equals("EPAM"))
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

        // TODO реализация
        List<String> companies = employees
                .stream()
                .map(Employee::getJobHistory)
                .flatMap(List::stream)
                .map(JobHistoryEntry::getEmployer)
                .distinct()
                .collect(Collectors.toList());

        assertEquals(Arrays.asList("EPAM", "google", "yandex", "mail.ru", "T-Systems"), companies);
    }

    @Test
    public void findMinimalAgeOfEmployees() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Integer minimalAge = employees
                .stream()
                .map(Employee::getPerson)
                .mapToInt(Person::getAge)
                .min()
                .getAsInt();

        assertEquals(21, minimalAge.intValue());
    }
}

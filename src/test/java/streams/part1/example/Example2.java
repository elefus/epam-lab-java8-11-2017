package streams.part1.example;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import org.junit.Test;
import lambda.part3.example.Example1;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @see <a href="https://habrahabr.ru/company/luxoft/blog/270383">Шпаргалка по Stream-API</a>
 */
@SuppressWarnings({"RedundantStreamOptionalCall", "ResultOfMethodCallIgnored", "unused"})
public class Example2 {

    @Test
    public void operationsOnStreamExample() {
        Example1.getEmployees()
                .stream()
                .filter(e -> e.getPerson().getFirstName().equals("John"))
                .map(Employee::getJobHistory)
                .flatMap(Collection::stream)
                .peek(System.out::println)
                .distinct()
                .sorted(comparing(JobHistoryEntry::getDuration))
                .skip(1)
                .limit(10)
                .unordered()
                .sequential()
                .parallel()
                .findAny();
            //  .allMatch(Predicate<T>)
            //  .anyMatch(Predicate<T>)
            //  .noneMatch(Predicate<T>)
            //  .reduce(BinaryOperator<T>) // коммутативность операции
            //  .collect(Collector<T, A, R>)
            //  .count()
            //  .findAny()
            //  .findFirst()
            //  .forEach(Consumer<T>)
            //  .forEachOrdered(Consumer<>)
            //  .max()
            //  .min()
            //  .toArray(IntFunction<A[]>)
            //  .iterator()
    }

    @Test
    public void singleUsageStream() {
        Stream<Employee> employeeStream = Example1.getEmployees()
                                                  .stream();
        Stream<Person> personStream = employeeStream.map(Employee::getPerson);

        long countPersons = personStream.count();
        assertEquals(6, countPersons);

        try {
            Person[] persons = personStream.toArray(Person[]::new);
        } catch (IllegalStateException ex) {
            assertEquals("stream has already been operated upon or closed", ex.getMessage());
            return;
        }
        fail("Невозможно переиспользовать Stream!");
    }
}

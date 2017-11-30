package streams.part1.example;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;

@SuppressWarnings("ConstantConditions")
public class Example3 {

    @Test
    public void getIvansLastNames() {
        String[] ivansLastNames = Example1.getEmployees()
                                          .stream()
                                          .map(Employee::getPerson)
                                          .filter(person -> "Иван".equals(person.getFirstName()))
                                          .map(Person::getLastName)
                                          .toArray(String[]::new);

        assertArrayEquals(new String[]{"Мельников", "Александров"}, ivansLastNames);
    }

    @Test
    public void checkAny25AgedIvanHasDevExperience() {
        boolean any25IvanHasDevExperience = Example1.getEmployees()
                                                    .stream()
                                                    .filter(employee -> employee.getPerson().getAge() > 25)
                                                    .filter(employee -> "Иван".equals(employee.getPerson().getFirstName()))
                                                    .map(Employee::getJobHistory)
                                                    .flatMap(Collection::stream)
                                                    .anyMatch(entry -> "dev".equals(entry.getPosition()));

        assertTrue(any25IvanHasDevExperience);
    }
}

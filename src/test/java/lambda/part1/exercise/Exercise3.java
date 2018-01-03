package lambda.part1.exercise;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise3 {

    @Test
    public void sortPersonsByAgeUsingArraysSortExpressionLambda() {
        Person[] persons = getPersons();

        //noinspection ComparatorCombinators
        Arrays.sort(persons, (p1, p2) -> p1.getAge() - p2.getAge());

        assertArrayEquals(new Person[]{
            new Person("Иван", "Мельников", 20),
            new Person("Николай", "Зимов", 30),
            new Person("Алексей", "Доренко", 40),
            new Person("Артем", "Зимов", 45)
        }, persons);
    }

    @Test
    public void sortPersonsByLastNameThenFirstNameUsingArraysSortExpressionLambda() {
        Person[] persons = getPersons();

        Arrays.sort(persons, (p1, p2) -> {
            int i = p1.getLastName().compareTo(p2.getLastName());
            return i != 0 ? i : p1.getFirstName().compareTo(p2.getFirstName());
        });

        assertArrayEquals(new Person[]{
            new Person("Алексей", "Доренко", 40),
            new Person("Артем", "Зимов", 45),
            new Person("Николай", "Зимов", 30),
            new Person("Иван", "Мельников", 20)
        }, persons);
    }

    @Test
    public void findFirstWithAge30UsingGuavaPredicateLambda() {
        List<Person> persons = Arrays.asList(getPersons());

        Person person = null;
        //noinspection Guava
        Optional<Person> personOptional = FluentIterable.from(persons)
                .firstMatch(p -> p.getAge() == 30);
        if (personOptional.isPresent()) {
            person = personOptional.get();
        }

        assertEquals(new Person("Николай", "Зимов", 30), person);
    }

    private Person[] getPersons() {
        return new Person[]{
            new Person("Иван", "Мельников", 20),
            new Person("Алексей", "Доренко", 40),
            new Person("Николай", "Зимов", 30),
            new Person("Артем", "Зимов", 45)
        };
    }
}

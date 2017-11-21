package lambda.part1.exercise;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "ComparatorCombinators", "Guava", "unused"})
public class Exercise3 {

    @Test
    public void sortPersonsByAgeUsingArraysSortExpressionLambda() {
        Person[] persons = getPersons();

        // TODO использовать Arrays.sort + expression-lambda
        Arrays.sort(persons, (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));

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

        // TODO использовать Arrays.sort + statement-lambda
        Arrays.sort(persons, (p1, p2) -> {
            int compareLastName = p1.getLastName().compareTo(p2.getLastName());
            if (compareLastName == 0)
                return p1.getFirstName().compareTo(p2.getFirstName());
            else
                return compareLastName;
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

        // TODO использовать FluentIterable
        // Using get() without isPresent() check is OK here, because if
        // there is no required person, we should throw exception any way.
        Person person = FluentIterable
                .from(persons)
                .firstMatch(
                        p -> p != null && p.getAge() == 30
                ).get();

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

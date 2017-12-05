package lambda.part1.exercise;

import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused", "CodeBlock2Expr", "ComparatorCombinators", "Guava"})
public class Exercise3 {

    @Test
    public void sortPersonsByAgeUsingArraysSortExpressionLambda() {
        Person[] persons = getPersons();

        // TODO использовать Arrays.sort + expression-lambda
        Arrays.sort(persons, (left, right) -> left.getAge() - (right.getAge()));

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
        Arrays.sort(persons, (left, right) -> {
            int result = left.getLastName().compareTo(right.getLastName());
            if (result == 0) {
                return left.getFirstName().compareTo(right.getFirstName());
            } else {
                return result;
            }
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
        Person person = FluentIterable.from(persons)
                .firstMatch(p -> {
                    return p != null && p.getAge() == 30;
                }).get();

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

package lambda.part2.example;

import lambda.data.Person;
import org.junit.Test;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.*;

@SuppressWarnings("UnnecessaryLocalVariable")
public class Example2 {

    // (Person, Person -> String) -> (String -> boolean)
    private static Predicate<String> stringPropertyChecker(Person person, Function<Person, String> getProperty) {
        // FIXME преобразовать в expression-lambda
        return string -> {
            String propertyValue = getProperty.apply(person);
            return string.equals(propertyValue);
        };
    }

    // (Person -> String) -> boolean
    @Test
    public void checkConcretePersonStringProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        // Person -> String
        Function<Person, String> getFirstName = Person::getFirstName;

        // String -> boolean
        Predicate<String> checkFirstName = stringPropertyChecker(person, getFirstName);

        assertTrue(checkFirstName.test("Иван"));
    }

    // (Person -> String) -> (Person -> String -> boolean)
    private static Function<Person, Predicate<String>> stringPropertyChecker(Function<Person, String> propertyExtractor) {
        return person -> {
            Predicate<String> checker = checkingValue -> {
                boolean isEquals = Objects.equals(propertyExtractor.apply(person), checkingValue);
                return isEquals;
            };
            return checker;
        };
    }

    @Test
    public void checkAnyPersonStringProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, Predicate<String>> lastNameChecker = stringPropertyChecker(Person::getLastName);

        assertTrue(lastNameChecker.apply(person).test("Мельников"));
        assertFalse(lastNameChecker.apply(person).test("Гущин"));
    }

    // (V -> P) -> (V -> P ->  boolean)
    private static <V, P> Function<V, Predicate<P>> propertyChecker(Function<V, P> propertyExtractor) {
        return person -> checkingValue -> Objects.equals(propertyExtractor.apply(person), checkingValue);
    }

    @Test
    public void checkAnyProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, Predicate<Integer>> ageChecker = propertyChecker(Person::getAge);

        assertTrue(ageChecker.apply(person).test(33));
        assertFalse(ageChecker.apply(person).test(10));
    }
}

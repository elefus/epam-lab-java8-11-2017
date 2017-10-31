package lambda.part1.exercise;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Exercise1 {

    @Test
    public void sortPersonsByAgeUsingArraysSortComparator() {
        Person[] persons = getPersons();

        Comparator<Person> comparatorByAge = new Comparator<Person>() {
            @Override
            public int compare(Person left, Person right) {
                int result;
                if (left.getAge() > right.getAge()) result = 1;
                else if (left.getAge() < right.getAge()) result = -1;
                else return 0;
                return result;
            }
        };

        Arrays.sort(persons, comparatorByAge);

        assertArrayEquals(new Person[]{
            new Person("Иван", "Мельников", 20),
            new Person("Николай", "Зимов", 30),
            new Person("Алексей", "Доренко", 40),
            new Person("Артем", "Зимов", 45)
        }, persons);
    }

    @Test
    public void sortPersonsByAgeUsingArraysSortAnonymousComparator() {
        Person[] persons = getPersons();

        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person left, Person right) {
                int result;
                if (left.getAge() > right.getAge()) result = 1;
                else if (left.getAge() < right.getAge()) result = -1;
                else return 0;
                return result;
            }});

        assertArrayEquals(new Person[]{
            new Person("Иван", "Мельников", 20),
            new Person("Николай", "Зимов", 30),
            new Person("Алексей", "Доренко", 40),
            new Person("Артем", "Зимов", 45)
        }, persons);
    }

    @Test
    public void sortPersonsByLastNameThenFirstNameUsingArraysSortAnonymousComparator() {
        Person[] persons = getPersons();

        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person left, Person right) {
                return left.getFirstName().compareTo(right.getFirstName());
            }});

        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person left, Person right) {
                return left.getLastName().compareTo(right.getLastName());
            }});

        assertArrayEquals(new Person[]{
            new Person("Алексей", "Доренко", 40),
            new Person("Артем", "Зимов", 45),
            new Person("Николай", "Зимов", 30),
            new Person("Иван", "Мельников", 20)
        }, persons);
    }

    @Test
    public void findFirstWithAge30UsingGuavaPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Predicate<Person> isAge30Checker = new Predicate<Person>() {
            @Override
            public boolean apply(Person p) {
                return 30 == p.getAge();
            }
        };

        Optional<Person> person = FluentIterable.from(persons)
                .firstMatch(isAge30Checker);

        assertEquals(new Person("Николай", "Зимов", 30), person.get());
    }

    @Test
    public void findFirstWithAge30UsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Optional<Person> person = FluentIterable.from(persons)
                .firstMatch(new Predicate<Person>() {
                    @Override
                    public boolean apply(Person p) {
                        return 30 == p.getAge();
                    }
                });

        assertEquals(new Person("Николай", "Зимов", 30), person.get());
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

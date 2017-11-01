package lambda.part1.exercise;

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
            public int compare(Person first, Person second) {
                Integer nullCheck = nullCheck(first, second);
                if (nullCheck != null) {
                    return nullCheck;
                }
                return Integer.compare(first.getAge(), second.getAge());
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
            public int compare(Person first, Person second) {
                Integer nullCheck = nullCheck(first, second);
                if (nullCheck != null) {
                    return nullCheck;
                }
                return Integer.compare(first.getAge(), second.getAge());
            }
        });

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
            public int compare(Person first, Person second) {
                Integer nullCheck = nullCheck(first, second);
                if (nullCheck != null) {
                    return nullCheck;
                }

                int lastNameCompare = first.getLastName().compareTo(second.getLastName());
                return lastNameCompare == 0
                        ? first.getFirstName().compareTo(second.getFirstName())
                        : lastNameCompare;
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
    public void findFirstWithAge30UsingGuavaPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Predicate<Person> is30AgeChecker = new Predicate<Person>() {
            @Override
            public boolean apply(Person p) {
                return 30 == p.getAge();
            }
        };
        Person person = FluentIterable.from(persons)
                .firstMatch(is30AgeChecker).get();

        assertEquals(new Person("Николай", "Зимов", 30), person);
    }

    @Test
    public void findFirstWithAge30UsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());
        Person person = FluentIterable.from(persons)
                .firstMatch(new Predicate<Person>() {
                    @Override
                    public boolean apply(Person p) {
                        return 30 == p.getAge();
                    }
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

    private <T> Integer nullCheck(T first, T second) {
        if (first == null) {
            if (second == null) {
                return 0;
            }
            return -1;
        } else if (second == null) {
            return 1;
        }
        return null;
    }
}

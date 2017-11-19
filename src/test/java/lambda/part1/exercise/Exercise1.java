package lambda.part1.exercise;

import com.google.common.base.Predicate;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import lombok.NonNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Exercise1 {

    @Test
    public void sortPersonsByAgeUsingArraysSortComparator() {
        Person[] persons = getPersons();

        Comparator<Person> comparatorByLastName = new Comparator<Person>() {
            @Override
            public int compare(@NonNull Person first, @NonNull Person second) {
                return Integer.compare(first.getAge(), second.getAge());
            }
        };
        Arrays.sort(persons, comparatorByLastName);

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
            public int compare(@NonNull Person first, @NonNull Person second) {
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
            public int compare(@NonNull Person first, @NonNull Person second) {
                int result;
                return ( result = first.getLastName().compareTo(second.getLastName()) ) == 0 ?
                        first.getFirstName().compareTo(second.getFirstName()) :
                        result;
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

        Predicate<Person> isAgeThirty = new Predicate<Person>() {
            @Override
            public boolean apply(Person p) {
                return p.getAge() == 30;
            }
        };

        Optional<Person> personOptional = FluentIterable.from(persons).firstMatch(isAgeThirty);

        assertTrue(personOptional.isPresent());
        assertEquals(new Person("Николай", "Зимов", 30), personOptional.get());
    }

    @Test
    public void findFirstWithAge30UsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Optional<Person> personOptional = FluentIterable.from(persons).firstMatch(new Predicate<Person>() {
            @Override
            public boolean apply(Person p) {
                return p.getAge() == 30;
            }
        });

        assertTrue(personOptional.isPresent());
        assertEquals(new Person("Николай", "Зимов", 30), personOptional.get());
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

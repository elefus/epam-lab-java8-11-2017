package lambda.part1.exercise;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import lombok.NonNull;
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

        Comparator<Person> comparator = new Comparator<Person>() {
            @Override
            public int compare(@NonNull Person person,@NonNull Person t1) {
                return Integer.compare(person.getAge(), t1.getAge());
            }
        };
        Arrays.sort(persons, comparator);

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
            public int compare(@NonNull Person person,@NonNull Person t1) {
                return Integer.compare(person.getAge(), t1.getAge());
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
            public int compare(@NonNull Person person,@NonNull Person t1) {
                int lastNameCompare = person.getLastName().compareTo(t1.getLastName());
                if(lastNameCompare == 0) return person.getFirstName().compareTo(t1.getFirstName());
                return lastNameCompare;
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

        //Person person = null;
        Predicate<Person> isAge30 = new Predicate<Person>() {
            @Override
            public boolean apply(Person person) {
                return person != null && person.getAge() == 30;
            }
        };
        Optional<Person> person = FluentIterable.from(persons)
                .firstMatch(isAge30);
        assertEquals(new Person("Николай", "Зимов", 30), person.get());
    }

    @Test
    public void findFirstWithAge30UsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Optional<Person> person = FluentIterable.from(persons)
                .firstMatch(new Predicate<Person>() {
                    @Override
                    public boolean apply(Person person) {
                        return person != null && person.getAge() == 30;
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

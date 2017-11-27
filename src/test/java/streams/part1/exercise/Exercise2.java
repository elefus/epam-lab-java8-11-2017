package streams.part1.exercise;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SuppressWarnings({"ConstantConditions", "unused", "MismatchedQueryAndUpdateOfCollection"})
public class Exercise2 {

    enum Status {
        PENDING,
        COMMENTED,
        ACCEPTED,
        DECLINED,
        UNKNOWN
    }

    @Test
    public void acceptGreaterThan21OthersDecline() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Map<Person, Status> candidates = new HashMap<>();
        candidates.put(alex, Status.PENDING);
        candidates.put(ivan, Status.PENDING);
        candidates.put(helen, Status.PENDING);

        // TODO реализация
/*        BiFunction<Person, Status, Status> getBasicOrRiseSalary = (person, status) -> person.getAge() > 21 ? Status.ACCEPTED
                : Status.DECLINED;

        candidates.compute(alex, getBasicOrRiseSalary);
        candidates.compute(ivan, getBasicOrRiseSalary);
        candidates.compute(helen, getBasicOrRiseSalary);*/

        for (Person person : candidates.keySet()) {
            candidates.compute(person, (person1, status) ->person.getAge() > 21 ? Status.ACCEPTED
                    : Status.DECLINED );
        }

        assertEquals(Status.ACCEPTED, candidates.get(ivan));
        assertEquals(Status.ACCEPTED, candidates.get(helen));
        assertEquals(Status.DECLINED, candidates.get(alex));
    }

    @Test
    public void acceptGreaterThan21OthersRemove() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Map<Person, Status> candidates = new HashMap<>();
        candidates.put(alex, Status.PENDING);
        candidates.put(ivan, Status.PENDING);
        candidates.put(helen, Status.PENDING);

        // TODO реализация
        for (Person person : candidates.keySet()) {
            candidates.compute(person, (person1, status) ->person.getAge() > 21 ? Status.ACCEPTED
                    : null);
        }

        assertEquals(Status.ACCEPTED, candidates.get(ivan));
        assertEquals(Status.ACCEPTED, candidates.get(helen));
        assertNull(candidates.get(alex));
    }

    @Test
    public void getStatus() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Map<Person, Status> candidates = new HashMap<>();
        candidates.put(alex, Status.PENDING);
        candidates.put(ivan, Status.PENDING);

        // TODO реализация
        Status alexStatus = candidates.getOrDefault(alex, Status.UNKNOWN);
        Status ivanStatus = candidates.getOrDefault(ivan, Status.UNKNOWN);
        Status helenStatus = candidates.getOrDefault(helen, Status.UNKNOWN);

        assertEquals(Status.PENDING, alexStatus);
        assertEquals(Status.PENDING, ivanStatus);
        assertEquals(Status.UNKNOWN, helenStatus);
    }

    @Test
    public void combineMapsAndPutToLatest() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Map<Person, Status> oldValues = new HashMap<>();
        oldValues.put(alex, Status.PENDING);
        oldValues.put(ivan, Status.ACCEPTED);

        Map<Person, Status> newValues = new HashMap<>();
        newValues.put(alex, Status.DECLINED);
        newValues.put(helen, Status.PENDING);

        // TODO реализация
        oldValues.forEach((person, status) -> newValues.merge(person, status, (oldValue, newValue) -> oldValue));

        assertEquals(Status.DECLINED, newValues.get(alex));
        assertEquals(Status.ACCEPTED, newValues.get(ivan));
        assertEquals(Status.PENDING, newValues.get(helen));
    }
}

package streams.part2.example.data;

import lambda.data.Person;

import java.util.HashSet;
import java.util.Set;

public class PersonPositionPair {
    private final Person person;
    private final String position;

    public PersonPositionPair(Person person, String position) {
        this.person = person;
        this.position = position;
    }

    public Person getPerson() {
        return person;
    }

    public String getPosition() {
        return position;
    }

    public Set<Person> getPersonAsSet() {
        Set<Person> result = new HashSet<Person>();
        result.add(person);
        return result;
    }
}

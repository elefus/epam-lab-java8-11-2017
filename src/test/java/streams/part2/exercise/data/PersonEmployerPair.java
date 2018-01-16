package streams.part2.exercise.data;

import lambda.data.Person;

public class PersonEmployerPair {
    private final Person person;
    private final String employer;

    public PersonEmployerPair(Person person, String employer) {
        this.person = person;
        this.employer = employer;
    }

    public Person getPerson() {
        return person;
    }

    public String getEmployer() {
        return employer;
    }
}

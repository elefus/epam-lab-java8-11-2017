package streams.part2.exercise.data;

import lambda.data.Person;

public class PersonEmployerDuration {

    private final Person person;
    private final String employer;
    private final int duration;

    public PersonEmployerDuration(Person person, String employer, int duration) {
        this.person = person;
        this.employer = employer;
        this.duration = duration;
    }

    public Person getPerson() {
        return person;
    }

    public String getEmployer() {
        return employer;
    }

    public int getDuration() {
        return duration;
    }

    public PersonEmployerPair getPersonEmployerPair() {
        return new PersonEmployerPair(person, employer);
    }

    @Override
    public String toString() {
        return "PersonEmployerDuration{" +
                "person=" + person +
                ", employer='" + employer + '\'' +
                ", duration=" + duration +
                '}';
    }
}
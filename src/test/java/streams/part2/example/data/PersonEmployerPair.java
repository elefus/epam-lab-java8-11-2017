package streams.part2.example.data;

import lambda.data.Person;
import lombok.Data;

@Data
public class PersonEmployerPair {

    private final Person person;
    private final String employer;
    private final Integer duration;


    public PersonEmployerPair(Person person, String employer, Integer duration) {
        this.person = person;
        this.employer = employer;
        this.duration = duration;
    }
    public PersonEmployerPair(Person person, String employer) {
        this.person = person;
        this.employer = employer;
        this.duration = 0;
    }

    public String getEmployer() {
        return employer;
    }

    public Person getPerson() {
        return person;
    }

    public Integer getDuration() {
        return duration;
    }
}

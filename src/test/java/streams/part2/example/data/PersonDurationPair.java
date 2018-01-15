package streams.part2.example.data;

import lambda.data.Person;
import lombok.Data;

@Data
public class PersonDurationPair {

    private final Person person;
    private final int duration;


    public PersonDurationPair(Person person, int duration) {
        this.person = person;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public Person getPerson() {
        return person;
    }
}

package streams.part2.example.data;

import lambda.data.Person;
import lombok.Data;

@Data
public class PersonDurationPair {

    private final Person person;
    private final int duration;
}

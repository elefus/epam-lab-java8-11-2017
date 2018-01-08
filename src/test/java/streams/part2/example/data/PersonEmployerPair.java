package streams.part2.example.data;

import lambda.data.Person;
import lombok.Data;

@Data
public class PersonEmployerPair {

    private final Person person;
    private final String employer;
}


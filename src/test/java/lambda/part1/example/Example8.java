package lambda.part1.example;

import lambda.data.Person;
import org.junit.Test;

@SuppressWarnings("unused")
public class Example8 {

    @FunctionalInterface
    private interface PersonFactory {

        Person create(String name, String lastName, int age);
    }

    private void withFactory(PersonFactory pf) {
        System.out.println(pf.create("name", "lastName", 33));
    }

    @Test
    public void factory() {
        PersonFactory factory = Person::new;
        withFactory(factory);
    }
}

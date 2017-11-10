package lambda.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Immutable.
 */
public class Person implements Comparable<Person>, Serializable {

    private final String firstName;
    private final String lastName;
    private final int age;

    /**
     * @param firstName Имя.
     * @param lastName Фамилия.
     * @param age Возраст.
     */
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
     * Статический фабричный метод.
     * @param firstName Имя.
     * @param lastName Фамилия.
     * @param age Возраст.
     * @return Созданный объект.
     */
    public static Person create(String firstName, String lastName, int age) {
        return new Person(firstName, lastName, age);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge(Person this) {
        return age;
    }

    public Person withFirstName(String firstName) {
        return new Person(firstName, lastName, age);
    }

    public Person withLastName(String lastName) {
        return new Person(firstName, lastName, age);
    }

    public Person withAge(int age) {
        return new Person(firstName, lastName, age);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Person person = (Person) other;
        return age == person.age
            && Objects.equals(firstName, person.firstName)
            && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    @Override
    public String toString() {
        return "Person@" + hashCode() + ": {"
             + "firstName='" + firstName + "', "
             + "lastName='" + lastName + "', "
             + "age=" + age + "}";
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(age, other.age);
    }
}

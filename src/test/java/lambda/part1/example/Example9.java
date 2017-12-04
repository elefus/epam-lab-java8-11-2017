package lambda.part1.example;

import lambda.data.Person;
import org.junit.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("unused")
public class Example9 {

    private static class ComparatorPersonsByLastName implements Comparator<Person> {

        @Override
        public int compare(Person left, Person right) {
            return left.getLastName().compareTo(right.getLastName());
        }
    }

    @Test
    public void serializeTree() {
        Set<Person> treeSet = new TreeSet<>();
        treeSet.add(new Person("Иван", "Мельников", 33));
        treeSet.add(new Person("Алексей", "Игнатенко", 1));
        treeSet.add(new Person("Сергей", "Лопатин", 3));

        System.out.println(treeSet);

        // TODO сериализовать дерево в массив байт
    }
}

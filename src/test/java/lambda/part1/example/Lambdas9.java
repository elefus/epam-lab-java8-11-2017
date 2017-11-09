package lambda.part1.example;

import lambda.data.Person;
import org.junit.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("unused")
public class Lambdas9 {

    private static class ComparatorPersonsByLastName implements Comparator<Person> {

        @Override
        public int compare(Person left, Person right) {
            return left.getLastName().compareTo(right.getLastName());
        }
    }

    @Test
    public void serializeTree() {
        Set<Person> treeSet = new TreeSet<>();
        treeSet.add(new Person("b", "b", 2));
        treeSet.add(new Person("a", "a", 1));
        treeSet.add(new Person("c", "c", 3));

        System.out.println(treeSet);

        // TODO сериализовать дерево в массив байт
    }
}

package streams.part2.example;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonPositionPair;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("ConstantConditions")
public class Example2 {

    /*
      * Вход:
      * [
      *     {
      *         {Иван Мельников 30},
      *         [dev, dev]
      *     },
      *     {
      *         {Александр Дементьев 28},
      *         [tester, dev, dev]
      *     },
      *     {
      *         {Дмитрий Осинов 40},
      *         [QA, QA, dev]
      *     },
      *     {
      *         {Анна Светличная 21},
      *         [tester]
      *     }
      * ]
      *
      * Выход:
      * [
      *     "dev" -> [ {Иван Мельников 30}, {Александр Дементьев 28}, {Дмитрий Осинов 40} ],
      *     "QA" -> [ {Дмитрий Осинов 40} ],
      *     "tester" -> [ {Александр Дементьев 28}, {Анна Светличная 21} ]
      * ]
      */
    @Test
    public void splitPersonsByPositionExperienceUsingReduce() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                                                   .parallel()
                                                   .flatMap(Example2::employeeToPersonPositionPairsStream)
                                                   .reduce(new HashMap<>(), Example2::addToMap, Example2::combineMaps);

        assertEquals(prepareExpected(employees), result);
    }

    private static Stream<PersonPositionPair> employeeToPersonPositionPairsStream(Employee employee) {
        return employee.getJobHistory()
                       .stream()
                       .map(JobHistoryEntry::getPosition)
                       .map(position -> new PersonPositionPair(employee.getPerson(), position));
    }

    private static Map<String, Set<Person>> addToMap(Map<String, Set<Person>> origin, PersonPositionPair pair) {
        Map<String, Set<Person>> result = new HashMap<>(origin);
        result.compute(pair.getPosition(), (position, persons) -> {
            persons = persons == null ? new HashSet<>() : persons;
            persons.add(pair.getPerson());
            return persons;
        });
        return result;
    }

    private static Map<String, Set<Person>> combineMaps(Map<String, Set<Person>> left, Map<String, Set<Person>> right) {
        Map<String, Set<Person>> result = new HashMap<>(left);
        right.forEach((position, persons) -> result.merge(position, persons, (leftPersons, rightPersons) -> {
            leftPersons.addAll(rightPersons);
            return leftPersons;
        }));
        return result;
    }

    @Test
    public void splitPersonsByPositionExperienceUsingCollect() {
        List<Employee> employees = Example1.getEmployees();

        BiConsumer<HashMap<String, Set<Person>>, PersonPositionPair> accumulator =
                (map, pair) -> map.compute(pair.getPosition(), (position, persons) -> {
                    persons = persons == null ? new HashSet<>() : persons;
                    persons.add(pair.getPerson());
                    return persons;
                });
        BiFunction<Set<Person>, Set<Person>, Set<Person>> mergingSets = (leftPersons, rightPersons) -> {
            leftPersons.addAll(rightPersons);
            return leftPersons;
        };

        BiConsumer<HashMap<String, Set<Person>>, HashMap<String, Set<Person>>> combiner =
                (left, right) -> right.forEach((position, persons) -> left.merge(position, persons, mergingSets));

        Map<String, Set<Person>> result = employees.stream()
                                                   .flatMap(Example2::employeeToPersonPositionPairsStream)
                                                   .collect(HashMap::new, accumulator, combiner);

        assertEquals(prepareExpected(employees), result);
    }

    @Test
    public void splitPersonsByPositionExperienceUsingGroupingByCollector() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                                                   .flatMap(Example2::employeeToPersonPositionPairsStream)
                                                   .collect(groupingBy(PersonPositionPair::getPosition,
                                                                       mapping(PersonPositionPair::getPerson,
                                                                               toSet())));

        assertEquals(prepareExpected(employees), result);
    }

    private static Map<String, Set<Person>> prepareExpected(List<Employee> employees) {
        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("dev", new HashSet<>(Arrays.asList(
                employees.get(0).getPerson(),
                employees.get(1).getPerson(),
                employees.get(2).getPerson(),
                employees.get(5).getPerson()))
        );
        expected.put("tester", new HashSet<>(Arrays.asList(
                employees.get(1).getPerson(),
                employees.get(3).getPerson(),
                employees.get(4).getPerson()
        )));
        expected.put("QA", new HashSet<>(Arrays.asList(
                employees.get(2).getPerson(),
                employees.get(4).getPerson(),
                employees.get(5).getPerson()
        )));
        return expected;
    }
}

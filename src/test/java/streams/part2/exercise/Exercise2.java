package streams.part2.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonEmployerPair;
import streams.part2.example.data.PersonDurationPair;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;

/**
 * @see <a href="https://youtu.be/kxgo7Y4cdA8">Через тернии к лямбдам, часть 1</a>
 * @see <a href="https://youtu.be/JRBWBJ6S4aU">Через тернии к лямбдам, часть 2</a>
 * @see <a href="https://youtu.be/O8oN4KSZEXE">Stream API, часть 1</a>
 * @see <a href="https://youtu.be/i0Jr2l3jrDA">Stream API, часть 2</a>
 */
@SuppressWarnings("ConstantConditions")
public class Exercise2 {

    private Function<Employee, Stream<PersonEmployerPair>> getEmployers = employee ->
            employee.getJobHistory().stream()
                    .map(entry -> new PersonEmployerPair(employee.getPerson(), entry.getEmployer()));


    /**
     * Преобразовать список сотрудников в отображение [компания -> множество людей, когда-либо работавших в этой компании].
     *
     * Входные данные:
     * [
     *     {
     *         {Иван Мельников 30},
     *         [
     *             {2, dev, "EPAM"},
     *             {1, dev, "google"}
     *         ]
     *     },
     *     {
     *         {Александр Дементьев 28},
     *         [
     *             {2, tester, "EPAM"},
     *             {1, dev, "EPAM"},
     *             {1, dev, "google"}
     *         ]
     *     },
     *     {
     *         {Дмитрий Осинов 40},
     *         [
     *             {3, QA, "yandex"},
     *             {1, QA, "EPAM"},
     *             {1, dev, "mail.ru"}
     *         ]
     *     },
     *     {
     *         {Анна Светличная 21},
     *         [
     *             {1, tester, "T-Systems"}
     *         ]
     *     }
     * ]
     *
     * Выходные данные:
     * [
     *    "EPAM" -> [
     *       {Иван Мельников 30},
     *       {Александр Дементьев 28},
     *       {Дмитрий Осинов 40}
     *    ],
     *    "google" -> [
     *       {Иван Мельников 30},
     *       {Александр Дементьев 28}
     *    ],
     *    "yandex" -> [ {Дмитрий Осинов 40} ]
     *    "mail.ru" -> [ {Дмитрий Осинов 40} ]
     *    "T-Systems" -> [ {Анна Светличная 21} ]
     * ]
     */
    @Test
    public void employersStuffList() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Map<String, Set<Person>> result = employees.stream()
                .flatMap(getEmployers)
                .collect(groupingBy(PersonEmployerPair::getEmployer, mapping(PersonEmployerPair::getPerson, toSet())));

        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("EPAM", new HashSet<>(Arrays.asList(
                employees.get(0).getPerson(),
                employees.get(1).getPerson(),
                employees.get(4).getPerson(),
                employees.get(5).getPerson()
        )));
        expected.put("google", new HashSet<>(Arrays.asList(
                employees.get(0).getPerson(),
                employees.get(1).getPerson()
        )));
        expected.put("yandex", new HashSet<>(Collections.singletonList(employees.get(2).getPerson())));
        expected.put("mail.ru", new HashSet<>(Collections.singletonList(employees.get(2).getPerson())));
        expected.put("T-Systems", new HashSet<>(Arrays.asList(
                employees.get(3).getPerson(),
                employees.get(5).getPerson()
        )));
        assertEquals(expected, result);
    }

    /**
     * Преобразовать список сотрудников в отображение [компания -> множество людей, начавших свою карьеру в этой компании].
     *
     * Пример.
     *
     * Входные данные:
     * [
     *     {
     *         {Иван Мельников 30},
     *         [
     *             {2, dev, "EPAM"},
     *             {1, dev, "google"}
     *         ]
     *     },
     *     {
     *         {Александр Дементьев 28},
     *         [
     *             {2, tester, "EPAM"},
     *             {1, dev, "EPAM"},
     *             {1, dev, "google"}
     *         ]
     *     },
     *     {
     *         {Дмитрий Осинов 40},
     *         [
     *             {3, QA, "yandex"},
     *             {1, QA, "EPAM"},
     *             {1, dev, "mail.ru"}
     *         ]
     *     },
     *     {
     *         {Анна Светличная 21},
     *         [
     *             {1, tester, "T-Systems"}
     *         ]
     *     }
     * ]
     *
     * Выходные данные:
     * [
     *    "EPAM" -> [
     *       {Иван Мельников 30},
     *       {Александр Дементьев 28}
     *    ],
     *    "yandex" -> [ {Дмитрий Осинов 40} ]
     *    "T-Systems" -> [ {Анна Светличная 21} ]
     * ]
     */
    @Test
    public void indexByFirstEmployer() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Map<String, Set<Person>> result = employees.stream()
                .flatMap(e -> Stream.of(new PersonEmployerPair(e.getPerson(), e.getJobHistory().get(0).getEmployer())))
                .collect(groupingBy(PersonEmployerPair::getEmployer, mapping(PersonEmployerPair::getPerson, toSet())));

        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("EPAM", new HashSet<>(Arrays.asList(
                employees.get(0).getPerson(),
                employees.get(1).getPerson(),
                employees.get(4).getPerson()
        )));
        expected.put("yandex", new HashSet<>(Collections.singletonList(employees.get(2).getPerson())));
        expected.put("T-Systems", new HashSet<>(Arrays.asList(
                employees.get(3).getPerson(),
                employees.get(5).getPerson()
        )));
        assertEquals(expected, result);
    }

    /**
     * Преобразовать список сотрудников в отображение [компания -> сотрудник, суммарно проработавший в ней наибольшее время].
     * Гарантируется, что такой сотрудник будет один.
     */

    private Function<Employee, Map<String, PersonDurationPair>> getMapEmployerToPersonDurationPair = employee -> {
        Map<String, Integer> employerDurationPairs = employee.getJobHistory().stream()
                .collect(groupingBy(JobHistoryEntry::getEmployer, summingInt(JobHistoryEntry::getDuration)));
        Map<String, PersonDurationPair> resultMap = new HashMap<>();
        employerDurationPairs.forEach((k, v) -> resultMap.put(k, new PersonDurationPair(employee.getPerson(), v)));
        return resultMap;
    };

    private BinaryOperator<Map<String, PersonDurationPair>> mergeMaps = (map1, map2) -> {
        map2.forEach((k, v) -> map1.merge(k, v,
                (oldVal, newVal) -> oldVal.getDuration() <= newVal.getDuration() ? newVal : oldVal));
        return map1;
    };

    private Function<Map<String, PersonDurationPair>, Map<String, Person>> simplifyMap = map -> {
        Map<String, Person> resultMap = new HashMap<>();
        map.forEach((k, v) -> resultMap.put(k, v.getPerson()));
        return resultMap;
    };

    @Test
    public void greatestExperiencePerEmployer() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Map<String, Person> result = employees.stream()
                .parallel()
                .map(getMapEmployerToPersonDurationPair)
                .reduce(mergeMaps)
                .map(simplifyMap)
                .get();

        Map<String, Person> expected = new HashMap<>();
        expected.put("EPAM", employees.get(4).getPerson());
        expected.put("google", employees.get(1).getPerson());
        expected.put("yandex", employees.get(2).getPerson());
        expected.put("mail.ru", employees.get(2).getPerson());
        expected.put("T-Systems", employees.get(5).getPerson());
        assertEquals(expected, result);
    }
}

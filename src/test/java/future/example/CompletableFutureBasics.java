package future.example;

import lambda.data.Employee;
import lambda.data.Person;
import org.junit.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CompletableFutureBasics {

    @Test
    public void createNonEmpty() throws ExecutionException, InterruptedException {
        Person person = new Person("Иван", "Семенов", 33);

        // TODO Create non empty Optional
        Optional<Person> optPerson = Optional.of(person);

        assertTrue(optPerson.isPresent());
        assertEquals(person, optPerson.get());

        // TODO Create stream with a single element
        Stream<Person> streamPerson = Stream.of(person);

        List<Person> persons = streamPerson.collect(toList());
        assertEquals(1, persons.size());
        assertEquals(person, persons.get(0));

        // TODO Create completed CompletableFuture
        CompletableFuture<Person> futurePerson = CompletableFuture.completedFuture(person);

        CompletableFuture<Person> futurePerson2 = new CompletableFuture<>();
        futurePerson2.thenApply(Person::getFirstName).thenAccept(System.out::println);
//        stringCompletableFuture.thenApply(name -> "Mr." + name).thenAccept(System.out::println);


        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Person> submit = service.submit(() -> {
            TimeUnit.SECONDS.sleep(3);
            return person;
        });
        Future<String> submit1 = service.submit(() -> {
            return submit.get().getFirstName();
        });
        service.submit(() -> {
            try {
                System.out.println(submit1.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });


        boolean complete = futurePerson2.complete(person);

        assertTrue(futurePerson.isDone());
        assertEquals(person, futurePerson.get());
    }

    @Test
    public void createEmpty() throws ExecutionException, InterruptedException {
        // TODO Create empty Optional
        Optional<Person> optPerson = null;

        assertFalse(optPerson.isPresent());

        // TODO Create empty stream
        Stream<Person> streamPerson = null;

        List<Person> persons = streamPerson.collect(toList());
        assertThat(persons.size(), is(0));

        // TODO Complete CompletableFuture with NoSuchElementException
        CompletableFuture<Person> futurePerson = new CompletableFuture<>();
        // futurePerson.???

        assertTrue(futurePerson.isCompletedExceptionally());
        assertTrue(futurePerson.thenApply(x -> false)
                               .exceptionally(t -> t.getCause() instanceof NoSuchElementException).get());
    }

    @Test
    public void forEach() throws ExecutionException, InterruptedException {
        Person person = new Person("Иван", "Семенов", 33);

        // TODO Create non empty Optional
        Optional<Person> optPerson = Optional.of(person);

        CompletableFuture<Person> result1 = new CompletableFuture<>();

        // TODO using optPerson complete result1
        assertEquals(person, result1.get());

        // TODO Create stream with a single element
        Stream<Person> streamPerson = null;

        CompletableFuture<Person> result2 = new CompletableFuture<>();

        // TODO Using streamPerson.forEach complete result2
        assertEquals(person, result2.get());

        // TODO Create completed CompletableFuture
        CompletableFuture<Person> futurePerson = null;

        CompletableFuture<Person> result3 = new CompletableFuture<>();

        // TODO Using futurePerson.thenAccept complete result3
        assertEquals(person, result3.get());
    }

    @Test
    public void map() throws ExecutionException, InterruptedException {
        Person person = new Person("Иван", "Семенов", 33);

        // TODO Create non empty Optional
        Optional<Person> optPerson = null;

        // TODO get Optional<first name> from optPerson
        Optional<String> optFirstName = null;

        assertEquals(person.getFirstName(), optFirstName.get());

        // TODO Create stream with a single element
        Stream<Person> streamPerson = null;

        // TODO Get Stream<first name> from streamPerson
        Stream<String> streamFirstName = null;

        assertEquals(person.getFirstName(), streamFirstName.collect(toList()).get(0));

        // TODO Create completed CompletableFuture
        CompletableFuture<Person> futurePerson = null;

        // TODO Get CompletableFuture<first name> from futurePerson
        CompletableFuture<String> futureFirstName = null;

        assertEquals(person.getFirstName(), futureFirstName.get());
    }

    @Test
    public void flatMap() {
        Person person = new Person("Иван", "Семенов", 33);

        // TODO Create non empty Optional
        Optional<Person> optPerson = null;

        // TODO Using flatMap and .getFirstName().codePoints().mapToObj(p -> p).findFirst()
        // TODO get the first letter of first name if any
        Optional<Integer> optFirstCodePointOfFirstName = null;

        assertEquals(Integer.valueOf(1048), optFirstCodePointOfFirstName.get());

        // TODO Create stream with a single element
        Stream<Person> streamPerson = null;

        // TODO Using flatMapToInt and .getFirstName().codePoints() get codepoints stream from streamPerson
        IntStream codePoints = null;

        int[] codePointsArray = codePoints.toArray();
        assertEquals(person.getFirstName(), new String(codePointsArray, 0, codePointsArray.length));

        // TODO Create completed CompletableFuture
        CompletableFuture<Person> futurePerson = null;

        // TODO Get CompletableFuture<Employee> from futurePerson using getKeyByPerson and employeeDb
        CompletableFuture<Employee> futureEmployee = null;

        assertEquals(person, futureEmployee.join());
    }

    private static String getKeyByPerson(Person person) {
        return person.getFirstName() + "_" + person.getLastName() + "_" + person.getAge();
    }

    private static CompletableFuture<Employee> getEmployeeByKey(String key) {
        return CompletableFuture.completedFuture(null);
    }
}

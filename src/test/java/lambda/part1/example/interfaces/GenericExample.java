package lambda.part1.example.interfaces;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GenericExample {

    public static void main(String[] args) {
        List<Integer> listIntegers = new ArrayList<Integer>();
        listIntegers.add(1);
        listIntegers.add(2);
        listIntegers.add(3);
//        listIntegers.add("123");

        List intList = listIntegers;
        intList.add("abc");
        Object x = listIntegers.get(3);
        System.out.println(x);

        List<Integer> checkedList = Collections.checkedList(listIntegers, Integer.class);
        List rawIntList = checkedList;
        rawIntList.add("abc");
        Object stringVal = listIntegers.get(3);
        System.out.println(stringVal);

        for (Method method : Person.class.getDeclaredMethods()) {
            System.out.println(method);
            System.out.println(method.isBridge());
            System.out.println(method.isSynthetic());
        }

        List<? extends Number> values1 = createList();
        List<? extends Number> values2 = new ArrayList<Integer>();
        List<? extends Number> values3 = new ArrayList<Double>();

//        values1.add((Number)1);
        Number number = values1.get(0);
        Number number1 = values2.get(0);


        List<? super Number> values4 = new ArrayList<Number>();
        List<? super Number> values5 = new ArrayList<Object>();
        Object object = values4.get(0);
        values4.add(1);
        values4.add(1F);
        values4.add(1D);

        // Producer
        // Extends
        // Consumer
        // Super

        Integer[] intArray = {1, 2, 3, 4};
        Object[] objects = intArray;



        List<Integer> intValues = Arrays.asList(1, 2, 3, 4);
        Summator<Integer> intSummator = new Summator<Integer>() {
            @Override
            public Integer sum(Integer left, Integer right) {
                return left + right;
            }
        };
        sum(intValues, intSummator);

        List<?> someValues = new ArrayList<Object>();
        someValues.add(null);
        Object o = someValues.get(0);
    }

    public static <T extends Number> T sum(List<? extends T> values, Summator<? super T> summator) {
        T t = values.get(0);
        summator.sum(t, t);
        return null;
    }


    private static ArrayList<Number> createList() {
        return new ArrayList<Number>();
    }
}


class Person implements Comparable<Person> {

    private final String name;
    private final String surName;

    Person(String name, String surName) {
        this.name = name;
        this.surName = surName;
    }

    @Override
    public int compareTo(Person o) {
        return surName.compareTo(o.surName);
    }
}

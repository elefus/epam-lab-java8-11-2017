package lambda.part1.example.interfaces;

@SuppressWarnings("all")
interface A {

    default int getValue() {
        return 50;
    }
}

@SuppressWarnings("all")
class Example4 implements A {

    @Override
    public int getValue() {
        return 999;
    }
}

class Parent1 {
    public int getValue() {
        return -100;
    }
}

class Child1 extends Parent1 {

}

class Child2 extends Child1 implements A {

}

class ChildExample4 extends Example4 implements A {

    public static void main(String[] args) {
//        ChildExample4 object = new ChildExample4();
//        System.out.println(object.getValue());
        Child2 child1 = new Child2();
        System.out.println(child1.getValue());

    }
}

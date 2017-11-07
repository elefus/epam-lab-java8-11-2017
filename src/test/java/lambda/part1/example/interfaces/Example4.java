package lambda.part1.example.interfaces;

interface A {

    default int method() {
        return 50;
    }
}

class Example4 {

    public int getValue() {
        return 999;
    }
}

class ChildExample4 extends Example4 implements A {

    public static void main(String[] args) {
        ChildExample4 object = new ChildExample4();
        System.out.println(object.getValue());
    }
}

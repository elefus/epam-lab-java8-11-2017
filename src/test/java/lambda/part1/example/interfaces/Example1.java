package lambda.part1.example.interfaces;

class Parent {

    private static final int value = 42;

    public static int getValue() {
        return value;
    }
}

class Child extends Parent {

    private static final int value = 0;

    public static int getValue() {
        return value;
    }
}

class GrandChild extends Child {

    private static final int value = -42;
}

@SuppressWarnings("all")
class Launcher1 {

    public static void main(String[] args) {
        Parent parent = new Parent();
        System.out.println("--- Parent ---");
        System.out.println(parent.getValue());
        System.out.println(Parent.getValue());

        parent = new Child();
        System.out.println("\n--- Child ---");
        System.out.println(parent.getValue());
        System.out.println(Child.getValue());

        parent = new GrandChild();
        System.out.println("\n--- GrandChild ---");
        System.out.println(parent.getValue());
        System.out.println(GrandChild.getValue());

        parent = null;
        System.out.println("\n--- null ---");
        System.out.println(parent.getValue());
        System.out.println(((GrandChild)null).getValue());
    }
}
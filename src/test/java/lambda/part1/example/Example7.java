package lambda.part1.example;

import org.junit.Test;

@SuppressWarnings("unused")
public class Example7 {

    @FunctionalInterface
    private interface Usable {

        void use();
    }

    private void perform(Runnable runnable) {
        System.out.println("Runnable");
        runnable.run();
    }

    private void perform(Usable usable) {
        System.out.println("Usable");
        usable.use();
    }

    private void method() {
        System.out.println("I'm just another non-static method");
    }

    @Test
    public void ambiguousMethodReference() {
        Runnable runnable = () -> System.out.println("I'm runnable");
        perform(runnable);

        Usable usable = () -> System.out.println("I'm usable");
        perform(usable);
        
        Runnable method = this::method;
        perform(method);
        
//        perform(this::method);
    }
}

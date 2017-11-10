package lambda.part1.example;

import org.junit.Test;

import java.util.concurrent.Callable;

@SuppressWarnings("unused")
public class Lambdas6 {

    @Test
    public void runNormalRunnable() {
        Runnable r = () -> {
//            Thread.sleep(100);
        };
//        r.run();
    }

    @FunctionalInterface
    private interface ThrowableRunnable {

        void run() throws Exception;
    }

    @Test
    public void runThrowableRunnable() {
        ThrowableRunnable throwableRunnable = () -> {
//            Thread.sleep(100);
        };
//        throwableRunnable.run();
    }

    @Test
    public void callNormalCallable() {
        Callable<Integer> callable = () -> {
//            Thread.sleep(100);
            return 42;
        };

//        callable.call();
    }

    @Test
    public void throwRuntimeExceptionFromRunnable() {
        ThrowableRunnable throwableRunnable = () -> {
//            throw new IllegalStateException("Some runtime exception");
        };
//        throwableRunnable.run();
    }
}

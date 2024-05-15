package org.example.iteration5;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class RunnableFactorial {
    static int factorialResult;

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Factorial factorial = new Factorial(5);
//        executorService.execute(factorial);
        Future future = executorService.submit(factorial);
        executorService.shutdown();
        System.out.println("Таск закогчен? "+future.isDone());
        executorService.awaitTermination(1, TimeUnit.SECONDS); //Ждет либо 10 сек либо окончание потоков всех
        System.out.println(future.get());
        System.out.println("Таск закогчен? "+future.isDone());
        System.out.println(factorialResult);
    }
}

@AllArgsConstructor
class Factorial implements Runnable {
    int f;

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(5000);
        if (f <= 0) {
            System.out.println("плохое число");
            //Нельзя эксепшены выбрасывать а callable может
        } else {
            int result = 1;
            for (int i = 1; i <= f; i++) {
                System.out.println(result+" "+ i);
                result*=i;
            }
            RunnableFactorial.factorialResult = result;
        }
    }
}

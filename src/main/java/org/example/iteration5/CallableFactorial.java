package org.example.iteration5;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.concurrent.*;

public class CallableFactorial {
    static int factorialRes;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FactorialImpl factorial = new FactorialImpl(6);
        // Метод submit передает наше задание в тредпул для выполнения одним из потоков
        // и возвращает Future в котором хранится результат
        Future<Integer> integerFuture = executorService.submit(factorial);
        //Пока мы пытаемся получить ирезультат - а таск еще не закончил работу (к моменту вызова)
        // - наш тред(мейн) будет заблокирорван - пока таск не завершится
        try {
            System.out.println(integerFuture.isDone());
            System.out.println("Ждем результата " + LocalDateTime.now().getSecond());
            factorialRes = integerFuture.get(); //Блокирует поток - пока таск не завершит сваю работу и не вернет результат
            System.out.println(integerFuture.isDone());
            System.out.println("Получили результат " + LocalDateTime.now().getSecond());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        } finally {
            executorService.shutdown();
            System.out.println(factorialRes); //C Future не нужен awaittermination
        }


    }
}

@AllArgsConstructor
class FactorialImpl implements Callable<Integer> {
    int f;

    @Override
    public Integer call() throws Exception {
        if (f <= 0) {
            throw new Exception("Плохое число"); //Runnable так не умеет
        } else {
            int result = 1;
            for (int i = 1; i <= f; i++) {
                Thread.sleep(1000);
                result *= i;
            }
            return result;
        }
    }
}

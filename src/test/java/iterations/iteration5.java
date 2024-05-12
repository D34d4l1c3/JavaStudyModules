package iterations;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.example.iteration5.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@Log4j2
public class iteration5 {

    @Autowired
    ItemCatalogService itemCatalogService;
    @Autowired
    ConcurrentCatalogService concurrentCatalogService;

    @Test
    public void testThread() {
        Collection<String> stringCollection = new ArrayList<>();

        //Вараинты - унаследоватеться от Треда
        SimpleThread simpleThread = new SimpleThread(1, () -> System.out.println("Test"));
        simpleThread.start();
        SimpleRunnableClass simpleRunnableClass = new SimpleRunnableClass();
        simpleRunnableClass.run();

        Runnable runnable = () -> System.out.println("Runnable: Я что-то делаю и никому ничего не должен");
        runnable.run();
        Callable<String> callable = () -> "Callable: Я что-то делаю и возвращаю но не принимаю";
        String stringNum = "1";
        Callable<Integer> parseWord = () -> Integer.parseInt(stringNum);
//        Future<Integer> integerFuture =

    }

    @SneakyThrows
    @Test
    public void testCollectionThreads() {
//        //С помощью создания новых тредов самостоятельно
//        concurrentCatalogService.testThreadsCatalog();
//        //C возвращаемым результатом через вызовы Callable
//        Map<Callable<Integer>, Integer> resCall = concurrentCatalogService.testCallableCatalogWithResMap();
//        log.info("Всего было вызовов " + resCall.values().stream().reduce(0, Integer::sum));
//        //С помощью FixedThreadPool
//        log.info("Всего было вызовов " + concurrentCatalogService.testByExecutorsWithFutureByResult());
//        //Итоговый результат в классе для баловства
//        log.info("Итоговых покупок " + StockItem.getCountBuy() + " -- " + StockItem.getCountBuyAtomic());
//        Integer test = concurrentCatalogService.testByAsyncInvokes();
//        log.info(test);
//        Thread.sleep(5000);
    }

    @Test
    public void testExecutors() throws InterruptedException, ExecutionException {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " "+LocalDateTime.now().getSecond());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Callable<String> callable = () -> "Банана!";
        ExecutorService executorService = Executors.newFixedThreadPool(10); //FactoryMethod
        IntStream.range(0, 50).forEach(i -> executorService.execute(runnable));


//        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
//        IntStream.range(0, 10).forEach(i -> executorService1.execute(runnable));


        Future<String> future = executorService.submit(callable);
        System.out.println("AAAAAAAAAAA "+ future.get());
       // executorService1.awaitTermination(Long.MAX_VALUE,TimeUnit.NANOSECONDS);
//        executorService1.awaitTermination(5,TimeUnit.SECONDS);
//        while (!executorService1.isTerminated()){
//            executorService1.awaitTermination(5,TimeUnit.NANOSECONDS);
//        }

        System.out.println("Main End!");

    }
    @Test
    public void testForkJoinThreadPool(){
//        ForkJoinTask<Integer> forkJoinTask = () -> 1;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Integer> init = IntStream.range(0,20).map(i->i).boxed().toList();
        System.out.println(init);

//        CRecursiveTask cRecursiveTask = new CRecursiveTask(init);
//        forkJoinPool.invoke(cRecursiveTask);
////        cRecursiveTask.fork();
//        System.out.println(cRecursiveTask.join());
    }

    @Test
    public void testDeadLock2() {
        List<Thread> threads = new ArrayList<>();
        IntStream.range(0, 40).forEach(i -> {
            Thread thread = (i % 2) == 0
                    ? new Thread(() -> itemCatalogService.buyItem(i, true))
                    : new Thread(() -> itemCatalogService.stokeUpItem(i, i, true));
            thread.start();
            threads.add(thread);
            // joinT(thread);
        });
        threads.forEach(this::joinT);
    }

    @SneakyThrows
    public void joinT(Thread thread) {
        thread.join();
    }
    @SneakyThrows
    @Test
    public void testScheduledAndCachedExecutors(){
        Runnable runnable = () -> {
            log.info(Thread.currentThread().getName()+" begin "+LocalDateTime.now().getSecond());
            sleep();
            log.info(Thread.currentThread().getName()+" end");
        };
        Callable<String> callable = ()->"";

        //Создаст тредпул который будет создавать новые потоки по надобности
        //Если через 60 сек задание не приходит то удалит тред созданный ранее
        ExecutorService executorServiceCache = Executors.newCachedThreadPool();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        IntStream.range(0,10).forEach(i-> {
//            log.info(LocalDateTime.now().getSecond());
//            scheduledExecutorService.execute(runnable);//Аналогично обычному экзекутору пока что
//            scheduledExecutorService.schedule(runnable,2,TimeUnit.SECONDS);
            //Планирует задачу для периодического выполнение, запустится через 3 сек и с периодичносьтю в 1 секунду будет выполнятся
            //Период между началами обработки этого задания(между началами)
            //Т.е если задание 5 секунды выполняетя то новый запустится сразу как то закончит и ничего не ждет.
//            scheduledExecutorService.scheduleAtFixedRate(runnable,1,1,TimeUnit.SECONDS);
            //Между окончанием первого и началом следующего должнен пройти делей
          //  scheduledExecutorService.scheduleWithFixedDelay(runnable,3,4,TimeUnit.SECONDS);
            executorServiceCache.execute(runnable);
        });

        Thread.sleep(50000);
//        Future<String> future = scheduledExecutorService.submit(callable);
//        System.out.println("AAAAAAAAAAA "+ future.get());
//        scheduledExecutorService.shutdown();// В тестах не очень надо в случае с scheduleAtFixedRate нужно ждать

    }
    @SneakyThrows
    public void sleep(){
//        Thread.sleep(2000);
    }

}

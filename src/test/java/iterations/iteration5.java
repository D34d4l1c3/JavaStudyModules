package iterations;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.example.iteration5.*;
import org.example.iteration5.WarSimulate.Solder;
import org.example.iteration5.clientExporter.model.Contact;
import org.example.iteration5.clientExporter.model.ExportClientService;
import org.example.utils.MyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
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
    @Autowired
    ExportClientService exportClientService;

    @SneakyThrows
    @Test
    public void testCSVExport() {

//        Future<Integer> t = concurrentCatalogService.testByAsync();
////        Future<Integer> t2 = concurrentCatalogService.testByAsync();
////        Future<Integer> t3 = concurrentCatalogService.testByAsync();

        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            MyUtils.sleep(1000);
            return "testFuture";
        });

        new Thread(futureTask).start();
        log.info(futureTask.get());

//        List<Contact> list = exportClientService.queryContactList();
//        List<List<Contact>> separatedList = new ArrayList<>();
//        int chunkSize = (int) Math.ceil(list.size() / 10.);
//        IntStream.range(1, 10)
//                .forEach(i -> separatedList.add(list.subList(i * chunkSize, Math.min((i + 1) * chunkSize, list.size()))));
//
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//        separatedList.forEach(l -> executorService.submit(() -> exportClientService.writeContacts(l)));
//        int b = 4;
//        MyUtils.sleep(10000);
    }


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
    public void testCompletableFuture() {
        MyUtils.clog("Старт основного потока");
//        Function<?, ?> functionAsker = (CompletableFuture<Void> v) -> {
//            IntStream.range(0, 20).forEach(i -> {
//                MyUtils.sleep(600);
//                MyUtils.clog();("А мы уже приехали? " + v.isDone());
//            });
//            return null;
//        };
//        BiConsumer<? super Void, ? super Throwable> biConsumer = (str1, str2) -> MyUtils.clog();(str1 + "      " + str2);
        Runnable clock20Run = () -> {
            MyUtils.clog("Я считаю до 20 и отключась");
            IntStream.range(0, 20).forEach(i -> {
                MyUtils.sleep(1000);
                log.info(Thread.currentThread().getName() + "..." + i);
            });
            MyUtils.clog("Время вышло");
        };
        MyUtils.clog("Запускаю clock20Run");
        CompletableFuture<Void> asyncRunnable = CompletableFuture.runAsync(clock20Run);
        MyUtils.clog("Запускаю asyncSupplyHello");
        CompletableFuture<String> asyncSupplyHello = CompletableFuture.supplyAsync(() -> {
            MyUtils.clog("asyncSupplyHello буду ждать 3 сек и скажу привет");
            MyUtils.sleep(3000);
            return "Привет!";
        });
        MyUtils.clog("Говорю что с результатом asyncSupplyHello надо будет что-то сделать");
        CompletableFuture<String> resultOfHello = asyncSupplyHello.thenApply((String resMain) -> {
            MyUtils.clog("Я запущусь после asyncSupplyHello и использую его результат через 2 секунды");
            MyUtils.sleep(2000);
            MyUtils.clog("Я завершился но результат скажу когда позовут");
            return resMain + " Это его результат";
        });

        MyUtils.clog("Жду и получаю результат resultOfHello " + resultOfHello.get());
        MyUtils.clog("Жду завершения clock20Run");
        asyncRunnable.join(); //Дожидаемся конца
        MyUtils.clog("Конец основного потока");
    }


    @SneakyThrows
    @Test
    public void testCollectionThreads() {
        //С помощью создания новых тредов самостоятельно
        concurrentCatalogService.testThreadsCatalog(); //Без синхронайз можно получить отрицательные иногда
//        boolean b = concurrentCatalogService.getLock().newCondition().await(5,TimeUnit.SECONDS);
//        log.info(b);

//        //C возвращаемым результатом через вызовы Callable
//        Map<Callable<Integer>, Integer> resCall = concurrentCatalogService.testCallableCatalogWithResMap();
//        log.info("Всего было вызовов " + resCall.values().stream().reduce(0, Integer::sum));

//        //С помощью FixedThreadPool
        log.info("Всего было вызовов " + concurrentCatalogService.testByExecutorsWithFutureByResult());

//        //Итоговый результат в классе для баловства
//        log.info("Итоговых покупок " + StockItem.getCountBuy() + " -- " + StockItem.getCountBuyAtomic());
//        Integer test = concurrentCatalogService.testByAsyncInvokes();
//        log.info(test);
//        Thread.MyUtils.sleep(5000);
    }

    @Test
    public void testExecutors() throws InterruptedException, ExecutionException {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " " + LocalDateTime.now().getSecond());
            MyUtils.sleep(500);
        };
        Callable<String> callable = () -> "Банана!";
        ExecutorService executorService = Executors.newFixedThreadPool(10); //FactoryMethod
        IntStream.range(0, 50).forEach(i -> executorService.execute(runnable));


//        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
//        IntStream.range(0, 10).forEach(i -> executorService1.execute(runnable));


        Future<String> future = executorService.submit(callable);
        System.out.println("AAAAAAAAAAA " + future.get());
        // executorService1.awaitTermination(Long.MAX_VALUE,TimeUnit.NANOSECONDS);
//        executorService1.awaitTermination(5,TimeUnit.SECONDS);
//        while (!executorService1.isTerminated()){
//            executorService1.awaitTermination(5,TimeUnit.NANOSECONDS);
//        }

        System.out.println("Main End!");

    }

    @Test
    public void testForkJoinThreadPool() {

//        В основе ForkJoinPool лежит концепция двух основных действий: «fork» и «join».
//        «Fork» – это действие, при котором мы разбиваем большую задачу на несколько более мелких подзадач,
//        которые могут быть выполнены параллельно. «Join» – это процесс, при котором результаты выполнения
//        этих подзадач объединяются воедино, формируя результат выполнения исходной задачи.

//        ForkJoinTask<Integer> forkJoinTask = () -> 1;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Integer> init = IntStream.range(0, 20).boxed().toList();
        System.out.println(init);

        CRecursiveTask cRecursiveTask = new CRecursiveTask(init);
        forkJoinPool.invoke(cRecursiveTask);
//        cRecursiveTask.fork();
        System.out.println(cRecursiveTask.join());
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
        threads.forEach(MyUtils::joinT);
    }

    @Test
    void testPublish() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        MessageQueue<String> messageQueue = new MessageQueue<>();
        final int CNT_MSG = 50;

        Runnable publisher = () -> {
            for (int i = 0; i < CNT_MSG; i++) {
                log.info(messageQueue.getQueue().size());
                String message = "Отправляем сообщение номер: " + i;
                MyUtils.clog("Отправлено "+message);
                messageQueue.publish(message);
//                messageQueue.publishSimple(message);
                MyUtils.sleep(1);
            }
        };
        Runnable messageReceiver = () -> {
            for (int i = 0; i < CNT_MSG || !messageQueue.getQueue().isEmpty(); i++) {
                log.info(messageQueue.getQueue().size());
                try {
                    String message = messageQueue.receive();
                    MyUtils.clog("Получено сообщение:" + message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Future<?> t = executorService.submit(publisher);
        executorService.submit(messageReceiver);
//        executorService.submit(messageReceiver);
//        try {
//            t.get();
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }
        MyUtils.sleep(10000);

//        publisher.start();
//        worker1.start();
//        worker2.start();

//        publisher.join();
//        worker1.join();
//        worker2.join();
    }
    @SneakyThrows
    @Test
    public void testScheduledAndCachedExecutors() {
        Runnable runnable = () -> {
            log.info(Thread.currentThread().getName() + " begin " + LocalDateTime.now().getSecond());
            MyUtils.sleep(5000);
            log.info(Thread.currentThread().getName() + " end");
        };
        Callable<String> callable = () -> "";

        //Создаст тредпул который будет создавать новые потоки по надобности
        //Если через 60 сек задание не приходит то удалит тред созданный ранее
        ExecutorService executorServiceCache = Executors.newCachedThreadPool();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        IntStream.range(0, 10).forEach(i -> {
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

        MyUtils.sleep(50000);
//        Future<String> future = scheduledExecutorService.submit(callable);
//        System.out.println("AAAAAAAAAAA "+ future.get());
//        scheduledExecutorService.shutdown();// В тестах не очень надо в случае с scheduleAtFixedRate нужно ждать

    }

    @SneakyThrows
    @Test
    public void testParallelStream() {
        List<Solder> solders = new ArrayList<>();
        Random random = new Random();

        Function<Solder, Integer> function = (s) -> {
            MyUtils.sleep(1);
            MyUtils.clog("Я атакую на " + s.getDamage());
            return s.getDamage();
        };
        log.info(ForkJoinPool.getCommonPoolParallelism() + 1);         //Вычисляем количество потоков? эмпирически parallelStream 23 потока и мейн, тут 24
        log.info(Runtime.getRuntime().availableProcessors() - 1);
        IntStream.range(0, 100000).forEach(i -> solders.add(new Solder(random.nextInt(9) + 1)));
        long t = System.currentTimeMillis();


        ForkJoinTask<Void> task = new ForkJoinTask<Void>() {
            @Override
            public Void getRawResult() {
                return null;
            }

            @Override
            protected void setRawResult(Void value) {

            }

            @Override
            protected boolean exec() {
                Map<Integer, List<Solder>> solderMap2 = solders.stream()
                        .parallel()
                        .collect(Collectors.groupingBy(function, Collectors.toList()));

                System.out.println("Я закончил мапу составлять");
                return true;
            }
        };
//

        try (ForkJoinPool custom = new ForkJoinPool(20)) {
            custom.submit(task);
        }
        task.join();
//        MyUtils.sleep(100000);


//        Map<Integer, List<Solder>> solderMap = solders.parallelStream().collect(Collectors.groupingBy(function,Collectors.toList()));

        log.info("Время выполнения - " + (System.currentTimeMillis() - t) / 1000);
        int b = 4;
    }
}

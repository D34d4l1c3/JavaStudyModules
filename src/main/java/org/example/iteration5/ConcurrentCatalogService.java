package org.example.iteration5;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

@Service
@Log4j2
public class ConcurrentCatalogService {
    final int MAX_STOCK_ITEMS = 50;
    final int MAX_STOCK_ITEM_COUNT = 6;
    final int MIN_STOCK_ITEM_COUNT = 0;
    final int CNT_THREADS = 100;
    final Random random = new Random();

    @Getter
    final ReentrantLock lock = new ReentrantLock();
            //new StampedLock();
    //new ReentrantLock();

    @Getter
    private final ConcurrentHashMap<Integer, StockItem> charityStock = new ConcurrentHashMap<Integer, StockItem>();
    // HashMap<Integer, StockItem> charityStock = new HashMap<Integer, StockItem>();
    // Без - ConcurrentHashMap будет
    //Exception in thread "Thread-4" java.util.ConcurrentModificationException
    //	at java.base/java.util.HashMap$Values.forEach(HashMap.java:1068)
    //	at iterations.iteration5.lambda$testCollectionThreads$6(iteration5.java:62)
    //	at org.example.iteration5.SimpleThread.run(SimpleThread.java:15)
    private final HashMap<String, Thread> threadHashMap = new HashMap<>();
    private final HashMap<Integer, Callable<Integer>> callableHashMap = new HashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(CNT_THREADS);

    Runnable buyRunnable = () -> charityStock.values().forEach(stockItem -> buyItem(charityStock, stockItem));
    Callable<Integer> byAllAndSumCallable = () -> {
        AtomicInteger sumCall = new AtomicInteger(0);
        charityStock.values().forEach(stockItem -> {
            buyItem(charityStock, stockItem);
            sumCall.incrementAndGet();
        });
        return sumCall.get();
    };

    public ConcurrentCatalogService() {
        generateStock();
        generateThreads();
        generateCallable();
    }

    private void generateStock() {
        IntStream.range(0, MAX_STOCK_ITEMS)
                .forEach(i -> charityStock.put(i, new StockItem(i, random.nextInt(MAX_STOCK_ITEM_COUNT - MIN_STOCK_ITEM_COUNT + 1) + MAX_STOCK_ITEM_COUNT)));
    }

    private void generateThreads() {
        IntStream.range(0, CNT_THREADS).forEach(n -> threadHashMap.put("Thread_" + n, new SimpleThread(n, buyRunnable)));
    }

    private void generateCallable() {
        IntStream.range(0, CNT_THREADS).forEach(n -> callableHashMap.put(n, byAllAndSumCallable));
    }

    @SneakyThrows({InterruptedException.class})
    public Integer testByExecutorsWithFutureByResult() {
        List<Future<Integer>> futureCallRes = executorService.invokeAll(callableHashMap.values());
        log.info("Запустили и ожидаем завершения testByExecutorsWithFutureByResult " + LocalDateTime.now().getSecond());
        Integer res = futureCallRes.stream().map(integerFuture -> {
            Integer result = 0;
            try {
                result = integerFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error(e.getCause());
            }
            return result;
        }).reduce(0, Integer::sum);
        log.info("Завершили testByExecutorsWithFutureByResult");
        return res;
    }

    public void testThreadsCatalog() {
        threadHashMap.values().forEach(Thread::start);
        threadHashMap.values().forEach(this::joinT);
    }

    //    При использовании аннотации @Async по умолчанию Spring создает Executor
//    , у которого нет предела по количеству создаваемых потоков
    @SneakyThrows
    @Async
    public Future<Integer> testByAsync() {
        log.info(Thread.currentThread().getName() + " Do something Async");
        TimeUnit.SECONDS.sleep(5);

//        Callable<Integer> byAllAndSumCallable = () -> {
//            AtomicInteger sumCall = new AtomicInteger(0);
//            charityStock.values().forEach(stockItem -> {
//                buyItem(charityStock, stockItem);
//                sumCall.incrementAndGet();
//            });
//            return sumCall.get();
//        };
        return new AsyncResult<>(1);
    }

    @Async
    public Future<Integer> byAsync(StockItem stockItem) {
        buyItem(charityStock, stockItem);
        return new AsyncResult<>(1);
    }

    @SneakyThrows
    @Async
    public Future<Integer> byAsyncAllCatalog() {
        return new AsyncResult<>(byAllAndSumCallable.call());
    }

    public List<Future<Integer>> invokeCalcAllSum() {
        log.info(Thread.currentThread().getName() + " вызов метода invokeCalcAllSum");
        ExecutorService executorService1 = Executors.newFixedThreadPool(CNT_THREADS);

        List<Future<Integer>> futureList = new ArrayList<>();
        charityStock.values().forEach(stockItem -> {
            executorService1.submit(() -> byAsync(stockItem), futureList);
        });

        return futureList;
    }

    @SneakyThrows
    public Integer testNonAsync() {
        log.info(Thread.currentThread().getName() + " Do something Sync");
        TimeUnit.SECONDS.sleep(10);
        return 1;
    }

    public Map<Callable<Integer>, Integer> testCallableCatalogWithResMap() {
        log.info("Вызываю callable");
        Map<Callable<Integer>, Integer> callableSumMap = new HashMap<>();
        callableHashMap.values().forEach(i -> {
            callableSumMap.put(i, 0);
        });
        callableHashMap.values().forEach(c -> iterateCallBuy(callableSumMap, c));
        log.info("Конец вызовов callable");
        return callableSumMap;
    }

    public void iterateCallBuy(Map<Callable<Integer>, Integer> callableSumMap, Callable<Integer> el) {
        callableSumMap.compute(el, (key, val) -> {
            try {
                return val != null ? val + el.call() : 0;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @SneakyThrows
    public boolean buyItem(Map<Integer, StockItem> catalog, StockItem stockItem) { //synhronize или лок
        lock.lock();
        try {
            Integer key = stockItem.getId();
            Thread.sleep(new Random().nextInt(100));
            AtomicBoolean res = new AtomicBoolean(false);
            Optional.of(stockItem).ifPresent(item -> {
                if (item.getCount() > 0) {
                    item.buy();
                    log.info(Thread.currentThread().getName() + " Покупаю " + item + " осталось " + item.getCount());
                    res.set(true);
                } else {
                    if (item.getCount() < 0) log.info("Отрциательные значения " + item);
                    catalog.remove(key);
                    log.info(Thread.currentThread().getName() + " " + key + " кончился - убираю из каталога");
                    res.set(false);
                }
            });
            return res.get();
        }
        finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public void joinT(Thread thread) {
        thread.join();
    }
}

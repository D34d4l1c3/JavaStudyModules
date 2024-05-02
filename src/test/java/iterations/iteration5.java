package iterations;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.example.iteration5.SimpleRunnableClass;
import org.example.iteration5.SimpleThread;
import org.example.iteration5.StockItem;
import org.example.iteration5.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
//@Log4j2
public class iteration5 {
    @Autowired
    StockService stockService;

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

    @Test
    public void testCollectionThreads() {

        final int MAX_STOCK_ITEMS = 10;
        final int MAX_STOCK_ITEM_COUNT = 4;
        final int MIN_STOCK_ITEM_COUNT = 2;
        final int CNT_THREADS = 30;

        Random random = new Random();
//        HashMap<Integer, StockItem> charityStock = new HashMap<>();
        ConcurrentHashMap<Integer, StockItem> charityStock = new ConcurrentHashMap<Integer,StockItem>();

        IntStream.range(0, MAX_STOCK_ITEMS)
                .forEach(i -> charityStock.put(i, new StockItem(i, random.nextInt(MAX_STOCK_ITEM_COUNT - MIN_STOCK_ITEM_COUNT + 1) + MAX_STOCK_ITEM_COUNT)));

        HashMap<String, Thread> threadHashMap = new HashMap<>();
        IntStream.range(0, CNT_THREADS).forEach(n -> threadHashMap.put("Thread_" + n, new SimpleThread(n, () -> {
//            System.out.println(Thread.currentThread().getName());
            for (int i = 0; i < MAX_STOCK_ITEMS; i++) {
           // IntStream.range(0, CNT_THREADS).forEach(i -> {
                //int key = random.nextInt(MAX_STOCK_ITEMS);
              //  if(charityStock.get(i).getCount() > 0 ){
                charityStock.get(i).buy();
                    System.out.println("Thread_ " + n +
                            " Покупаю "+i+" раз " + charityStock.get(i)+ " осталось "+charityStock.get(i).getCount());
               // }

            }})));

        threadHashMap.values().forEach(Thread::start);
        threadHashMap.values().forEach( i -> {
            try {
                i.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        charityStock.values().forEach(System.out::println);
        System.out.println(StockItem.getCountBuy() +" -- " +StockItem.getCountBuyAtomic());
    }
}

package org.example.iteration5;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class ItemCatalogService {
    private static final Map<Integer, StockItem> buyCatalog = new HashMap<>(); //Monitor 1
    private static final Map<Integer, StockItem> stockCatalog = new HashMap<>(); //Monitor 2

        private final Random random = new Random();
    {
        IntStream.range(0, 50)
                .forEach(i -> stockCatalog.put(i,
                        new StockItem(i, random.nextInt(10 - 5 + 1) + 10)));
    }
    @SneakyThrows
    public void buyItem(Integer articul, boolean stock) {
        synchronized (buyCatalog) {
           // Thread.sleep(1000);
          //  if(buyCatalog.get(articul) == null) buyCatalog.wait();
            System.out.println("Покупаю элемент " + Thread.currentThread().getName() + " " + articul);
            System.out.println("Stock " + stock);
            if (stock) stokeUpItem(articul, 1, false);
           // buyCatalog.notify();
        }
    }

    @SneakyThrows //Debit
    public void stokeUpItem(Integer articul, Integer stockUpCnt, boolean buy) {
        synchronized (buyCatalog) {
            //Thread.sleep(1000);
            System.out.println("Беру на складе " + Thread.currentThread().getName() + " " + articul);
            System.out.println("Buy " + buy);
            buyCatalog.put(articul,stockCatalog.get(articul));
          //  stockCatalog.notify();
            if (buy) buyItem(articul, false);

        }
    }
}
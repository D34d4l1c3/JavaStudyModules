package org.example.iteration5;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class StockService {
    private static final int MAX_STOCK_ITEMS = 10;
    private static final int MAX_STOCK_ITEM_COUNT = 10;
    private static final int MIN_STOCK_ITEM_COUNT = 5;
    private static Random random = new Random();
    private static HashMap<Integer, StockItem> charityStock;

    private static void generateStock(){
        IntStream.range(0, MAX_STOCK_ITEMS)
                .forEach(i -> charityStock.put(i,
                        new StockItem(i, random.nextInt(MAX_STOCK_ITEM_COUNT - MIN_STOCK_ITEM_COUNT + 1) + MAX_STOCK_ITEM_COUNT)));
    }

}

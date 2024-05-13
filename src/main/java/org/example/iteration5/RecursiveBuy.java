package org.example.iteration5;

import lombok.AllArgsConstructor;

import java.util.concurrent.RecursiveTask;
@AllArgsConstructor
public class RecursiveBuy extends RecursiveTask<Integer> {
    ConcurrentCatalogService concurrentCatalogService;
    Integer keyItem;


    @Override
    protected Integer compute() {

        boolean res = concurrentCatalogService.buyItem(concurrentCatalogService.getCharityStock(),concurrentCatalogService.getCharityStock().get(0));
        return res ? 1 : 0;
    }
}

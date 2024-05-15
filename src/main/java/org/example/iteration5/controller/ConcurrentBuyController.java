package org.example.iteration5.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.example.iteration5.ConcurrentCatalogService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/catalog")
@AllArgsConstructor
@Log
@Profile("dev")
public class ConcurrentBuyController {
    static AtomicInteger sumAllBuying = new AtomicInteger(0);
    static List<Future<Integer>> futureCall = new ArrayList<>();
    ConcurrentCatalogService concurrentCatalogService;

    @SneakyThrows
    @GetMapping("/buyAll/")
    public String testThreadController() {
//        concurrentCatalogService.testByAsyncInvokes();
        log.info(Thread.currentThread().getName() + "------ Start -------");
//        Future<Integer> t = concurrentCatalogService.testByAsync();
//        Future<Integer> t2 = concurrentCatalogService.testByAsync();
//        Future<Integer> t3 = concurrentCatalogService.testByAsync();
//        t.get();
//        t2.get();
//        t3.get();
//        concurrentCatalogService.testNonAsync();
//        concurrentCatalogService.testNonAsync();
//        concurrentCatalogService.testNonAsync();

//        concurrentCatalogService.invokeCalcAllSum();
        ;
//        futureCall.add(concurrentCatalogService.byAsyncAllCatalog());
//        concurrentCatalogService.byAsyncAllCatalog();
        log.info(Thread.currentThread().getName() + "------- End ---------");
        return "End Request";
    }
    @GetMapping("/testAsync/")
    public String testAsync() {
        log.info(Thread.currentThread().getName() + "------ Start -------");
//        Future<Integer> t = concurrentCatalogService.testByAsync();
//        Future<Integer> t2 = concurrentCatalogService.testByAsync();
//        Future<Integer> t3 = concurrentCatalogService.testByAsync();
//        t.get();
//        t2.get();
//        t3.get();
//        concurrentCatalogService.testNonAsync();
//        concurrentCatalogService.testNonAsync();
//        concurrentCatalogService.testNonAsync();

//        concurrentCatalogService.invokeCalcAllSum();
        ;
        log.info(Thread.currentThread().getName() + "------- End ---------");
        return "End Request";
    }

    @SneakyThrows
    @GetMapping("/threadSumCnt/")
    public Integer testCallAllSum() {
        sumAllBuying.addAndGet(futureCall.stream().map(this::getFuture).reduce(0, Integer::sum));
        return sumAllBuying.get();
    }

    @GetMapping("/getSumCnt/")
    public Integer getSumCnt() {
        return sumAllBuying.get();
    }

    @SneakyThrows
    private Integer getFuture(Future<Integer> integerFuture) {
        return integerFuture.get();
    }
}

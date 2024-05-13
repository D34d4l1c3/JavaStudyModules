package org.example.iteration5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SumNumbers {
    private static final long value = 10L;
    private static long sum = 0L;
    private final static int NUM_THREADS = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Long>> futureSums = new ArrayList<>();
        long valueDivideByNumThreads = value / NUM_THREADS;
        IntStream.range(0, NUM_THREADS).forEach(i -> {
            long from = valueDivideByNumThreads * i + 1;
            long to = valueDivideByNumThreads * (i + 1);
            BiteSum task = new BiteSum(from, to);
            Future<Long> futSum = executorService.submit(task);
            futureSums.add(futSum);
        });

        futureSums.forEach(fRes -> {
            try {
                sum += fRes.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        executorService.shutdown();
        System.out.println(sum);
    }


}

class BiteSum implements Callable<Long> {
    long from;
    long to;
    long localSum;

    public BiteSum(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Long call() throws Exception {
        for (long i = from; i <= to; i++) {
            localSum += i;
        }
        System.out.println("Сумма с " + from + " по " + to + " :" + localSum);
        return localSum;
    }
}
package org.example.iteration5;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log
public class CRecursiveTask extends RecursiveTask<Integer> {
    List<Integer> integerList;

    public CRecursiveTask(List<Integer> integerList) {
        this.integerList = integerList;
    }

    @SneakyThrows
    @Override
    protected Integer compute() {
        log.info(Thread.currentThread().getName()+" Запуск compute");
        log.info(Thread.currentThread().getName()+" "+integerList.size()+"");
        if(integerList.size() <= 2){
            return integerList.stream().reduce(0,Integer::sum);
        }
        CRecursiveTask cRecursiveTaskFirst = new CRecursiveTask(integerList.subList(0,integerList.size()/2));
        CRecursiveTask cRecursiveTaskLast = new CRecursiveTask(integerList.subList(integerList.size()/2,integerList.size()));
        log.info(Thread.currentThread().getName()+" "+"Первый подмассив: "+cRecursiveTaskFirst.integerList.size()+" Второй подмассив "+cRecursiveTaskLast.integerList.size());
//        Thread.sleep(1000);
        cRecursiveTaskFirst.fork();
        cRecursiveTaskLast.fork();
        Integer res = cRecursiveTaskFirst.join()+ cRecursiveTaskLast.join();
        log.info(Thread.currentThread().getName()+" результат "+res);
        return res;

    }
}

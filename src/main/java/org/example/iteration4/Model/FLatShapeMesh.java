package org.example.iteration4.Model;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.iteration3.version1.model.FlatShape;
import org.jetbrains.annotations.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class FLatShapeMesh {

//    @Autowired
//    FLatShapeMesh fLatShapeMesh;
    private String name;
    List<? extends FlatShape> flatShapes;
    @PostConstruct
    public void initShapeService(){
        System.out.println("Я FLatShapeMesh инициализируюсь " + this);
    }
    @PreDestroy
    public void destroyBusinessService(){
        System.out.println("Я FLatShapeMesh уничтожаюсь "+this);
    }

//    @Cacheable не работает при внутренних вызовах методов в рамках одного и того же бина.
//    Кэширование активируется только при вызове методов через прокси бина.
    @Cacheable(value = "GetIntegers")
    public Integer getInteger(Integer i){
        return i;
    }
    public Integer getInteger2(){
//        return fLatShapeMesh.getInteger(1); падает в цикле зависимости на себя
        return getInteger(1);
    }

}

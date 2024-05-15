package org.example.iteration4.Model;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.iteration3.version1.model.FlatShape;
import org.example.iteration4.Model.duplicate.TestBean3;
import org.jetbrains.annotations.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizationAutoConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class FLatShapeMesh {

    @Autowired
    @Lazy
    @Getter
    FLatShapeMesh fLatShapeMesh;
    private String name;
    List<? extends FlatShape> flatShapes;
//    @Autowired
//    @Qualifier("reTestBean2")
//    ITestBeanInterface iTestBeanInterface;  //Field iTestBeanInterface in org.example.iteration4.Model.FLatShapeMesh required a single bean, but 2 were found:

    @PostConstruct
    public void initShapeService() {
        System.out.println("Я FLatShapeMesh инициализируюсь " + this);
//        iTestBeanInterface.getTests();
    }

    @PreDestroy
    public void destroyBusinessService() {
        System.out.println("Я FLatShapeMesh уничтожаюсь " + this); //После закрытия контекста не вызывается для прототипа
    }

    //    @Cacheable не работает при внутренних вызовах методов в рамках одного и того же бина.
//    Кэширование активируется только при вызове методов через прокси бина.
    @Cacheable(value = "GetIntegers")
    public Integer getInteger(Integer i) {
        return i;
    }

    //    @Transactional
    public Integer getInteger2() {
        return fLatShapeMesh.getInteger(1); //падает в цикле зависимости на себя
//        return getInteger(1);
    }
//    @AllArgsConstructor
//    static class FlatShapeMeshProxyCache {
//        private final FLatShapeMesh shapeMesh;
//        private Map<Integer,Integer> cacheRes = new HashMap();
//        private final TransactionManager transactionManager; //Autowire
//        Integer getInteger(Integer i) {
//            if (cacheRes.get(i) == null) {
//                cacheRes.put(i,shapeMesh.getInteger(i));
//            }
//            return cacheRes.get(i);
//        }
//        Integer getInteger2(Integer n) {
//            Transaction tx = transactionManager.getTransaction();
//            tx.start();
//            Integer b = shapeMesh.getInteger2(n);
//            tx.commit();
//           return b;
//        }//}
}

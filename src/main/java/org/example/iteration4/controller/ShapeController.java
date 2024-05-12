package org.example.iteration4.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.example.iteration3.version1.model.Shape;
import org.example.iteration4.Model.FLatShapeMesh;
import org.example.iteration4.Model.Place;
//import org.example.iteration4.property.SpringProperty;
import org.example.iteration4.service.BusinessShapeService;
import org.example.iteration4.service.MainUtils;
import org.example.iteration5.ConcurrentCatalogService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/shapes")
@AllArgsConstructor
@Log
public class ShapeController {
    BusinessShapeService businessShapeService; //Внедрено через конструктор
//    SpringProperty springProperty;
    MainUtils mainUtils;
    FLatShapeMesh fLatShapeMesh1;

    @GetMapping("/{id}")
    public Shape findShapeById(@PathVariable long id) {
        return businessShapeService.getShapeById(id);
    }
    @GetMapping("/")
    public String testController() {
        ApplicationContext context = mainUtils.getApplicationContext();
        int b = 4;
        FLatShapeMesh fLatShapeMesh =  context.getBean(FLatShapeMesh.class);
        FLatShapeMesh fLatShapeMesh1 =  context.getBean(FLatShapeMesh.class);
        FLatShapeMesh fLatShapeMesh2 = new FLatShapeMesh();
        BusinessShapeService businessShapeService1 = context.getBean(BusinessShapeService.class);
        BusinessShapeService businessShapeService2 = context.getBean(BusinessShapeService.class);
        Place place = context.getBean(Place.class);
//        Boolean test = springProperty.getTestProperty();
//        InvocationHandler handler = Proxy.getInvocationHandler(fLatShapeMesh1);
//        InvocationHandler handler1 = Proxy.getInvocationHandler(businessShapeService1);
        Integer i1 = fLatShapeMesh2.getInteger2();
        Integer i2 = fLatShapeMesh2.getInteger(1);
        Integer i3 = fLatShapeMesh.getInteger(1);
        Integer i4 = fLatShapeMesh.getInteger2();
        Integer i5 = fLatShapeMesh1.getInteger(1);
        Integer i6 = fLatShapeMesh1.getInteger2();
        businessShapeService1.getInteger();
        businessShapeService1.getInteger2();
        int c = 4;
        return "1";
    }

}

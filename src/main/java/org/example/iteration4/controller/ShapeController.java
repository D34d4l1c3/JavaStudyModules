package org.example.iteration4.controller;

import lombok.AllArgsConstructor;
import org.example.iteration3.version1.model.Shape;
import org.example.iteration4.Model.FLatShapeMesh;
import org.example.iteration4.Model.ITestBeanInterface;
import org.example.iteration4.Model.Place;
import org.example.iteration4.Model.TestBean;
import org.example.iteration4.Model.duplicate.TestBean2;
import org.example.iteration4.Model.duplicate.TestBean3;
import org.example.iteration4.service.BusinessShapeService;
import org.example.iteration4.service.MainUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@RestController
@RequestMapping("/shapes")
public class ShapeController {
    BusinessShapeService businessShapeService; //Внедрено через конструктор
    MainUtils mainUtils;
    FLatShapeMesh fLatShapeMesh1;
    @Autowired
    @Qualifier("getTestBean3Sec")
    TestBean3 testBean3;

    public ShapeController(BusinessShapeService businessShapeService,
                           MainUtils mainUtils,
                           FLatShapeMesh fLatShapeMesh1,
                           @Qualifier("reTestBean2") ITestBeanInterface iTestBeanInterface) {
        this.businessShapeService = businessShapeService;
        this.mainUtils = mainUtils;
        this.fLatShapeMesh1 = fLatShapeMesh1;
        this.iTestBeanInterface = iTestBeanInterface;
    }

    ITestBeanInterface iTestBeanInterface;
//    TestBean testBean;
    @GetMapping("/{id}")
    public Shape findShapeById(@PathVariable long id) {
        return businessShapeService.getShapeById(id);
    }
    @GetMapping("/")
    public String testController() {
        ApplicationContext context = mainUtils.getApplicationContext();
        int b = 4;
        iTestBeanInterface.getTests();
        TestBean2 testBean1 = context.getBean(TestBean2.class);
        TestBean3 testBean3 = context.getBean("getTestBean3Sec", TestBean3.class);
        TestBean3 testBean3_1 = context.getBean("getTestBean3", TestBean3.class);
        FLatShapeMesh t = fLatShapeMesh1.getFLatShapeMesh();
//        TestBean testBean2 = context.getBean(TestBean.class,"Banana1");
        FLatShapeMesh fLatShapeMesh =  context.getBean(FLatShapeMesh.class);
//        FLatShapeMesh fLatShapeMesh1 =  context.getBean(FLatShapeMesh.class);
//        FLatShapeMesh fLatShapeMesh2 = new FLatShapeMesh();
//        BusinessShapeService businessShapeService1 = context.getBean(BusinessShapeService.class);
//        BusinessShapeService businessShapeService2 = context.getBean(BusinessShapeService.class);
//        Place place = context.getBean(Place.class);
//        InvocationHandler handler = Proxy.getInvocationHandler(fLatShapeMesh1);
//        InvocationHandler handler1 = Proxy.getInvocationHandler(businessShapeService1);
//        Integer i1 = fLatShapeMesh2.getInteger2();
//        Integer i2 = fLatShapeMesh2.getInteger(1);
//        Integer i3 = fLatShapeMesh.getInteger(1);
//        Integer i4 = fLatShapeMesh.getInteger2();
//        Integer i5 = fLatShapeMesh1.getInteger(1);
//        Integer i6 = fLatShapeMesh1.getInteger2();
//        businessShapeService1.getInteger();
//        businessShapeService1.getInteger2();
        int c = 4;
        return "1";
    }

}

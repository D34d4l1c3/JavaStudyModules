package org.example.iteration4.controller;

import lombok.AllArgsConstructor;
import org.example.iteration3.version1.model.Shape;
import org.example.iteration4.Model.FLatShapeMesh;
import org.example.iteration4.service.BusinessShapeService;
import org.example.iteration4.service.MainUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shapes")
@AllArgsConstructor
public class ShapeController {
    BusinessShapeService businessShapeService; //Внедрено через конструктор
    MainUtils mainUtils;
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
        BusinessShapeService businessShapeService1 = context.getBean(BusinessShapeService.class);
        BusinessShapeService businessShapeService2 = context.getBean(BusinessShapeService.class);
        int c = 4;
        return "1";
    }

}

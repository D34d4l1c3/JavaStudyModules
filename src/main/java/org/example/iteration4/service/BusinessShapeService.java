package org.example.iteration4.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.iteration3.version1.model.Shape;
import org.example.iteration3.version1.service.ShapeService;
import org.example.iteration4.Model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessShapeService {
    @Autowired
    MainUtils mainUtils;

    @Autowired
    Building building;

    public Shape getShapeById(Long id){
       return ShapeService.getShapeByIdFromDB(id);
    }
    @PostConstruct
    public void initShapeService(){
        System.out.println("Я инициализируюсь " + this);
    }
    @PreDestroy
    public void destroyBusinessService(){
        System.out.println("Я уничтожаюсь "+this);
    }
}

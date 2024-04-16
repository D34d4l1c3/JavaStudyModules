package org.example.iteration4.service;

import org.example.iteration4.Model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MainUtils {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    public Building building;
    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}

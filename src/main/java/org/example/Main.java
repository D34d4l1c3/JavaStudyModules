package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableCaching
@ImportResource("classpath:applicationContext.xml")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        Building building = context.getBean("building", Building.class);
//        System.out.println(building.getHeight());

    }
}

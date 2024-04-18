package org.example.iteration4.config;

import org.example.iteration4.Model.Building;
import org.example.iteration4.Model.Place;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CustomSpringConfiguration {
    @Bean
    @Scope("prototype")
    public Place place(){
        return new Place(new Building(1));
    }
}

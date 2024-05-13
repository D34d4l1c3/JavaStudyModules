package org.example.iteration4.config;

import org.example.iteration4.Model.Building;
import org.example.iteration4.Model.Place;
//import org.example.iteration4.Model.duplicate.TestBean;
import org.example.iteration4.Model.TestBean;
import org.example.iteration4.Model.duplicate.TestBean2;
import org.example.iteration4.Model.duplicate.TestBean3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@Configuration
public class CustomSpringConfiguration {
    @Bean
    @Scope("prototype")
    public Place place() {
        return new Place(new Building(1));

    }

    @Bean(name = "reTestBean2")
    @ConditionalOnProperty(prefix = "re", name = "reTestBean2", havingValue = "TestBean")
    public TestBean2 getTestBean2(@Value("Banana") String name) {
        return new TestBean2(new TestBean());
    }

//    @Bean
//    TestBean getTestBean() {
//        return new TestBean("_conf");
//    }
//    @Bean
//    TestBean getTestBeanSec() {
//        return new TestBean("_configure");
//    }

//    @Bean
//    TestBean getSecTestBean() {
//    TestBean getSecTestBean() {
//        return new TestBean("Test2");
//    }

//    @Bean
//    public TestBean3 getBean1() {
//        return new TestBean3("1");
//    }

//    @Primary
//    @Bean
//    public TestBean3 getBean2() {
//        return new TestBean3("2");
//    }

}

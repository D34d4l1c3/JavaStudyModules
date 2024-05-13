package org.example.iteration4.Model;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Component("testbean2") //Как вариант
//@Primary
@ConditionalOnProperty(
        value="test.bean.enabled",
        matchIfMissing = false)
@Component
public class TestBean implements ITestBeanInterface {
    @Value("TestBean")
    String name;
    @Override
    public void getTests() {
        System.out.println("testbean2");
    }
}

package org.example.iteration4.Model.duplicate;

import lombok.AllArgsConstructor;
import org.example.iteration4.Model.ITestBeanInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component("testbean")
@ConditionalOnProperty(
        value="test.bean.enabled",
        havingValue="false",
        matchIfMissing = true)
public class TestBean implements ITestBeanInterface {
   @Value("TestBean")
    String name;

    @Override
    public void getTests() {
        System.out.println("testbean");
    }
}

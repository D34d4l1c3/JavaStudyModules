package org.example.iteration4.Model.duplicate;

import lombok.AllArgsConstructor;
import org.example.iteration4.Model.ITestBeanInterface2;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class TestBean3 implements ITestBeanInterface2 {
    String name;

    @Override
    public void getTests2() {
        System.out.println("TestBean3 get test");
    }
}

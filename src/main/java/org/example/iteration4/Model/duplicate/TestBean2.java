package org.example.iteration4.Model.duplicate;

import org.example.iteration4.Model.ITestBeanInterface;
import org.example.iteration4.Model.TestBean;

public class TestBean2 implements ITestBeanInterface {
    TestBean testBean;
    public TestBean2(TestBean bean){
        this.testBean = bean;
    }

    @Override
    public void getTests() {
        System.out.println("TestBean2");
    }
}

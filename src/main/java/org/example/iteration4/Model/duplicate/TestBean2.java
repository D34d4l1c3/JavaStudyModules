package org.example.iteration4.Model.duplicate;

import org.example.iteration4.Model.ITestBeanInterface;
import org.example.iteration4.Model.ITestBeanInterface2;
import org.example.iteration4.Model.TestBean;

public class TestBean2 implements ITestBeanInterface, ITestBeanInterface2 {
    TestBean testBean;
    public TestBean2(TestBean bean){
        this.testBean = bean;
    }

    @Override
    public void getTests2() {
        System.out.println("TestBean2 getTests2");
    }

    @Override
    public void getTests() {
        System.out.println("TestBean2 getTests");
    }
}

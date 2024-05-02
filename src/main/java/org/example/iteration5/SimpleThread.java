package org.example.iteration5;

import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class SimpleThread extends Thread {
    Integer numThread;
    Runnable runFunction;

    @Override
    public void run() {
//        System.out.println("Начинается тред SimpleThread "+numThread);
        runFunction.run();
    }
}

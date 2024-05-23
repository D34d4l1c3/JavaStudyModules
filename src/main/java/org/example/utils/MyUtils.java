package org.example.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@UtilityClass
@Log4j2
public class MyUtils {
    @SneakyThrows
    public void sleep(int mls) {
        Thread.sleep(mls);
    }
    @SneakyThrows
    public void joinT(Thread thread) {
        thread.join();
    }
    public void clog(String msg) {
        log.info(Thread.currentThread().getName() + " " + msg);
    }
}

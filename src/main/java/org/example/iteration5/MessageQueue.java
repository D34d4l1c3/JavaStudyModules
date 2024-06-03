package org.example.iteration5;

import lombok.Getter;
import lombok.SneakyThrows;
import org.example.utils.MyUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue<T> {

    @Getter
    private Queue<T> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition hasMessages = lock.newCondition();

    public void publish(T message) {
        lock.lock();
        try {
            queue.offer(message);
            hasMessages.signal();
        } finally {
            lock.unlock();
        }
    }

    public void publishSimple(T message) {
        queue.offer(message);
//        hasMessages.signal();
    }

    @SneakyThrows
    public T receive() {
        lock.lock();
        try {
            MyUtils.sleep(300); //предположим мы что-то долго делаем с вычитанным сообщением долго
            while (queue.isEmpty()) {
                hasMessages.await();
            }
            return queue.poll();
        } finally {
            lock.unlock();
        }
    }

}
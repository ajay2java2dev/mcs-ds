package Entities;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class Queue<T> {

    BlockingQueue<T> queue;

    public Queue(int size) {

        queue = new ArrayBlockingQueue<T>(size);
    }

    public boolean put(T item) {
        boolean val = false;
        try {
            queue.put(item);
            val = true;
        } catch (InterruptedException e) {
            val = false;
        }
        return val;
    }

    public T take() {
        T t = null;
        try {
            t = queue.take();
        } catch (InterruptedException e) {
            t = null;
        }
        return t;
    }

}

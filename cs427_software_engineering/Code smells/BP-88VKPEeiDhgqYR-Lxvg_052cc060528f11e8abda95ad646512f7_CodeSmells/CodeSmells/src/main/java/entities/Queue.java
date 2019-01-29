package entities;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class Queue<T> {

    BlockingQueue<T> blockingQueue;

    public Queue(int size) {
        blockingQueue = new ArrayBlockingQueue<T>(size);
    }

    public boolean put(T item) {
        try {
            blockingQueue.put(item);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public T take() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

}

package no19_lock_fair;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock独特特性-公平锁
 * 防止造成饥饿
 * Auth: miaopeng
 * Date: 2018-04-24 17:54:35
 */
public class Main {

    /**
     * 参数设置成true表示公平锁，不会造饥饿，但是为了保证顺序，性能比较低
     */
    private static ReentrantLock lock = new ReentrantLock(true);

    private static class MyThread extends Thread {
        public void run () {
            while (true) {
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            MyThread myThread = new MyThread();
            myThread.start();
        }
    }
}

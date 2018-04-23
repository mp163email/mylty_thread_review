package no2_wait_notify;

import java.util.concurrent.TimeUnit;

/**
 * wait  notify  notifyAll 的使用
 * 1.wait notify notifyAll 都要加上synchronized
 * 2.wait 要加异常处理
 * 3.notify 释放lock对象上第一个被wait的线程
 * 4.notifyAll 按照wait的时间顺序倒叙释放
 * Created by miaopeng on 2018 4-20 020
 */
public class Main {

    private static Object lock = new Object();

    /**
     * Wait线程
     */
    private static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread().getName() + ", wait before");
                    lock.wait();
                    System.out.println(Thread.currentThread().getName() + ", wait after");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Notify线程
     * notify的时候, 释放顺序为第一个wait的线程
     */
    private static class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                lock.notify();
                System.out.println("随机释放一个在lock对象上wait的线程");
            }
        }
    }

    /**
     * NotifyAll线程
     * notifyAll的时候, 释放顺序为按wait时间倒序
     */
    private static class NotifyALL implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                lock.notifyAll();
                System.out.println("释放所有在lock对象上wait的线程");
            }
        }
    }

    public static void main(String[] args) throws Exception {

        //启动5个wait线程
        for (int i = 0; i < 5; i++) {
            Thread waitThread = new Thread(new Wait(), "THREAD" + i);
            waitThread.start();
        }

        //经过3秒随机释放一个在lock上wait的线程
        TimeUnit.SECONDS.sleep(3);
        new Thread(new Notify()).start();

        //再经过3秒释放全部在lock上wait的线程
        TimeUnit.SECONDS.sleep(3);
        new Thread(new NotifyALL()).start();
    }

}

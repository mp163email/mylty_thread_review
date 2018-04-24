package no23_lock_trylock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock的tryLock方法的使用
 * tryLock: 如果锁被其他线程持有，则立刻返回false
 * tryLock(int num, TimeUnit timeUnit): 如果锁被其他线程持有，则等待指定时间, 时间到了再次获取
 * 使用tryLock肯定不会产生死锁，因为他发现锁被其他线程占有的时候，不会在那傻傻等待，而是立刻返回
 * Auth: miaopeng
 * Date: 2018-04-24 20:05:50
 */
public class Main {

    private static ReentrantLock lock = new ReentrantLock();

    private static class MyThread extends Thread {

        public void run () {
            try {
                if (lock.tryLock(1, TimeUnit.SECONDS)) {//当发现锁被其他线程持有的时候，等待指定时间再次获取
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(3);
                } else {
                    System.out.println("有其他线程持有锁,但是我不在那傻傻的等待");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        MyThread myThread1 = new MyThread();
        myThread1.start();

        TimeUnit.SECONDS.sleep(1);

        MyThread myThread2 = new MyThread();
        myThread2.start();

    }
}

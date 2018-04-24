package no21_lock_interrupted;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用lock的lockInterruptibly方法破除死锁的僵局
 * lockInterruptbily方法会上锁，但同时也会响应中断, 中断后，会释放当前持有的锁
 * Auth: miaopeng
 * Date: 2018-04-24 19:45:17
 */
public class Main {

    private static ReentrantLock lock1 = new ReentrantLock();

    private static ReentrantLock lock2 = new ReentrantLock();

    /**
     * 会产生死锁的线程
     */
    private static class DeadThread extends Thread {

        private int num;

        public DeadThread(int num) {
            this.num = num;
        }

        public void run () {
            if (num == 1) {
                try {
                    lock1.lockInterruptibly();
                    System.out.println(Thread.currentThread().getName() + ", lock1");
                    TimeUnit.SECONDS.sleep(5);
                    lock2.lockInterruptibly();
                    System.out.println(Thread.currentThread().getName() + "如果死锁,我不会走到这，走到这说明死锁已被破除");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (lock1.isHeldByCurrentThread()) {
                        lock1.unlock();
                    }
                    if (lock2.isHeldByCurrentThread()) {
                        lock2.unlock();
                    }
                }
            } else {
                try {
                    lock2.lockInterruptibly();
                    System.out.println(Thread.currentThread().getName() + ", lock2");
                    TimeUnit.SECONDS.sleep(5);
                    lock1.lockInterruptibly();
                    System.out.println(Thread.currentThread().getName() + "如果死锁,我不会走到这，走到这说明死锁已被破除");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (lock2.isHeldByCurrentThread()) {
                        lock2.unlock();
                    }
                    if (lock1.isHeldByCurrentThread()) {
                        lock1.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        DeadThread deadThread1 = new DeadThread(1);
        deadThread1.start();

        DeadThread deadThread2 = new DeadThread(2);
        deadThread2.start();

        //10秒后通过其中一个线程, 来破除死锁
        TimeUnit.SECONDS.sleep(10);

        deadThread2.interrupt();//lock的lockInterruptibly()方法，除了会上锁还会相应中断,如果中断，会抛出异常，同时会释放当前自己的锁
    }
}

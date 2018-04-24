package no20_lock_condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试Condition的使用
 * Auth: miaopeng
 * Date: 2018-04-24 18:17:11
 */
public class Service {

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();

    private Condition condition2 = lock.newCondition();

    /**
     * 等待方法一
     */
    public void wait1 () {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "wait1");
            condition1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 唤醒方法一
     */
    public void notify1 () {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "notify1");
            condition1.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 等待方法二
     */
    public void wait2 () {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "wait2");
            condition2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 唤醒方法二
     */
    public void notify2 () {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "notify2");
            condition2.signal();
        } finally {
            lock.unlock();
        }
    }
}

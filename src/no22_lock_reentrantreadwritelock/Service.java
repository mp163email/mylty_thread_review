package no22_lock_reentrantreadwritelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 测试读写锁作用范围
 * Auth: miaopeng
 * Date: 2018-04-24 20:29:33
 */
public class Service {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 测试读锁
     */
    public void read () {
        try {
            readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "---sleep before read");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 测试写锁
     */
    public void write () {
        try {
            readWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + "---sleep before write");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

}

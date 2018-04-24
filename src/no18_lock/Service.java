package no18_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试ReentrantLock的使用
 * unlock放在finally里, 避免因忘记unlock而造成死锁
 * Auth: miaopeng
 * Date: 2018-04-24 17:33:05
 */
public class Service {

    private ReentrantLock lock = new ReentrantLock();

    public void methodA () {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "--methodA");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void methodB () {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "--methodB");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

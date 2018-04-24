package no17_lock_synchronized;

/**
 * ReentrantLock和synchronized对比
 * Auth: miaopeng
 * Date: 2018-04-24 16:45:59
 */
public class Explain {

    /**
     * 1.从实现区别：synchronized基于JVM实现, ReentrantLock基于JDK需要自己写代码实现
     * 2.性能区别：synchronized优化后和ReentrantLock差不多,官方甚至推荐使用synchronized
     * 3.功能区别：synchronized使用比较简洁， 并由编译器保证锁的加锁和释放; ReentrantLock需要手动加锁和释放, 所以为了避免死锁，忘记释放锁，一般使用finally释放锁
     * 4.ReentrantLock的独有功能：
     *      4-1：可以指定是公平锁还是非公平锁, 而synchronized没有
     *      4-2：提供了Condition可以分组唤醒需要唤醒的线程, 而synchronized只能随机唤醒一个或者全部唤醒， 比wait-notify更细粒度
     *      4-3：提供了一个能够中断等待锁的机制，通过lock.lockInterruptibly方法实现
     * 5.ReentrantLock实现原理：是一种自旋锁，通过循环调用CAS操作来实现加锁。
     * ***想尽办法避免线程进入内核的阻塞状态是我们去分析和理解锁设计的关键钥匙***
     */
}

package no3_thread_hangup;

import java.util.concurrent.locks.LockSupport;

/**
 * 几种线程挂起的方式
 * 1.sleep方式，synchronized不是必须的， 持有锁, sleep多少秒后自动唤醒
 * 2.wait方式, synchronized必须要加上,否则会报错.  不持有锁, 唤醒需要notify或者notifyAll方法
 * 3.suspend方式, synchronized不是必须的，持有锁, 唤醒需要调用resume方法
 *   过期方法, 不推荐使用, 挂起时不释放锁, 如果resume方法在suspend之前调用, 会造成死锁
 * 4.LockSupport  park/parkNanos 方法, synchronized不是必须的，持有锁, 唤醒需要unpark方法, 或者等待时间结束
 * Created by miaopeng on 2018 4-20 020
 */
public class Main {

    private static Object lock_wait = new Object();
    private static Object lock_suspend = new Object();
    private static Object lock_park = new Object();

    /**
     * sleep方式，synchronized不是必须的， 持有锁, sleep多少秒后自动唤醒
     */
    private static class Sleep extends Thread {
        @Override
        public void run() {
            System.out.println("sleep--start");
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sleep--end");
        }
    }

    /**
     * wait方式, synchronized必须要加上,否则会报错.  不持有锁, 唤醒需要notify或者notifyAll方法
     */
    private static class Wait extends Thread {
        @Override
        public void run() {
            System.out.println("wait---start");
            try {
                synchronized (lock_wait) {
                    lock_wait.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("wait---end");
        }
    }

    /**
     * suspend方式, synchronized不是必须的，持有锁, 唤醒需要调用resume方法
     * 过期方法, 不推荐使用, 挂起时不释放锁, 如果resume方法在suspend之前调用, 会造成死锁
     */
    private static class Suspend extends Thread {
        public void run () {
            System.out.println("suspend---start");
            synchronized (lock_suspend) {
                suspend();
            }
            System.out.println("suspend---end");
        }
    }

    /**
     * LockSupport  park/parkNanos 方法
     * synchronized不是必须的，持有锁
     * 唤醒需要unpark方法, 或者等待时间结束
     */
    private static class LockSupportPark extends Thread {
        public void run () {
            System.out.println("park--start");
            synchronized (lock_park) {
//                LockSupport.parkNanos(1000 * 1000 * 1000 * 1000 * 3);//这个地方换算有问题么? 单位是皮秒还是纳秒?
                LockSupport.park();
            }
            System.out.println("park-end");
        }
    }

    private static class ReleaseLock extends Thread {
        private int whichLock;
        private Suspend suspend;

        public ReleaseLock (int whichLock, Suspend suspend) {
            this.whichLock = whichLock;
            this.suspend = suspend;
        }


        public void run () {
            if (whichLock == 1) {
                synchronized (lock_wait) {
                    lock_wait.notify();
                }
            } else if (whichLock == 2) {
                synchronized (lock_suspend) {
                    suspend.resume();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        //测试sleep
        Sleep sleep = new Sleep();
        sleep.start();

        Thread.sleep(1000);

        //测试wait
        Wait wait = new Wait();
        wait.start();

        Thread.sleep(1000);

        synchronized (lock_wait) {
            lock_wait.notifyAll();
        }

        Thread.sleep(1000);

        //测试suspend
        Suspend suspend = new Suspend();
        suspend.start();

        Thread.sleep(1000);

        suspend.resume();

        //测试park
        LockSupportPark lockSupportPark = new LockSupportPark();
        lockSupportPark.start();

        Thread.sleep(1000);

        LockSupport.unpark(lockSupportPark);

    }
}

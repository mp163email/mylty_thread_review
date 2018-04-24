package no20_lock_condition;

import java.util.concurrent.TimeUnit;

/**
 * Condition的使用 await 和 signal 方法的使用
 * 和Object的wait-notify相比, condition的wait和notify方法更细粒度
 * Object的wait和notify是针对于锁的
 * Conditiond的await和signal方法是针对于某个Condition的，作用范围更细
 * Auth: miaopeng
 * Date: 2018-04-24 18:10:43
 */
public class Main {

    private static class Thread1 extends Thread {
        private Service service;
        public Thread1 (Service service) {
            this.service = service;
        }
        public void run () {
            service.wait1();
        }
    }

    private static class Thread2 extends Thread {
        private Service service;
        public Thread2 (Service service) {
            this.service = service;
        }
        public void run () {
            service.wait2();
        }
    }

    public static void main(String[] args) throws Exception {

        Service service = new Service();

        Thread1 thread1 = new Thread1(service);
        thread1.start();

        Thread2 thread2 = new Thread2(service);
        thread2.start();

        TimeUnit.SECONDS.sleep(3);
        service.notify1();

        TimeUnit.SECONDS.sleep(3);
        service.notify2();

    }
}

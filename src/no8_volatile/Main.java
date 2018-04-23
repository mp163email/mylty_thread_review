package no8_volatile;

import java.util.concurrent.TimeUnit;

/**
 * volatile线程可见性， 在不同的线程里，此变量都可见（可操作）
 * 在一个线程里，修改另一个线程的变量，能够生效
 * Auth: miaopeng
 * Date: 2018-04-23 09:55:29
 */
public class Main {
    public static void main(String[] args) throws Exception {

        MyThread myThread1 = new MyThread();
        myThread1.start();
        MyThread myThread2 = new MyThread();
        myThread2.start();

        //5秒后关掉线程1
        TimeUnit.SECONDS.sleep(5);
        myThread1.setRun(false);

        //再过5秒关掉线程2
        TimeUnit.SECONDS.sleep(5);
        myThread2.setRun(false);
    }
}

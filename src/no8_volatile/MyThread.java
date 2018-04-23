package no8_volatile;

import java.util.concurrent.TimeUnit;

/**
 * 含有volitle的线程类
 * Auth: miaopeng
 * Date: 2018-04-23 09:51:07
 */
public class MyThread extends Thread {

    /**
     * volatile开关控制
     */
    private volatile boolean running = true;

    public void setRun (boolean flag) {
        this.running = flag;
    }

    public void run () {
        while (running) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

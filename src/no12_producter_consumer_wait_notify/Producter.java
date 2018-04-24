package no12_producter_consumer_wait_notify;

import java.util.concurrent.TimeUnit;

/**
 * 生产者线程
 * Auth: miaopeng
 * Date: 2018-04-23 10:55:43
 */
public class Producter extends Thread {

    private Container container;

    //生产间隔（多久生产一个）
    private int productInterval;

    public Producter (int productInterval, Container container) {
        this.productInterval = productInterval;
        this.container = container;
    }

    public void run () {
        try {
            while (true) {
                container.put();
                TimeUnit.SECONDS.sleep(productInterval);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

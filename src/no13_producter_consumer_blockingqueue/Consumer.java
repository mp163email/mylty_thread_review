package no13_producter_consumer_blockingqueue;

import java.util.concurrent.TimeUnit;

/**
 * 消费者线程
 * Auth: miaopeng
 * Date: 2018-04-23 11:00:21
 */
public class Consumer extends Thread {

    //消费间隔
    private int consumerInterval;

    private Container container;

    public Consumer (int consumerInterval, Container container) {
        this.consumerInterval = consumerInterval;
        this.container = container;
    }

    public void run () {
        try {
            while (true) {
                container.get();
                TimeUnit.SECONDS.sleep(consumerInterval);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

package no12_producter_consumer_wait_notify;

import java.util.ArrayList;
import java.util.List;

/**
 * 容器类, 向容器里放数据, 从容器里取数据
 * Auth: miaopeng
 * Date: 2018-04-23 10:34:40
 */
public class Container {

    //数据管道
    private List<String> dataChannel = new ArrayList<>();

    /**
     * 消费方法
     * 加锁是因为ArrayList不是线程安全的, 保证同一时间只有一个线程对ArrayList进行操作
     */
    public synchronized void get () {
        try {
            //管道里没有可消费的数据了, 消费线程挂起, 等待生产线程向管道里生产数据, 直到有数据位置
            while (dataChannel.size() <= 0) {
                this.wait();
            }

            //如果有数据, 唤醒生产者别再挂起了,

            //从管道里取出数据
            String data = dataChannel.get(0);
            System.out.println("从数据管道中消费了一条数据" + data);
            dataChannel.remove(0);

            //如果取出来数据, 发现管道内没数据了, 叫醒生产者进行生产, 别睡了，起来生产数据
            if (dataChannel.size() <= 0) {
                this.notifyAll();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生产方法
     * 加锁是因为ArrayList不是线程安全的, 保证同一时间只有一个线程对ArrayList进行操作
     */
    public synchronized void put () {
        try {
            //数据通道里有数据, 生产者就挂起，不再生产数据
            while (dataChannel.size() > 0) {
                this.wait();
            }

            //没有数据的时候就进行生产
            String data = "" + Math.random() * 100;
            dataChannel.add(data);
            System.out.println("向数据管道中生产了一条数据" + data);

            //生产完, 把数据放入管道后, 管道有数据了, 通知消费者别睡了，有数据了，去取吧
            this.notifyAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

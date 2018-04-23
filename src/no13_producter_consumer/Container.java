package no13_producter_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 容器类, 向容器里放数据, 从容器里取数据
 * Auth: miaopeng
 * Date: 2018-04-23 10:34:40
 */
public class Container {

    //数据管道-采用阻塞队列实现
    private BlockingQueue<String> dataChannel = new LinkedBlockingQueue<>();

    /**
     * 消费方法
     */
    public void get () {
        try {
            //从管道里取出数据
            String data = dataChannel.take();
            System.out.println("从数据管道中消费了一条数据" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生产方法
     */
    public void put () {
        try {
            //生产数据
            String data = "" + Math.random() * 100;
            dataChannel.put(data);
            System.out.println("向数据管道中生产了一条数据" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

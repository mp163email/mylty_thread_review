package no12_producter_consumer_wait_notify;

/**
 * 使用synchronized + wait-notify-notifyAll 的方式实现生产消费
 * Auth: miaopeng
 * Date: 2018-04-23 11:04:43
 */
public class Main {

    public static void main(String[] args) {

        //创建一个容器
        Container container = new Container();

        //创建生产者线程
        Producter producter = new Producter(1, container);
        producter.start();

        //创建消费者线程
        Consumer consumer = new Consumer(1, container);
        consumer.start();

    }
}

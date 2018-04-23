package no13_producter_consumer;

/**
 * 使用阻塞队列（BlockingQueue）方式实现生产消费
 * 与使用synchronized + wait-notify相比, 阻塞队列的方式更为简洁
 * 把synchronized和wait-notify操作封装到了阻塞队列里面.是程序员使用起来更方便
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

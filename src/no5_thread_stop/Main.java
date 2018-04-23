package no5_thread_stop;

import java.util.concurrent.TimeUnit;

/**
 * 1. 执行完run方法的逻辑
 * 2. stop强制退出, 方法已过期
 * 3. 使用volatile控制一个boolean变量来优雅退出
 * 4. 使用interrupter打断退出
 * Auth: miaopeng
 * Date: 2018-04-20 14:50:37
 */
public class Main {

    private static volatile boolean running = true;

    public static void main(String[] args) throws Exception {

        //执行完run方法的逻辑
        new Thread(()-> System.out.println("===执行完run方法的逻辑,线程就会退出===")).start();

        //stop强制退出, 方法已过期
        new Thread(()->{
            int index = 0;
            while (true) {
                index ++;
                if (index >= 1000) {
                    System.out.println("====使用stop方法执行关闭=====");
                    Thread.currentThread().stop();
                }
            }
        }).start();

        //使用volatile控制一个boolean变量来优雅退出
        new Thread(()->{
            while (running) {
            }
            System.out.println("====使用volatile关键字,进行关闭====");
        }).start();

        TimeUnit.SECONDS.sleep(3);

        running = false;

        //使用interrupter打断退出
        Interrupter interrupter = new Interrupter();
        interrupter.start();
        TimeUnit.MILLISECONDS.sleep(100);
        interrupter.interrupt();
    }

    /**
     * 线程打断的方式, 使线程退出
     * 当被打断后, interrupted()方法会立即变成true，随后又立刻会变成false
     */
    private static class Interrupter extends Thread {
        public void run () {
            while (true) {
                if (interrupted()) {
                    System.out.println("====被打断, 线程退出====");
                    System.out.println("打断后的状态==" + interrupted());
                    break;
                }
            }
        }
    }

}

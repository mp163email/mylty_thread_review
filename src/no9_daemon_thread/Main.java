package no9_daemon_thread;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程
 * 与用户线程的区别在于， 没有用户线程的时候, 守护线程会自动停止退出, 哪怕你没做任何的停止退出操作.
 * 因为守护线程随时可能会停止, 所以不能用它来做文件和数据库操作
 * .setDaemon方法要在.start方法之前,否则会报错.
 * Auth: miaopeng
 * Date: 2018-04-23 11:29:58
 */
public class Main {

    /**
     * 守护线程
     */
    private static class DaemonThread extends Thread {
        public void run () {
            try {
                while (true) {
                    System.out.println("I`m a dadmon thread");
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用户线程
     */
    private static class GeneralThread extends Thread {
        public void run () {
            try {
                int index = 0;
                while (true) {
                    index ++;
                    if (index > 5) {
                        break;
                    }
                    System.out.println("I`m a generalThread thread");
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        GeneralThread generalThread = new GeneralThread();
        generalThread.start();

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true);
        daemonThread.start();
    }
}

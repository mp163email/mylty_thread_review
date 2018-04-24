package no22_lock_reentrantreadwritelock;

import java.util.concurrent.TimeUnit;

/**
 * 测试读写锁作用范围
 * 读读锁是不生效的
 * 读写锁生效
 * 写写锁生效
 * Auth: miaopeng
 * Date: 2018-04-24 20:29:07
 */
public class Main {

    /**
     * 读线程
     */
    private static class ReadThread extends Thread {

        private Service service;

        public ReadThread (Service service) {
            this.service = service;
        }

        public void run () {
            service.read();
        }
    }

    /**
     * 写线程
     */
    private static class WriteThread extends Thread {

        private Service service;

        public WriteThread (Service service) {
            this.service = service;
        }

        public void run () {
            service.write();
        }
    }
    public static void main(String[] args) throws Exception {

        Service service = new Service();

        //读读-不受锁的影响
        for (int i = 0; i < 2; i++) {
            ReadThread readThread = new ReadThread(service);
            readThread.start();
        }

        TimeUnit.SECONDS.sleep(10);

        //写写-受锁的影响
        for (int i = 0; i < 2; i++) {
            WriteThread writeThread = new WriteThread(service);
            writeThread.start();
        }

        TimeUnit.SECONDS.sleep(10);

        //读写-受锁的影响
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                ReadThread readThread = new ReadThread(service);
                readThread.start();
            } else if (i == 1) {
                WriteThread writeThread = new WriteThread(service);
                writeThread.start();
            }

        }
    }

}

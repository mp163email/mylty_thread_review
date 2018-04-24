package no25_countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch的使用， 先规定一个数， 然后一直往下减， 减到0， 唤醒后面的执行
 * 模拟火箭点火操作： 火箭点火前先进行10项检查，检查完了以后，才开始点火
 * Auth: miaopeng
 * Date: 2018-04-24 21:12:57
 */
public class Main {

    private static CountDownLatch countDownLatch = new CountDownLatch(10);

    /**
     * 需要检查的任务, 检查操作需要2秒钟的时间
     */
    private static class CheckTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getId() + "， check");
                TimeUnit.SECONDS.sleep(2);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查线程
     * 完成十项检查任务
     */
    private static class CheckThread extends Thread {
        public void run () {
            CheckTask checkTask = new CheckTask();
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 10; i++) {
                executorService.execute(checkTask);
            }
        }
    }

    /**
     * 点火线程
     */
    private static class FireThread extends Thread {
        public void run () {
            try {
                countDownLatch.await();
                System.out.println("开始点火!!!!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        new CheckThread().start();//检查线程需同时做10项检查，每个检查耗时2秒
        new FireThread().start();//点火线程会等到检查线程执行完了以后再执行
    }



}

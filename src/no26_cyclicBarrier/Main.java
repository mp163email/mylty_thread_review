package no26_cyclicBarrier;

import java.util.concurrent.*;

/**
 * 循环栅栏 10个士兵先集合， 集合完毕后输出"集合完毕", 然后10个士兵开始工作, 都工作完毕后输出“工作完毕”
 * 和countdownlatch相比, cyclicBarrier走的是加法， 达到指定个数后清零， 重新来过
 * 用到的方法只有await
 * Auth: miaopeng
 * Date: 2018-04-25 10:58:56
 */
public class Main {

    /**
     * 循环栅栏 数量增长到10， 触发参数2的线程， 然后数量又重置到0
     */
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new CyclicBarrierTriggerThread(1));

    /**
     * 循环栅栏达到指定数量后, 触发的线程, 这里只做下简单输出
     */
    private static class CyclicBarrierTriggerThread extends Thread {

        private int step;

        public CyclicBarrierTriggerThread (int step) {
            this.step = step;
        }

        public void run () {
            if (step == 1) {
                System.out.println("士兵集合完毕");
            } else if (step == 2) {
                System.out.println("士兵工作完毕");
                step = 2;
            }
        }
    }

    /**
     * 士兵
     */
    private static class Soldier implements Runnable {

        private CyclicBarrier cyclicBarrier;

        public Soldier (CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        public void run () {
            try {
                jiHe();
                cyclicBarrier.await();//来一个线程到这， 数量加1，到达指定数量后， 触发指定线程
                work();
                cyclicBarrier.await();

            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void jiHe () {
            try {
                System.out.println("solder：" + Thread.currentThread().getName() + ", 报到！");
                TimeUnit.SECONDS.sleep(1);//模拟过来报到需要1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void work () {
            try {
                System.out.println("solder：" + Thread.currentThread().getName() + ", 开始工作！");
                TimeUnit.SECONDS.sleep(1);//模拟工作需要1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.execute(new Soldier(cyclicBarrier));
        }
    }

}

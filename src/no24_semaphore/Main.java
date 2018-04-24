package no24_semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量, 增加了同时访问资源的线程数
 * 用到两个方法acquire, release
 * 就跟停车场一样,假设停车场有3个空位,门卫能让3辆车同时进入,后来的车都得排队，不让进。停车场里边开走了1辆，门卫就在放进一辆，依次
 * 信号量管理许可，线程从信号量那拿许可，拿一个信号量的许可减一，释放一个，信号量的许可加一
 * 信号量许可数设置成1,可以实现线程间互斥
 * Auth: miaopeng
 * Date: 2018-04-24 20:51:44
 */
public class Main {

    private static Semaphore semaphore = new Semaphore(5);

    /**
     * 模拟停车场，能同时放5辆车
     */
    private static class Task implements Runnable {
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int nThread = 20;
        ExecutorService service = Executors.newFixedThreadPool(nThread);
        Task task = new Task();
        for (int i = 0; i < nThread; i++) {
            service.execute(task);
        }
        service.shutdown();
    }
}

package no14_threadpool_execute;

import java.util.concurrent.*;

/**
 * 线程池
 * 几种常见的线程池
 * Auth: miaopeng
 * Date: 2018-04-24 12:06:17
 */
public class Main {

    /**
     * 固定线程池
     */
    private static ExecutorService fixThreadPool = Executors.newFixedThreadPool(3);

    /**
     * 无限大的线程池
     */
    private static ExecutorService cachThreadPool = Executors.newCachedThreadPool();

    /**
     * 单根线程的线程池
     */
    private static ExecutorService simpleThreadPool = Executors.newSingleThreadExecutor();

    /**
     * 自定义线程池
     */
    private static ExecutorService customThreadPool = new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    public static void main(String[] args) {

        //将定义的线程池组装到数组中
        ExecutorService[] executorServices = new ExecutorService[4];
        executorServices[0] = fixThreadPool;
        executorServices[1] = cachThreadPool;
        executorServices[2] = simpleThreadPool;
        executorServices[3] = customThreadPool;

        //组装20个任务
        BlockingQueue<Runnable> runnableBlockingQueue = new LinkedBlockingQueue<>();
        for (int i = 0; i < 20; i++) {
            final int num = i;
            runnableBlockingQueue.add(()-> System.out.println("runnable-" + num));
        }

        //让4个线程池依次执行这20个任务
        int index = 0;
        while (true) {
           Runnable runnable = runnableBlockingQueue.poll();
           if (runnable != null) {
               executorServices[index].execute(runnable);
               index ++;
               if (index >= executorServices.length) {
                   index = 0;
               }
           } else {
               break;
           }
       }

       //关掉4个线程池
       for (int i = 0; i < executorServices.length; i++) {
            executorServices[i].shutdown();
       }

    }

}

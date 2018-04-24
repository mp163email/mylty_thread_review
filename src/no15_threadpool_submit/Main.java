package no15_threadpool_submit;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池 submit方式提交任务
 * 实现Callable接口 覆盖call方法 返回值Futher<T>
 * Auth: miaopeng
 * Date: 2018-04-24 15:19:30
 */
public class Main {

    /**
     * 自定义一个任务, 实现Callable接口, 覆盖call方法
     * 将此任务的执行时间返回回来
     */
    private static class Task implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < Integer.MAX_VALUE; i++) {

            }
            long endTime = System.currentTimeMillis();
            long costTime = (endTime - startTime);
            System.out.println(Thread.currentThread().getName() + " 耗时-" + costTime);
            return costTime;
        }
    }

    public static void main(String[] args) throws Exception {
        int nThread = 4;
        ExecutorService executorService = Executors.newFixedThreadPool(nThread);
        Task task = new Task();
        int sumTime = 0;
        for (int i = 0; i < nThread; i++) {
            Future<Long> future = executorService.submit(task);
            sumTime += future.get();
        }
        System.out.println("总耗时=" + sumTime);
        executorService.shutdown();
    }

}

package no16_threadpool_custom;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 * Auth: miaopeng
 * Date: 2018-04-24 15:57:50
 */
public class Main {

    private static class Task implements Runnable {

        private String taskName;

        public Task (String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName () {
            return taskName;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "---run---");
        }
    }

    public static void main(String[] args) {

        ExecutorService custThreadPool = new ThreadPoolExecutor(4, 4, 0, TimeUnit.MICROSECONDS, new LinkedBlockingQueue()) {

            /**
             * 用以下两个方法,也可以监控任务的耗时
             * 耗时的计算不放在任务里, 而是放在外层,利用下面那两个方法,对任务进行统一处理
             * 执行前在任务对象上记上一个开始时间，执行后用当前时间减去执行前的那个开始时间
             * 这里只提供思想,不提供实现了
             */

            /**
             * 执行前
             * @param t
             * @param r
             */
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                Task task = (Task) r;
                System.out.println(Thread.currentThread().getName() + "-" + task.getTaskName() + ", before");
                super.beforeExecute(t, r);
            }

            /**
             * 执行后
             * @param r
             * @param t
             */
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                Task task = (Task) r;
                System.out.println(Thread.currentThread().getName() + "-" + task.getTaskName() + ", after");
                super.afterExecute(r, t);
            }

            /**
             * 中断后
             */
            @Override
            protected void terminated() {
                System.out.println("====中断=====");
                super.terminated();
            }
        };

        Task task = new Task("task");
        custThreadPool.execute(task);
        custThreadPool.shutdown();
    }

}

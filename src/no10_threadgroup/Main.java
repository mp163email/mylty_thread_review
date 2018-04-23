package no10_threadgroup;

import java.util.concurrent.TimeUnit;

/**
 * 线程组
 * 线程组下可以放线程也可以放一个线程组
 * Auth: miaopeng
 * Date: 2018-04-23 16:10:16
 */
public class Main {

    //父线程组
    private static ThreadGroup threadGroupFather = new ThreadGroup("fatherGroup");

    //子线程组 子线程组归于父线程组
    private static ThreadGroup threadGroupSon = new ThreadGroup(threadGroupFather, "sonGroup");

    //父线程
    private static class ThreadFather extends Thread {

        public ThreadFather (ThreadGroup threadGroup, String threadName) {
            super(threadGroup, threadName);
        }

        public void run () {
            while (true) {
                if (interrupted()) {
                    System.out.println("中断");
                    break;
                }
                try {
                    System.out.println(Thread.currentThread().getThreadGroup().getName() + "--" +Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    interrupt();//因为Sleep的时候如果调用打断,会直接被异常捕获.捕获后interrupted变成true后会立即再变成false,所以在这里重新打断一下
                }

            }
        }
    }

    //子线程
    private static class ThreadSon extends Thread {

        public ThreadSon (ThreadGroup threadGroup, String threadName) {
            super(threadGroup, threadName);
        }

        public void run () {
            while (true) {
                if (interrupted()) {
                    System.out.println("中断");
                    break;
                }
                try {
                    System.out.println(Thread.currentThread().getThreadGroup().getName() + "--" +Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    interrupt();//因为Sleep的时候如果调用打断,会直接被异常捕获.捕获后interrupted变成true后会立即再变成false,所以在这里重新打断一下
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        //创建三个子线程, 子线程归于子线程组
        for (int i = 0; i < 3; i++) {
            ThreadSon threadSon = new ThreadSon(threadGroupSon, "son-thread" + i);
            threadSon.start();
        }
        System.out.println(threadGroupSon.getName() + "线程组中有活动线程个数： " + threadGroupSon.activeCount());
        System.out.println(threadGroupFather.getName() + "线程组中有活动线程个数： " + threadGroupFather.activeCount());

        //创建三个父线程, 子线程归于父线程组
        for (int i = 0; i < 3; i++) {
            ThreadFather threadFather = new ThreadFather(threadGroupFather, "father-thread" + i);
            threadFather.start();
        }
        System.out.println(threadGroupFather.getName() + "线程组中有活动线程个数： " + threadGroupFather.activeCount());

        //筛选出父线程组下所有的活跃线程, 包含子线程组下的
        Thread[] activeThreads = new Thread[threadGroupFather.activeCount()];
        threadGroupFather.enumerate(activeThreads);
        for (Thread thread : activeThreads) {
            System.out.println("父线程组中的活跃线程名字=" + thread.getName());
        }

        TimeUnit.SECONDS.sleep(5);

        //5秒后停止所有线程
        for (Thread thread : activeThreads) {
            thread.interrupt();
        }
    }

}

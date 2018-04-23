package no11_thread_catch_exception;

/**
 * 线程的异常捕获, 交由一个类处理某个线程所发生的异常
 * 注意：run方法中不可以有try-catch来自己捕获异常，否则setUncaughtExceptionHandler会无效
 * Auth: miaopeng
 * Date: 2018-04-23 17:02:52
 */
public class Main {

    /**
     * 抛出异常的线程
     */
    private static class LogicThread extends  Thread {
        public void run () {
            //报错
            String str = null;
            str.length();
        }
    }

    public static void main(String[] args) {
        LogicThread logicThread = new LogicThread();
        logicThread.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.println("捕获到线程" + t.getName() + "的异常:" + e.getMessage());
            e.printStackTrace();
        });
        logicThread.start();
    }

}

package no4_join;

import java.util.concurrent.TimeUnit;

/**
 * Join方法的使用
 * 先执行.join()方法的.前面线程的逻辑, 执行完了再执行当前线程(主线程)
 * Created by miaopeng on 2018 4-20 020.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(()->{
            try {
                System.out.println("====thread线程执行====");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();
        System.out.println("====主线程执行=====");
    }

}

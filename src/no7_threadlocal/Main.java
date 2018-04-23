package no7_threadlocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadLocal的使用
 * 线程(Thread)的变量
 * Auth: miaopeng
 * Date: 2018-04-21 11:31:36
 */
public class Main {

    /**
     * 定义成static是为了减少new TheradLocal的次数, 有一个就够了
     */
    private static ThreadLocal<List<Integer>> threadLocal = ThreadLocal.withInitial(()-> new ArrayList<>());
    private static ThreadLocal<List<Integer>> threadLoca2 = ThreadLocal.withInitial(()-> new ArrayList<>());

    /**
     * 获取
     */
    private static class ThreadLocalTest extends Thread {
        public void run() {
            List<Integer> integerList = threadLocal.get();
            List<Integer> integerList2 = threadLoca2.get();
            for (int i = 0; i < 10; i++) {
                integerList.add(i);
                integerList2.add(i);
            }
            System.out.println(Thread.currentThread().getName() + "中的threadlocal对应的ArrayList的大小为" + integerList.size());
            System.out.println(Thread.currentThread().getName() + "中的threadloca2对应的ArrayList的大小为" + integerList2.size());
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new ThreadLocalTest().start();
        }
    }

}

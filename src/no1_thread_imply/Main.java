package no1_thread_imply;

/**
 * 线程的几种实现方式
 * 1.实现Runnable接口
 * 2.继承Thread类
 * 3.匿名类
 * 4.静态内部类
 * Created by miaopeng on 2018 4-20 020
 */
public class Main {

    public static void main(String[] args) {

        //实现方式一 实现Runnable
        Thread thread1 = new Thread(new Thread1());
        thread1.start();

        //实现方式二 继承Thread
        Thread thread2 = new Thread2();
        thread2.start();

        //实现方式三 匿名类
        new Thread(()-> System.out.println("实现方式三： 直接匿名类")).start();

        //实现方式四 静态内部类
        Thread4 thread4 = new Thread4();
        thread4.start();

    }

    /**
     * 实现方式四
     * 静态内部类
     */
    private static class Thread4 extends Thread {
        @Override
        public void run() {
            System.out.println("实现方式四： 静态内部类实现");
        }
    }

}


/**
 * 实现方式一
 * 实现Runnable接口
 */
class Thread1 implements Runnable {
    @Override
    public void run() {
        System.out.println("实现方式一： 实现Runnable接口");
    }
}

/**
 * 实现方式二
 * 继承Thread类
 */
class Thread2 extends Thread {
    @Override
    public void run() {
        System.out.println("实现方式二： 继承Thread类");
    }
}
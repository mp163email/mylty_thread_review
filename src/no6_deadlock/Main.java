package no6_deadlock;

/**
 * 死锁
 * 通过jstack 进程号,  Found 1 deadlock. 表示死锁构建成功
 * Auth: miaopeng
 * Date: 2018-04-20 16:01:57
 */
public class Main {

    private static Object lock1 = new Object();

    private static Object lock2 = new Object();

    public static void main(String[] args) throws Exception {

        //线程一
        Thread thread1 = new Thread(()-> {
            synchronized (lock1) {
                try {
                    System.out.println("线程一， lock1");
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("线程一， lock2");
                }
            }
        });
        thread1.start();

        //线程二
        Thread thread2 = new Thread(()-> {
            synchronized (lock2) {
                try {
                    System.out.println("线程二， lock2");
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("线程二， lock1");
                }
            }
        });
        thread2.start();

        Thread.sleep(10 * 1000);

        System.out.println("thread1 state = " + thread1.getState());
        System.out.println("thread2 state = " + thread2.getState());

    }

}

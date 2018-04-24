package no18_lock;

/**
 * ReentrantLock的使用
 * Auth: miaopeng
 * Date: 2018-04-24 17:03:44
 */
public class Main {

    /**
     * 线程A执行service的methodA
     */
    private static class MyThreadA extends Thread {

        private Service service;

        public MyThreadA (Service service) {
            this.service = service;
        }

        public void run () {
            service.methodA();
        }
    }

    /**
     * 线程执行serviceB的methodB
     */
    private static class MyThreadB extends Thread {

        private Service service;

        public MyThreadB (Service service) {
            this.service = service;
        }

        public void run () {
            service.methodB();
        }
    }

    public static void main(String[] args) {
        Service service = new Service();
        MyThreadA threadA = new MyThreadA(service);
        threadA.start();

        MyThreadB threadB = new MyThreadB(service);
        threadB.start();

    }
}

package no28_longadder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder比AtomicLong有更优秀的性能
 * AtomicLong基于CAS，无线循环更新失败的值，一直到更新成功。
 * LongAdder则在更新失败的时候，将值放到一个Cell数组里，分段更新，最后求和的时候，再加进来。
 * LongAdder在CAS成功的时候不会进入Cell，和AtomicLong一样，高并发情况下，CAS失败，才会触发Cell
 * 通过比较， LongAdder的性能在多线程情况下，性能明显高于AtomicLong. LongAdder完全可以替换掉AtomicLong
 * Auth: miaopeng
 * Date: 2018-05-04 10:09:31
 */
public class Main {

    private static AtomicLong atomicLong = new AtomicLong(0);

    private static LongAdder longAdder = new LongAdder();

    private static long costTime = 0;


    /**
     * pool-1-thread-7, sum=1266428619, longadder end -- 6998
     pool-1-thread-5, sum=1282384094, longadder end -- 7096
     pool-1-thread-8, sum=1282505893, longadder end -- 7097
     pool-1-thread-6, sum=1282505893, longadder end -- 7097
     pool-1-thread-1, sum=1867830103, longadder end -- 9725
     pool-1-thread-10, sum=1909419129, longadder end -- 9940
     pool-1-thread-9, sum=1985410295, longadder end -- 10303
     pool-1-thread-4, sum=2138714622, longadder end -- 11056
     pool-1-thread-3, sum=2147474877, longadder end -- 11115
     pool-1-thread-2, sum=2147483650, longadder end -- 11116
     */
    private static class LongAdderRunnable implements Runnable {
        public void run () {
            int index = 0;
            while (true) {
                index ++;
                longAdder.increment();
                if (index > Integer.MAX_VALUE / 10) {
                    System.out.println(Thread.currentThread().getName() + ", sum=" + longAdder.sum() +  ", longadder end -- "  + (System.currentTimeMillis() - costTime));
                    break;
                }
            }
        }
    }

    /**
     * pool-1-thread-8, sum=1531956250, atomic end -- 34937
     pool-1-thread-6, sum=1730227297, atomic end -- 39597
     pool-1-thread-3, sum=1755842127, atomic end -- 40191
     pool-1-thread-7, sum=1877905727, atomic end -- 43058
     pool-1-thread-4, sum=1987457034, atomic end -- 45644
     pool-1-thread-10, sum=2110014657, atomic end -- 48542
     pool-1-thread-5, sum=2112812977, atomic end -- 48608
     pool-1-thread-1, sum=2117262220, atomic end -- 48713
     pool-1-thread-2, sum=2145060488, atomic end -- 49008
     pool-1-thread-9, sum=2147483650, atomic end -- 49026
     */
    private static class AtomicRunnable implements Runnable {
        public void run () {
            long index = 0;
            while (true) {
                index++;
                atomicLong.incrementAndGet();
                if (index > (Integer.MAX_VALUE / 10)) {
                    System.out.println(Thread.currentThread().getName() + ", sum=" + atomicLong.get() + ", atomic end -- "  + (System.currentTimeMillis() - costTime));
                    break;
                }
            }
        }
    }


    public static void main(String[] args) {
        costTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
//            executorService.execute(new AtomicRunnable());
            executorService.execute(new LongAdderRunnable());
        }
        executorService.shutdown();
    }
}

package no27_forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 计算任务
 * 计算start-end 的每个数的总和
 * Auth: miaopeng
 * Date: 2018-04-25 16:03:48
 */
public class CalcTask extends RecursiveTask<Long> {

    private long start;

    private long end;

    public CalcTask (long start, long end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 计算
     * @return
     */
    @Override
    protected Long compute() {
        long sum = 0;
        for (long i = start; i <= end; i++) {
            sum += i;
        }
        System.out.println(Thread.currentThread().getName() + "start = " + start + ", end=" + end + " calc sum = " + sum);
        return sum;
    }
}

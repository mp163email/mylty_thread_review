package no27_forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 分段，分线程，求0-n的每个数的和
 * Auth: miaopeng
 * Date: 2018-04-25 16:23:56
 */
public class Main {

    /**
     * 分段计算 start-end之间每位数的和
     */
    private static class ForkJoinCalc extends RecursiveTask<Long> {

        private long start;

        private long end;

        public ForkJoinCalc (long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {

            long step = (end - start) / 10; //分成几个段

            long pos = start; //初始第一次的第一个数

            List<CalcTask> taskList = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }
                if (pos > end) {
                    continue;
                }
                CalcTask calcTask = new CalcTask(pos, lastOne);
                calcTask.fork();
                taskList.add(calcTask);
                pos = pos + step + 1;
            }

            //跟线程池的submit机制很像
            long sum = 0;
            for (CalcTask calcTask : taskList) {
                sum += calcTask.join();//线程池futher.get()方法很像
            }
            return sum;
        }
    }

    public static void main(String[] args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinCalc forkJoinCalc = new ForkJoinCalc(0, 100);//这个对象只负责分派任务,跟等待结果,不负责分派后的计算
        ForkJoinTask<Long> forkJoinTask = forkJoinPool.submit(forkJoinCalc);
        long sum = forkJoinTask.get();
        System.out.println(sum);
    }
}

package no100_operate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * tick里模拟秒数
 * 模拟超时后设置成自动
 * 3秒后进入自动判断，10秒后没有自动的强制设置成自动
 * 模拟棋牌室超时判断
 * 道无边战斗操作
 * Auth: miaopeng
 * Date: 2018-05-05 14:48:39
 */
public class Main {

    //存放操作
    private static List<Integer> operateList = new ArrayList<>();

    //总操作数
    private static int sumOperateNum = 5;

    //3秒的时候进入自动
    private static int aotuDelay = 3;

    //10秒的时候超时，强制设置成自动
    private static int outOfTime = 10;

    /**
     * 在tick里实现每秒倒计时
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        int lastTickTime = 0;
        int roundTime = outOfTime;
        boolean isExecAuto = false;

        while (true) {
            if (roundTime <= 0) {
                break;
            }
            TimeUnit.MILLISECONDS.sleep(100);
            int currSeond = (int)(System.currentTimeMillis() / 1000);
            if (lastTickTime == 0) {
                lastTickTime = currSeond;
            }
            if (currSeond > lastTickTime) {
                int interval = currSeond - lastTickTime;
                roundTime -= interval;
                lastTickTime = currSeond;
                System.out.println("=====" + roundTime);
                if (roundTime < outOfTime - 3 && !isExecAuto) {
                    isExecAuto = true;
                    System.out.println("进入到自动操作");
                    handlerAuto();
                }
                if (roundTime <= 0) {
                    roundTime = 0;
                    System.out.println("进入到超时操作，强制设置成自动");
                    handlerOutOfTime();
                }
            }
        }
    }

    /**
     * 处理设置成自动的单元
     * 模拟1和2两个单元是自动的
     */
    private static void handlerAuto () {
        int [] autoUnit = {1, 2};
        for (int i : autoUnit) {
            addOperate(i);
        }
    }

    /**
     * 处理超时的操作
     * 模拟3,4,5三个是超时后强制设置的自动
     */
    private static void handlerOutOfTime () {
        int [] outOfTimeUnit = {3, 4, 5};
        for (int i : outOfTimeUnit) {
            addOperate(i);
        }
    }

    /**
     * 向操作列表中插入操作完成的单元
     */
    private static void addOperate (int operate) {
        operateList.add(operate);
        if (operateList.size() == sumOperateNum) {
            afterOperate();
        }
    }

    /**
     * 收到所有的操作后，做的事情
     */
    private static void afterOperate () {
        System.out.println("我已经收集全所有的操作，进入下面的逻辑......");
    }

}

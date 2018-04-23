package no7_threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * HashCode算法
 * 2的n次方容量，通过这种算法,能产生0-容量的不重复下标
 * Auth: miaopeng
 * Date: 2018-04-21 17:10:37
 */
public class ThreadLocalHashCodeTestMain {
    private static AtomicInteger nextHashCode =  new AtomicInteger();

    private static final int HASH_INCREMENT = 0x61c88647;

    public static void main(String[] args) {
        int length = (int) Math.pow(2, 4);
        for (int i = 0; i < length * 2; i++) {//换成leagth是对的，不重复。 换成length * 2 重复了一遍
            int n = nextHashCode.getAndAdd(HASH_INCREMENT) & (length - 1);
            System.out.println(n);
        }
    }
}

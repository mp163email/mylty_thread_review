package no7_threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * new一个带有静态方法的类, 看静态方法值的输出
 * Auth: miaopeng
 * Date: 2018-04-21 17:05:39
 */
public class StaticTest {

    private static AtomicInteger nextHashCode =  new AtomicInteger();

    private static final int HASH_INCREMENT = 1;

    private final int threadLocalHashCode = nextHashCode();

    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    public int getThreadLocalHashCode () {
        return threadLocalHashCode;
    }
}

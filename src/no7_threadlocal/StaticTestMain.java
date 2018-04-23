package no7_threadlocal;

/**
 * Auth: miaopeng
 * Date: 2018-04-21 17:06:36
 */
public class StaticTestMain {
    public static void main(String[] args) {
        StaticTest test1 = new StaticTest();//new的时候值变化， 并把变化后的值赋值到new的这个对象的属性上，再调用这个属性，值不会变化
        System.out.println(test1.getThreadLocalHashCode());//值已经赋值到对象的属性上了,所以不会变化
        System.out.println(test1.getThreadLocalHashCode());
        System.out.println(test1.getThreadLocalHashCode());
        StaticTest test2 = new StaticTest();
        System.out.println(test2.getThreadLocalHashCode());
        System.out.println(test2.getThreadLocalHashCode());
        System.out.println(test2.getThreadLocalHashCode());
    }
}

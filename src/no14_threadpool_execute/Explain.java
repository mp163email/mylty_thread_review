package no14_threadpool_execute;

/**
 * 线程池解释
 * Auth: miaopeng
 * Date: 2018-04-24 10:53:35
 */
public class Explain {

    /**
     * 使用哪种线程池,要考虑两个因素，一个是机器的线程资源是否紧张,一个是任务量和单个任务的执行时间
     *
     * a.如果线程资源紧张（cpu，mem),要用固定线程数的线程池newFiexedThreadPool
     * b.如果线程资源不紧张,且任务提交频繁,单个任务执行时间短,就用newCachedThreadPool,因为对线程处理任务效率会高
     *
     * 1.newFixedThreadPool() 返回一个corePoolSize和maximumPoolSize相等的，所以keepAliveTime参数没有意义。
     *  使用无界的任务队列LinkedBlockingQueue的线程池
     *  无界队列里存放无法立刻执行的任务,当任务提交非常频繁并且处理能力超过那几个固定线程的的时候,该队列会迅速膨胀,从而耗尽内存资源
     *  由于无界，所以不执行拒绝策略
     *
     * 2.newCachedThreadPool()它是一个可以无限扩大的线程池。返回的是一个corePoolSize为0, maximumPoolSize无穷大，意味着线程数量可以无限大
     * 使用直接提交队列synchronousQueue的线程池,keepAliveTime为60S，意味着线程空闲时间超过60S就会被杀死
     * 采用SynchronousQueue装等待的任务，这个阻塞队列没有存储空间，这意味着只要有请求到来，就必须要找到一条工作线程处理他，如果当前池中没有空闲的线程，那么就会再创建一条新的线程。
     * 如果有大量任务被提交,而任务的执行耗时比较长，系统会创建大量的线程, 这样很快就会耗尽系统资源
     * 当处理执行时间比较小时,可以使用newCacheThreadPool线程池
     *
     * 除了以上
     * 1.无界任务队列：LinkedBlockingQueue，达到最大线程数量后,不会再创建新线程,未执行完的任务都会放到这个无界队列里
     * 2.直接提交队列：synchronousQueue,可以设置maximumPoolSize最大值,但一般要设置比较大，当线程达到最大值是会执行拒绝策略,内部使用了无锁
     * 3.有界任务队列：ArrayBlockingQueue，因为有界,所以构造函数会带一个容量参数,指定界限。有界队列在未满之前会优先使用corePoolSize线程数来解决问题,
     *   如果满了，则会创建线程到maximumPoolSize, 线程数。如果这个时候队列又满了,则执行拒绝策略
     * 4.优先任务队列：PriorityBlockingQueue可以设置任务的优先级,不用先进先出
     *
     * 队列满了有以下几种拒绝策略,也可以自定义
     * 1.AbortPolicy:直接抛出异常
     * 2.CallerRunsPolicy：有点没明白,反正就是不会丢任务,但是性能会急剧下降
     * 3.DiscardOledestPolicy:丢弃最老的一个任务,也就是排在出口的任务
     * 4.DiscardPolicy:丢弃多个任务,如果任务允许丢弃,这个是最好的
     */

}

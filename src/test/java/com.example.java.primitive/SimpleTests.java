package com.example.java.primitive;

import com.example.interfaced.Sampled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class SimpleTests {

    /**
     *
     *  == 和 equals的区别 ..
     *
     *  == :
     *  支持基础类型值校验( 不需要 类型相同), 支持引用校验(判断引用指向的地址是否一样 ..) ...
     *  但是不支持包装类型之间的 引用地址比较 ...
     *
     * 以下等式是相同的,因为数字常量池缓存
     *
     * 所以 a 指向的 本质上是 b 指向的相同 的缓存的全局常量 1...
     *
     */
    @Test
    public void doubleEqualsTests() {
        short a = 1;
        int b = 1;
        System.out.println( a == b);


        Object c = 1;
        Integer d = 1;

        System.out.println(c == d);

        // 两个相同类型的不同变量指向地址比较
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(1);

        System.out.println(i2 == i1);
    }

    /**
     * 本质上它,在Object中的实现等价于 ==
     * 主要就是想要判断对象的值是否相同(内容是否相同) ...
     *
     * 那么 在实际中,可能需要覆盖去实现自定义的对象内容值校验...
     */
    @Test
    public void equalsTests() {
        //pass
    }


    /**
     * 本质上它,在Object中的实现等价于 == 主要就是想要判断对象的值是否相同(内容是否相同) ...
     * 那么 在实际中,可能需要覆盖去实现自定义的对象内容值校验...
     */
    @Test
    public void reflectionTests() {

    }

    /**
     * 数据分为基本数据类型和引用数据类型。基本数据类型：数据直接存储在栈中；引用数据类型：存储在栈中的是对象的引用地址，真实的对象数据存放在堆内存里。
     *
     * 浅拷贝：对于基础数据类型：直接复制数据值；对于引用数据类型：只是复制了对象的引用地址，新旧对象指向同一个内存地址，修改其中一个对象的值，另一个对象的值随之改变。
     *
     * 深拷贝：对于基础数据类型：直接复制数据值；对于引用数据类型：开辟新的内存空间，在新的内存空间里复制一个一模一样的对象，新老对象不共享内存，修改其中一个对象的值，不会影响另一个对象。
     *
     * 深拷贝相比于浅拷贝速度较慢并且花销较大。
     */
    @Test
    public void copyComparison() {

    }

    /**
     * 构造器不能够被重写,但是可以重载 ..
     * 重写是在方法参数签名不变的情况下,修改方法返回值类型向下兼容的形式 ...
     * 重载是相同方法名称连同不同的方法参数签名形成的方法的形式 ...
     *
     * 方法的重载和重写都是实现多态的方式，区别在于前者实现的是编译时的多态性，而后者实现的是运行时的多态性。
     *
     * 重载：一个类中有多个同名的方法，但是具有有不同的参数列表（参数类型不同、参数个数不同或者二者都不同）。
     *
     * 重写：发生在子类与父类之间，子类对父类的方法进行重写，参数都不能改变，返回值类型可以不相同，但是必须是父类返回值的派生类。
     * 即外壳不变，核心重写！重写的好处在于子类可以根据需要，定义特定于自己的行为。
     */
    @Test
    public void rewriteAndReLoad() {
        // pass
    }

    /**
     * java中只有值传递,
     * 当你传递一个对象参数到方法中，并修改这个对象的属性并返回它 ..
     * 那么这里返回的对象 本身也需要一个对象引用进行地址指向 ..
     * 那么本质上这个对象引用就是一个值传递(因为开辟了新的栈空间 - 它的内容就是对象地址.) ..
     *
     */
    @Test
    public void valuePassing() {
        Map<String,Object> value = new HashMap<>();
        value.put("1","1");
        Object value1 = value(value);
        System.out.println(value == value1);

    }

    private Object value(Map<String,Object> source) {
        source.put("1","2");
        return source;
    }

    /**
     * 静态方法中是否能够调用非静态方法 ...
     *
     * 答案是区分两种情况,一种是调用产生实例的对象方法,如果没有创建实例,则无法调用 ...
     *
     * // 例如一般通过静态帮助方法 构建一个类的实例  ..
     *
     * 有关静态初始化和清理 示例详解:
     * https://blog.csdn.net/v123411739/article/details/79600228
     *
     * 本质上就是静态代码块先执行,然后构造代码块先执行,最后执行构造方法 ..
     * 先执行父类,后执行子类 ...
     */
    @Test
    public void staticMethodInvoke() {

    }

    /**
     * 为什么不能根据返回类型来区分重载..
     * 由于java 支持类型协变,也就是java多态性 ..
     *
     * 通过返回值来决定调用的是哪一个方法 完全不合理 ...
     * 因为在协变的情况下,哪一种方法调用都合适 ...
     *
     * c语言中返回值作为 方法调用的判断规则之一,是因为它不存在多态处理 ...
     *
     * 方法的返回值只是作为方法运行之后的一个“状态”，但是并不是所有调用都关注返回值，所以不能将返回值作为重载的唯一区分条件。
     */
    @Test
    public void variantForReturnTypeNotUseMethodInvokeDistinguish() {

    }


    /**
     * 接口规范
     * 抽象类中可以包含非抽象的方法，在 Java 7 之前接口中的所有方法都是抽象的，
     * 在 Java 8 之后，接口支持非抽象方法：default 方法、静态方法等。
     * Java 9 支持私有方法、私有静态方法。
     */
    @Test
    public void interfaceTests() {
        Sampled sampled = new Sampled() {

        };
    }

    /**
     * 来源不同：sleep() 来自 Thread 类，wait() 来自 Object 类。
     *
     * 对于同步锁的影响不同：sleep() 不会代表同步锁的行为，
     * 如果当前线程持有同步锁，那么 sleep 是不会让线程释放同步锁的。
     * wait() 会释放同步锁，让其他线程进入 synchronized 代码块执行。
     *
     * 使用范围不同：sleep() 可以在任何地方使用。wait() 只能在同步控制方法或者同步控制块里面使用，否则会抛 IllegalMonitorStateException。
     *
     * 恢复方式不同：两者会暂停当前线程，但是在恢复上不太一样。sleep() 在时间到了之后会重新恢复；
     * wait() 则需要其他线程调用同一对象的 notify()/nofityAll() 才能重新恢复。
     * @throws InterruptedException
     */
    @Test
    public void sleepWithObjectWait() throws InterruptedException {
        final String object=  "value";
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // 不允许打断 ..
            }
            synchronized (object) {
                System.out.println("线程2执行了 ...");
            }
        }).start();


        new Thread(() -> {

            synchronized (object) {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                    // 打断失败,不允许打断 ..
                }

                System.out.println("线程1执行了 ..");
            }
        }).start();


        Thread.sleep(10000);
    }

    /**
     * 线程的 sleep() 方法和 yield() 方法有什么区别？
     *
     * 线程执行 sleep() 方法后进入超时等待（TIMED_WAITING）状态，而执行 yield() 方法后进入就绪（READY）状态。
     *
     * sleep() 方法给其他线程运行机会时不考虑线程的优先级，因此会给低优先级的线程运行的机会；
     * yield() 方法只会给相同优先级或更高优先级的线程以运行的机会。
     *
     *
     * 线程几大状态
     *                              运行态
     *      就绪                                              阻塞态(超时等待,io阻塞)
     *                              死亡
     */
    @Test
    public void sleepWithYield() {

    }

    /**
     * 2）Lock 在发生异常时，如果没有主动通过 unLock() 去释放锁，很可能会造成死锁现象，因此使用 Lock 时需要在 finally 块中释放锁
     * ；synchronized 不需要手动获取锁和释放锁，在发生异常时，会自动释放锁，因此不会导致死锁现象发生；
     *
     * 3）Lock 的使用更加灵活，可以有响应中断、有超时时间等；而 synchronized 却不行，使用 synchronized 时，
     * 等待的线程会一直等待下去，直到获取到锁；
     *
     * 4）在性能上，随着近些年 synchronized 的不断优化，Lock 和 synchronized 在性能上已经没有很明显的差距了，
     * 所以性能不应该成为我们选择两者的主要原因。
     * 官方推荐尽量使用 synchronized，除非 synchronized 无法满足需求时，则可以使用 Lock。
     */
    @Test
    public void synchronizedWithLock() {

    }

    /**
     * 死锁必要条件
     * 1. 互斥性
     *      一个资源在同一时间内仅有一个进程能够获取,如果已经被获取,其他的进程请求,那么必须等待 ..
     * 2. 请求和保持条件
     *      多个资源情况下,资源在请求成功之后,在用完之前保持排他性,并在结束之后进行释放,但是得到了一部分资源之后,尝试获取其他资源,
     *      而满足互斥性的资源被其他进程获取,那么就会陷入等待 ...
     * 3. 不可剥夺条件
     *      请求的资源在未使用完毕之前,具有排他性,只能由所有者释放 ..
     * 4. 环路等待条件 ...
     *      等待的资源形成了一条环路等待 ..
     *      pi的需要的资源(形成了 pi+1  -> , ->  pi+2, ->  pi+3, ->  pi+4)
     *
     *
     *  预防死锁:
     *      打破任意条件即可:
     *      1. 打破互斥条件,取消互斥,但是一般互斥条件是无法破坏的 .. 所以这个不用考虑
     *      2. 打破请求和保持条件:
     *          1. 采用资源预先分配的策略,既线程运行前必须申请全部的资源,满足则运行,否则就陷入等待状态 ..
     *          2. 每个进程提出新的资源申请前,需要释放掉已经占有的资源 ..
     *      3. 打破不可剥夺条件:
     *          例如,当进程占有某些资源之后,进一步申请其他资源而无法满足,则进程必须释放掉它之前获取的资源 ..
     *          也就是说,跳出申请资源的等待的陷阱,并恢复资源公有性 .
     *      4. 打破环路等待条件
     *          很简单,资源进行统一编号，然后进程进行序号递增的方式申请资源 ..
     */
    @Test
    public void deadLock() {

    }

    /**
     * 使用线程池的好处:
     * 1. new 线程非常消耗系统资源,降低系统稳定性 ..
     *
     * 那么使用线程池的好处是:
     * 1. 降低资源消耗 - 利用重复的线程(降低线程创建和销毁的消耗)
     * 2. 提高响应速度 - 因为线程已经是就绪态 ..(不需要创建并等待从用户态进入内核态)
     * 3. 增加线程的可管控性 ,线程是奢侈资源,通过线程池统一分配并实现调优和监控..
     */
    @Test
    public void threadPool() {

    }

    /**
     * 线程的核心属性有那些??
     * 1. threadFactory 用于创建工作线程的工厂 ..
     * 2. corePoolSize 核心线程数 - 少于核心池数量时,则创建新的来处理,即使其他工作线程处于空闲状态 ..
     * 3. workQueue(队列) 用于保留任务并移交给工作线程的阻塞队列 ..
     * 4. maximumPoolSize 最大线程数: 线程池允许开启的最大线程数
     *
     * 5. handler(拒绝策略) : 让线程池中添加任务时,将在下面的两种情况下触发拒绝策略 ..
     *      1. 线程池状态不是running 2. 线程池已经达到线程最大数并且阻塞队列已经满了 ...
     * 6. keepAliveTime 保持存活时间: 如果线程池当前线程数量超过 corePoolSize,则多余的线程空闲时间超过给定的keepAliveTime将会被终止 ..
     *
     * 简单说一下线程池的工作原理 ..
     * https://img-blog.csdnimg.cn/20200608092639652.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3YxMjM0MTE3Mzk=,size_16,color_FFFFFF,t_70
     *
     * 线程池有那些拒绝策略:
     *  1. AbortPolicy: 中止策略: 默认的拒绝策略,直接抛出RejectedExecutionException异常,可以捕获这个异常并编写自己的处理代码
     *
     *  2. DiscardPolicy 抛弃策略,什么都不做,直接抛弃被拒绝的任务
     *
     *  3. DiscardOldestPolicy 抛弃最老策略,抛弃阻塞队列中的最老的任务,相当于就是队列中下一个将要被执行的任务,然后重写提交被拒绝的任务 ..
     *  如果阻塞队列是一个优先级队列,那么抛弃最旧的策略将导致抛弃优先级最高的任务,因此最好不要将该策略和优先级队列一起使用 ...
     *
     *  4. callerRunsPolicy: 调用者运行策略,直接在调用者策略上进行执行 .. 是一种调节机制,不会抛弃任务也不会抛出异常,而是将任务回退到调用者 ..
     *  (调用线程池执行任务的主线程),由于执行任务需要一段时间,所以主线程在一段时间内无法提交任务,从而使得线程池有时间来处理完正在执行的任务 ..
     */
    @Test
    public void threadCoreParameters() {


    }

}


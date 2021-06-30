package pers.qxllb.common.test.design.Singleton;

/**
 * 饿汉式是只要Hungry类被装载了，那么instance就会被实例化（没有达到lazy loading效果）
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/21 9:20
 */
public class Hungry {

    /**
     * 通过JVM类加载和类初始化完成，保证了多线程安全；
     * JVM 会保证只有一个线程能执行该类的clinit方法，其它线程将会被阻塞等待
     */
    public static Hungry hungry = new Hungry();

    private Hungry(){}

    public static Hungry getInstance(){
        return hungry;
    }
}

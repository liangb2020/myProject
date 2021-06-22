package pers.qxllb.common.test.design.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * 懒汉模式 + synchronized同步锁 + double-check +volatile(可以保证线程间变量的可见性)
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/21 9:38
 */
public class Fully3Synchronized {

    /**
     * 类初始化时，不做对象实例化
     */
    private volatile static Fully3Synchronized fully1=null;

    public List list = null;//list属性

    //构造函数
    private Fully3Synchronized(){
        list = new ArrayList();
    }

    /**
     * 通过该函数向整个系统提供实例
     * @return
     */
    private static Fully3Synchronized getInstance(){
        /**
         * 对象实例化是非线程安全的，需要应用自身控制
         */
        if(fully1 == null){
            //第一次判断，当instance为null时，则实例化对象，否则直接返回对象
            synchronized(Fully3Synchronized.class) {
                //Synchronized 只能保证可见性、原子性，无法保证执行的顺序。
                if(null == fully1) {
                    //第二次判断
                    /**
                     * //实例化对象的顺序,Happens-Before 规则和重排序导致第3步比第二步先执行
                     * 1.给 Fully3Synchronized 分配内存；
                     * 2.调用 Fully3Synchronized 的构造函数来初始化成员变量
                     * 3.将 Fully3Synchronized 对象指向分配的内存空间（执行完这步 fully1 就为非 null 了）
                     */
                    fully1 = new Fully3Synchronized();  //第三步static分配内存后，对象不为空了，但构造函数的成元变量还没赋值，则刚好有另一个线程到了第一次判断，这个时候判断为非 null，并返回对象使用，这个时候实际没有完成其它属性的构造，因此使用这个属性就很可能会导致异常
                }
            }
        }
        return fully1;
    }

}

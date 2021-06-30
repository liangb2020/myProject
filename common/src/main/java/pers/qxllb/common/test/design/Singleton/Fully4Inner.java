package pers.qxllb.common.test.design.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * 懒汉模式 内部类实现
 * 利用了classloder的机制来保证初始化instance时只有一个线程
 * Fully4Inner类被装载了，instance不一定被初始化。
 * 因为InnerFully4Inner类没有被主动使用，只有显示通过调用getInstance方法时，才会显示装载InnerFully4Inner类，从而实例化instance
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/21 9:38
 */
public class Fully4Inner {

    public List list = null;//list属性

    //构造函数
    private Fully4Inner(){
        list = new ArrayList();
    }

    /**
     * 内部类实现，JVM线程安全
     *
     */
    public static class InnerFully4Inner{
        private static Fully4Inner instance = new Fully4Inner(); //自行创建实例
    }

    /**
     *
     * @return
     */
    public static Fully4Inner getInstance(){
        return InnerFully4Inner.instance; // 返回内部类中的静态变量
    }

}

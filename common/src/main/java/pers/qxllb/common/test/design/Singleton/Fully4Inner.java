package pers.qxllb.common.test.design.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * 懒汉模式 内部类实现
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
     * 内部类实现
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

package pers.qxllb.common.test.design.Singleton;

/**
 * 懒汉模式  非线程安全
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/21 9:38
 */
public class Fully1 {

    /**
     * 类初始化时，不做对象实例化
     */
    private static Fully1 fully1=null;

    private Fully1(){}

    /**
     * 通过该函数向整个系统提供实例
     * @return
     */
    private static Fully1 getInstance(){
        /**
         * 对象实例化是非线程安全的，需要应用控制
         */
        if(fully1 == null){
            fully1 =  new Fully1();
        }
        return fully1;
    }

}

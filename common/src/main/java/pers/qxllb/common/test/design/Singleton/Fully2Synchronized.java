package pers.qxllb.common.test.design.Singleton;

/**
 * 懒汉模式 + synchronized同步锁 + double-check
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/21 9:38
 */
public class Fully2Synchronized {

    /**
     * 类初始化时，不做对象实例化
     */
    private static Fully2Synchronized fully1=null;

    private Fully2Synchronized(){}

    /**
     * 通过该函数向整个系统提供实例
     * @return
     */
    private static Fully2Synchronized getInstance(){
        /**
         * 对象实例化是非线程安全的，需要应用自身控制
         */
        if(fully1 == null){
            //第一次判断，当instance为null时，则实例化对象，否则直接返回对象
            synchronized(Fully2Synchronized.class) {
                if(null == fully1) {
                    //第二次判断
                    fully1 = new Fully2Synchronized();  //实例化对象
                }
            }
        }
        return fully1;
    }

}

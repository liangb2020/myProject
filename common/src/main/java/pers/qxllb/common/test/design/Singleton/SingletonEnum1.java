package pers.qxllb.common.test.design.Singleton;

/**
 * 类描述: 改造已有的类为单例
 *
 * @author liangb
 * @date 2021/6/27  9:24
 */
public class SingletonEnum1 {

    private SingletonEnum1(){}

    public static SingletonEnum1 getInstace(){
        /**
         * 外部类可以访问内部类实例的成员和方法
         */
        return InnerEnum.SINGLETON.getInstance();
        //return InnerEnum.SINGLETON.instance;
    }

    /**
     * 内部枚举类实现单例懒加载
     *  内部类可以访问外部类
     */
    public enum InnerEnum{

        //仅定义一个实例枚举，不能多个,否则会实例化多个
        SINGLETON;

        private  SingletonEnum1 instance=null;

        // JVM保证这个方法只调用一次
        InnerEnum(){
            instance = new SingletonEnum1();
        }

        public SingletonEnum1 getInstance(){
            return instance;
        }
    }
}

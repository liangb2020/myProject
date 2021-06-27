package pers.qxllb.common.test.design.Singleton;

/**
 * 类描述:最简单的单例枚举实现方式
 *
 * @author liangb
 * @date 2021/6/27  9:20
 */
public enum SingletonEnum {

    instance;

    public void businessMethod() {
        System.out.println("我是一个单例！");
    }

    public static void main(String[] args){
        System.out.println(SingletonEnum.instance.ordinal());
    }

}

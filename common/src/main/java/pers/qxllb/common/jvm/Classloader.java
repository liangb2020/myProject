package pers.qxllb.common.jvm;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/15  1:00
 */
public class Classloader {
    public static void main(String[] args) {

        //打印常量
        System.out.println(Son.strFatherFinal);

        //打印类变量
        System.out.println(Son.strFatherStatic);
    }
}

class YeYe{
    static {
        System.out.println("YeYe静态代码块");
    }
}

class Father extends YeYe{
    public final static String strFatherFinal="HelloJVM_Father_final";
    public static String strFatherStatic="HelloJVM_Father_static";

    static{
        System.out.println("Father静态代码块");
    }
}

class Son extends Father{
    public static String strSon="HelloJVM_Son_static";

    static{
        System.out.println("Son静态代码块");
    }
}

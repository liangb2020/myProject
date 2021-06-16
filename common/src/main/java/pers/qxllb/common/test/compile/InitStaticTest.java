package pers.qxllb.common.test.compile;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/16 9:31
 */
public class InitStaticTest {

    public static int i=10;
    static{
        System.out.println("InitStaticTest init(),i:"+i);
    }

    /**
     * Java虚拟机启动时被标明为启动类的类（ JavaTest ），还有就是Main方法的类会首先被初始化
     * @param args
     */
    public static void main(String[] args) {

        /**
         * 用到了类常量，在类连接的第二步即完成（验证和准备），不会触发类初始化
         */
        System.out.println(Son.strFatherFinal+"\n");

        /**
         * 用到Son父类Father的类变量，但是Son类没涉及使用，就不会初始化
         * 1.触发Father类初始化
         * 2.初始化父类Father时，先对起父类YeYe进行初始化
         * 3.完成后再进行Father的初始化
         */
        System.out.println(Son.strFatherStatic+"\n");

        /**
         * 用到Son类变量，再初始化类<clinit>() {包括静态变量赋值语句、静态代码块、静态方法}，不用时只完成连接的前两步（验证和准备（分配内存及类型初始化））
         * 1.使用Son类变量，要对父类验证是否完成初始化；
         * 2.父类已完成初始化，则不在执行；
         */
        System.out.println(Son.strSonStatic+"\n");

        /**
         * 针对常量在编译期没确定的，可以在类初始化阶段赋值常量
         */
        System.out.println(Test.str+"\n");
    }
}

class Test{
    static {
        System.out.println("Test static 静态代码块");
    }

    public static final double str=Math.random();  //编译期不确定,在类初始化期间确定常量值
}

class YeYe{
    static {
        System.out.println("YeYe static{}静态代码块");
    }
}

class Father extends YeYe{
    /**
     * 连接->准备阶段：即完成常量赋值
     */
    public final static String strFatherFinal="HelloJVM_Father_Final";

    /**
     * 连接->准备阶段：完成内存分配和JAVA类型初始化值
     * 初始化阶段：完成static变量的初始化赋值
     */
    public static String strFatherStatic="HelloJVM_Father_Static";

    static{
        System.out.println("Father static{}静态代码块");
    }
}

class Son extends Father{
    public static String strSonStatic="HelloJVM_Son_Static";

    static{
        System.out.println("Son static{}静态代码块");
    }
}
package pers.qxllb.common.test.inner;

/**
 * 普通内部类
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/21 20:09
 */
public class Outer {

    /**
     * 私有成员需要给内部类访问，外部类编译时，外部类提供的静态方法，接受一个外部类对象作为参数
     */
    private int outi=0; //外部类的私有成员

    /**
     * 外部类的方法
     */
    public void outPrint(){
        System.out.println("outPrint()");
    }

    /**
     * 1.成员内部类不能含有 static 的变量和方法
     * 2.成员内部类可以访问外部类的所有成员和方法；
     * 3.外部类要访问成员内部类的成员和方法需要通过实例对象来访问；
     * 4.普通内部类对象依赖外部类对象而存在，即在创建一个普通内部类对象时首先需要创建其外部类对象
     */
    public class Inner{
        /**
         * 私有成员可以给外部类访问；内部类编译时，内部类提供静态方法，接收一个内部类对象作为参数
         */
        private int inj=3; //内部类私有成员
        int ini = outi + 1; //内部类可以访问外部类的成员变量

        public void inPrint(){
            outPrint();//内部类可以访问外部类的成员方法
            System.out.println("inPrint()");
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        //这里因为main是静态方法，访问非静态的 Inner对象需要一个Outer对象
        Inner inner = outer.new Inner();
        inner.inPrint();//外部类访问内部类的成员方法
        System.out.println(inner.inj);//外部类访问内部类的成员变量
    }

}

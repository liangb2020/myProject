package pers.qxllb.common.test.inner;

/**
 * 静态内部类
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/21 20:09
 */
public class OuterStatic {

    private int outi=0; //外部类的成员

    /**
     * 私有成员需要给内部类访问，通过外部类提供的静态方法来得到对应外部类对象的私有成员的值
     */
    private static  String outStr ="outStr";

    /**
     * 外部类非静态方法
     */
    public void outPrint(){
        System.out.println("outPrint()");
    }

    public static void outStatic(){
        System.out.println("outStatic:");
    }

    /**
     * 1.静态内部类不依赖于外部类的加载。（根据是否使用去加载，相当于内外平行的关系。）
     * 2.静态内部类不能直接访问外部类的非静态成员。（因为外部类加载的时候非静态成员是没有加载的，除非实例化之后）
     * 3.创建一个类的静态内部类对象不需要依赖其外部类对象
     */
    static class Inner{
        String iStr = outStr;
        Outer outer = new Outer();

        public void inPrint(){
            outer.outPrint();//只能通过实例
            System.out.println("in:"+iStr);
        }
    }

    public static void main(String[] args) {
        //可以直接new出来，因为相当于是两个独立的类
        Inner inner = new Inner();
        inner.inPrint();
        String iStr = inner.iStr;

        String oStr = OuterStatic.outStr;

    }

}

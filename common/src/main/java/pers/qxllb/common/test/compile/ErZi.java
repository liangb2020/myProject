package pers.qxllb.common.test.compile;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/20  23:45
 */
public class ErZi extends BaBa {

    public static void sayStatic(){
        System.out.println("ErZi sayStatic:"+strStatic);
    }

    /**
     * 父类静态代码块--> 子类静态代码块-->父类非静态代码块-->父类构造方法-->子类非静态代码块-->子类构造方法
     * @param args
     */
    public static void main(String[] args){

        String str1 = BaBa.strStatic;
        String str2 = ErZi.strStatic;

        ErZi.sayStatic();

    }

}

package pers.qxllb.common.test.compile;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/20  23:38
 */
public class BaBa {

    public static String strStatic="Baba_Static";
    public String strTmp;

    static {
        System.out.println("static init()");
    }

    {
        System.out.println("object init()");
    }

    public void say(){
        System.out.println("BaBa say"+strStatic);
    }

    public static void sayStatic(){
        System.out.println("BaBa sayStatic:"+strStatic);
    }

}

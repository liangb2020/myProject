package pers.qxllb.common.test.copyvalue;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/7/2  23:53
 */
public class Example1 {

    /**
     * 基本数据类型，都是值传递赋值
     * @param i
     * @param str
     */
    public static void setValue(int i,String str){
        i = 5;
        str="efg";

        System.out.println("i:"+i+",str:"+str);
    }

    public static void main(String[] args){

        int i=0;
        String str="abc";
        setValue(i,str);
        System.out.println("i:"+i+",str:"+str);
    }

}

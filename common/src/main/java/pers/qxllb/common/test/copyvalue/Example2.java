package pers.qxllb.common.test.copyvalue;

import com.alibaba.fastjson.JSON;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/7/2  23:58
 */
public class Example2 {

    /**
     * 实参引用的堆地址传递，其实也是值传递，栈中新定义一个test，指向传递过来的堆引用地址
     * @param test
     */
    public void setValue(Test test){
        System.out.println("Test object:"+test+",value:"+ JSON.toJSONString(test));

        test.setName("efg");
        test.setJ(10);

    }

    /**
     * 侧面例子证明不是实参的引用传递，而是堆地址值传递
     * 1.如果是引用传递，则test也是调用者的引用；这时候test new一个对象（堆新地址），那么实参也应用跟着改到这个新堆地址，实际不是；
     * 2.这里是把实际参数的引用的地址复制了一份，传递给了形式参数，其实是值传递，把实参对象引用的地址当做值传递给了形式参数。
     * @param test
     */
    public void setValue2(Test test){
        System.out.println("Test object before:"+test+",value:"+ JSON.toJSONString(test));

        test = new Test();
        test.setJ(100);
        test.setName("okr");

        System.out.println("Test object after:"+test+",value:"+ JSON.toJSONString(test));

    }

    public static void main(String[] args){

        Test test = new Test();
        test.setJ(5);
        test.setName("abc");

        System.out.println("Test object:"+test+",value:"+ JSON.toJSONString(test));
        Example2 example2 = new Example2();
        example2.setValue(test);
        System.out.println("Test object:"+test+",value:"+ JSON.toJSONString(test));


        example2.setValue2(test);
        System.out.println("Test object:"+test+",value:"+ JSON.toJSONString(test));

    }

}

class Test{
    String  name;
    int j;

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public void setJ(int j){
        this.j=j;
    }

    public int getJ(){
        return j;
    }

}
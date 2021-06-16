package pers.qxllb.common.test.compile;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/16 9:25
 */
public class CompileTest {


    private static int add1(int x1, int x2, int x3, int x4) {
        return add2(x1, x2) + add2(x3, x4);
    }
    private static int add2(int x1, int x2) {
        return x1 + x2;
    }

    public static void main(String[] args) {
        for(int i=0; i<1000000; i++) {//方法调用计数器的默认阈值在C1模式下是1500次，在C2模式在是10000次，我们循环遍历超过需要阈值
            add1(1,2,3,4);
        }
    }
}
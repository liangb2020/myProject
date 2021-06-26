package pers.qxllb.common.test;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/21  23:51
 */
public class InnerClassTest {
    int field1 = 1;
    private int field2 = 2;

    public InnerClassTest() {
        InnerClassA inner = new InnerClassA(); //外部类可以直接new内部非静态类
        int v = inner.x2;
    }

    public class InnerClassA {
        int x1 = field1;
        private int x2 = field2;
    }


}

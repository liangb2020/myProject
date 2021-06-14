package pers.qxllb.common.jvm;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/13  23:42
 */
public class ShowByteCode {

    private String xx;

    private static final int TEST = 1;

    public ShowByteCode() {
    }

    public int calc() {
        int a = 100;
        int b = 200;
        int c = 300;
        return (a + b) * c;
    }

    public static void main(String[] args){

        ShowByteCode showByteCode = new ShowByteCode();
        showByteCode.calc();
    }
}

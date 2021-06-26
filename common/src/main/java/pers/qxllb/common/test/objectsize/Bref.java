package pers.qxllb.common.test.objectsize;

import org.openjdk.jol.info.ClassLayout;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/26  8:06
 */
public class Bref {

    private int i = 5;

    private Integer ii = 128;

    public static void main(String[] args) throws InterruptedException {
        Bref b = new Bref();
        // 对象大小
        System.out.println(ClassLayout.parseInstance(b));
        // 对象大小组成
        /**
         * Shallow Size：12(B Header) + 4 (i instance) + 4 (ii reference) + 4(padding) = 24byte
         * Retained Size: 12(B Header) + 4 (i instance) + 4 (ii reference) + (12(ii header) + 4(instance)+ 0(padding)) + 4(padding) = 40bytes
         */
        System.out.println(ClassLayout.parseClass(b.getClass()).toPrintable());

    }
}

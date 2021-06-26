package pers.qxllb.common.test.objectsize;

import org.openjdk.jol.info.ClassLayout;

import java.io.IOException;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/26  8:10
 */
public class Carray {
    private int i;
    private char[] cc;

    public Carray() {
        i = 5;
        cc = new char[]{'a', 'b', 'c'};
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Carray c = new Carray();
        /**
         * Shallow Size：12(C Header) + 4 (i instance) + 4 (cc reference) + 4(padding) = 24bytes
         *
         * Retained Size: 12(C Header) + 4 (i instance) + 4 (cc reference) + (16(cc header) + 2(instance) * 3+ 2(padding)) + 4(padding) = 48bytes
         */
        System.out.println(ClassLayout.parseClass(c.getClass()).toPrintable());

        System.in.read();

    }
}

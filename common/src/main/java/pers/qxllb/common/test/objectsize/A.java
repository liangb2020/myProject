package pers.qxllb.common.test.objectsize;

import org.openjdk.jol.info.ClassLayout;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/26  7:43
 */
public class A {

    /**
     * 1.对象头(Header): 32位-8byte,64位(16byte,12byte-开启压缩指针)
     * 1.1 数据对象头：32位-12byte,64位(24byte,16byte-开启压缩指针)  多了一个数据的大小
     * 2.实例数据(Instance Data)
     * 3.对其补充(Padding)：对象的大小必须是8字节的整数倍
     */

    private int i;   //32bit--4bytes; 64bit-4bytes

    public static void main(String[] args) throws InterruptedException {
        A a = new A();

        /**
         * 64位下默认开启指针压缩，对象头位12byte, i4byte,此时12 + 4 = 16 可以整除8，所以padding=0,最终
         * 12(header) + 4(instance data)+0(padding)=16byte
         */
        System.out.println(a);

        // 对象大小
        System.out.println(ClassLayout.parseInstance((new A())));
        // 对象大小组成
        System.out.println(ClassLayout.parseClass(new A().getClass()).toPrintable());

    }
}

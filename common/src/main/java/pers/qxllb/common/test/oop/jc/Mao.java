package pers.qxllb.common.test.oop.jc;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/27  23:59
 */
public class Mao extends Animal implements Eat {
    @Override
    public void eat() {
        System.out.println("Mao eat()");
    }
}

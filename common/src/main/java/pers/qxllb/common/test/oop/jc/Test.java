package pers.qxllb.common.test.oop.jc;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/28  0:00
 */
public class Test {

    public static void main(String[] args){
        Animal animal = new Dog();
        animal.zoulu();
        String str=animal.name;

        animal = new Mao();
        animal.zoulu();
    }

}

package pers.qxllb.common.test.oop.jc;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/27  19:42
 */
public class Dog extends Animal implements Eat {

    /**
     * 子类中的成员变量如果和父类中的成员变量同名，那么即使他们类型不一样，只要名字一样。父类中的成员变量都会被隐藏
     * 在子类中，父类的成员变量不能被简单的用引用来访问。而是，必须从父类的引用获得父类被隐藏的成员变量
     */
    public String name="";

    @Override
    public void eat() {
        System.out.println("Dog eat()");
    }

    public static void main(String[] args){
        Dog dog = new Dog();
        dog.eat();
        dog.zoulu();
        System.out.println(":"+dog.name);

    }
}

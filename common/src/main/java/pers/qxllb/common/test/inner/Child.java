package pers.qxllb.common.test.inner;

/**
 * 匿名内部类：匿名内部类只能使用一次，它通常用来简化代码编写
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/22 14:38
 */
public class Child{

    public void eat(){
        Person person = new Person() {
            @Override
            public void eat() {
                System.out.println("Child eat");
            }
        };
    }

    public void fly(){
        Fly fly = new Fly() {
            @Override
            public void fadongji() {
                System.out.println("Child fly().");
            }
        };
    }
}

abstract class Person {
    public abstract void eat();
}

interface Fly{
    void fadongji();
}
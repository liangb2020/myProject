package pers.qxllb.common.test.enum1;

/**
 * 类描述:枚举类中还可以定义抽象的方法，但每个枚举项中必须实现对应的抽象方法：
 *
 * @author liangb
 * @date 2021/6/27  1:07
 */
public enum SessionType3 {

    SPRING{
        //匿名内部类的实现抽象方法
        @Override
        public String printSession() {
            return "spring";
        }
    },
    SUMMER{
        //匿名内部类的实现抽象方法
        @Override
        public String printSession() {
            return "summer";
        }
    },
    AUTUMN{
        //匿名内部类的实现抽象方法
        @Override
        public String printSession() {
            return "autumn";
        }
    },
    WINTER{
        //匿名内部类的实现抽象方法
        @Override
        public String printSession() {
            return "winter";
        }
    };

    public abstract String printSession();

    public static void main(String[] args){

        System.out.println(SessionType3.AUTUMN.printSession());

    }
}

package pers.qxllb.common.test.enum1;

/**
 * 类描述:定义只有一个参数的枚举类
 *
 * @author liangb
 * @date 2021/6/26  23:03
 */
public enum SessionType1 {

    // 通过构造函数传递参数并创建实例
    SPRING("spring"),
    SUMMER("summer"),
    AUTUMN("autumn"),
    WINTER("winter");

    // 定义实例对应的参数
    private String msg;

    // 必写：通过此构造器给枚举值创建实例
    SessionType1(String msg) {
        this.msg = msg;
    }

    // 通过此方法可以获取到对应实例的参数值
    public String getMsg() {
        return msg;
    }

    public static void main(String[] args){
        System.out.println(SessionType1.AUTUMN.getMsg());
    }

}

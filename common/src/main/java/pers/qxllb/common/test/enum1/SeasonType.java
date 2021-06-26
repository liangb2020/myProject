package pers.qxllb.common.test.enum1;

/**
 * 无参枚举类
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/22 16:49
 */
public enum SeasonType {
    SPRING, SUMMER, AUTUMN, WINTER;

    public static void main(String[] args){
        // 根据实际情况选择下面的用法即可
        SeasonType springType = SeasonType.SPRING;    // 输出 SPRING
        System.out.println(springType);
        int autumnInt = SeasonType.AUTUMN.ordinal();    // 输出 2
        System.out.println(autumnInt);
    }
}

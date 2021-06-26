package pers.qxllb.common.test.enum1;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/26  23:11
 */
public enum SessionType2 {

    // 通过构造函数传递参数并创建实例
    SPRING(1, "spring"),
    SUMMER(2, "summer"),
    AUTUMN(3, "autumn"),
    WINTER(4, "winter");

    // 定义实例对应的参数
    private Integer key;
    private String msg;

    // 必写：通过此构造器给枚举值创建实例
    SessionType2(Integer key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    // 通过此方法可以获取到对应实例的 key 值
    public Integer getKey() {
        return key;
    }

    // 通过此方法可以获取到对应实例的 msg 值
    public String getMsg() {
        return msg;
    }

    // 很多情况，我们可能从前端拿到的值是枚举类的 key ，然后就可以通过以下静态方法获取到对应枚举值
    public static SessionType2 valueofKey(Integer key) {
        for (SessionType2 season : SessionType2.values()) {
            if (season.key.equals(key)) {
                return season;
            }
        }
        throw new IllegalArgumentException("No element matches " + key);
    }

    public static void main(String[] args){
        SessionType2.valueofKey(5);
    }

}

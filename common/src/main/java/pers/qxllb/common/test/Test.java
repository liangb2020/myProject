package pers.qxllb.common.test;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/4/2  21:33
 */
public class Test {

    private final int i = 5;


    @SneakyThrows
    public static void main(String[] args){
        String songNames="[\"圣诞树\",\"小毛驴\",\"小星星(改编)\"]";
        List<String> list = JSON.parseArray(songNames, String.class);
        System.out.println(list);

        //System.in.read();

        System.out.println(Test.class.getClass());


        //毫秒 转 LocalDateTime IOS 交易时间不在订单的有效时间内【大于订单创建时间，小于订单失效时间】，则无效
        LocalDateTime freshRecordTimeSource = LocalDateTime.now();
        //偏移量60分钟
        LocalDateTime freshRecordTime = freshRecordTimeSource.plusMinutes(60);
        System.out.println("freshRecordTimeSource:"+freshRecordTimeSource+",freshRecordTime："+freshRecordTime);

    }
}

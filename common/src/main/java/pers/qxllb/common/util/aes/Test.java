package pers.qxllb.common.util.aes;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;

import java.util.List;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/4/2  21:33
 */
public class Test {


    @SneakyThrows
    public static void main(String[] args){
        String songNames="[\"圣诞树\",\"小毛驴\",\"小星星(改编)\"]";
        List<String> list = JSON.parseArray(songNames, String.class);
        System.out.println(list);

        System.in.read();

    }
}

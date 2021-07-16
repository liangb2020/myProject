package pers.qxllb.common.test.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/6  0:35
 */
public class FastJsonTest {

    public void putFastJsonObject(){



    }

    public void getFastJsonObject(){

    }


    public static void main(String[] args){


        Map<String, Object> map = new HashMap<>();
        map.put("roomId", "123456");

        JSONObject body = new JSONObject();
        body.put("event", "roomHotIncrNotify");
        body.put("body", JSON.toJSONString(map));

        String str = JSON.toJSONString(body);


        JSONObject bodyRecv = JSON.parseObject(str);
        String event = body.getString("event");
        JSONObject roomIdJson = JSON.parseObject(body.getString("body"));
        String roomId = roomIdJson.getString("roomId");
        System.out.println(roomId);

    }

}

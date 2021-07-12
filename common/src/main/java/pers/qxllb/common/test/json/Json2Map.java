package pers.qxllb.common.test.json;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/7/12 14:25
 */
@Data
public class Json2Map {

    /**
     * 消息内容包括以下三种情况组合：
     * 1 普通文本
     * 2 <image url="{url}"/>
     * 3 <at id="{id}">name</at>
     */
    private String content;

    /**
     * 消息颜色
     * 黄色:#FFEEEEEE      默认
     * 其他颜色....
     */
    private String textColor="#FFEEEEEE";

    /**
     * 消息背景颜色
     * 半透明：#66000000     默认
     * 其他颜色....
     */
    private String backgroundColor="#66000000";

    /**
     * 是否显示按钮
     */
    private boolean showButton;

    /**
     * 高亮按钮文案,基于showButton来用
     */
    private String lightButtonText;

    /**
     * 灰色按钮文案,基于showButton来用
     * 是否灰色客户端控制，服务端仅下发文案
     */
    private String grayButtonText;

    /**
     * 点击事件,基于showButton来用
     */
    private String action;

    /**
     * 点击事件的其他补充信息
     */
    private String otherInfo;

    /**
     * 文案中包含@用户名 情况，支持点击用户名弹出profile
     */
    private List<UserInfo> userList;

    @Data
    public static class UserInfo{
        /**
         * 用户ID
         */
        private String userId;

        /**
         * 用户头像框
         */
        private String userAvatar;

        /**
         * 用户昵称
         */
        private String userName;
    }

    public static String display1(){
        Json2Map json2Map = new Json2Map();
        String url="http://....";
        String id="10000";
        String name="沐沐";
        Long roomId=1230L;
        Map<String,Object> map = Maps.newHashMap();
        map.put("roomId",roomId);

        String content= "感谢"+"<image url=\"" + url + "\"/>"+"<at id=\"" + id + "\">" + name + "</at>"+"送的礼物抢占成功"+"<image url=\"" + url + "\"/>"+"<at id=\"" + id + "\">" + name + "</at>"+"的礼物墙NO.X";
        json2Map.setContent(content);
        json2Map.setShowButton(true);
        json2Map.setLightButtonText("高亮");
        json2Map.setGrayButtonText("灰度");
        json2Map.setAction("102");
        json2Map.setOtherInfo(JSON.toJSONString(map));

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        userInfo.setUserAvatar("http://avatrar");
        userInfo.setUserName(name);

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setUserId("20000");
        userInfo1.setUserAvatar("http://avatrar2");
        userInfo1.setUserName("风风");

        List<UserInfo> userInfos = Lists.newArrayList();
        userInfos.add(userInfo);
        userInfos.add(userInfo1);

        json2Map.setUserList(userInfos);

        String str=JSON.toJSONString(json2Map);

        System.out.println("json2Map before:"+str);

        return str;
    }

    public static String display2(){
        Json2Map json2Map = new Json2Map();
        json2Map.setContent("i love u");
        return JSON.toJSONString(json2Map);
    }

    public static String display3(){
        Json2Map json2Map = new Json2Map();
        String url="http://....";
        String id="10000";
        String name="沐沐";
        Long roomId=1230L;
        Map<String,Object> map = Maps.newHashMap();
        map.put("roomId",roomId);

        String content= "感谢"+"<image url=\"" + url + "\"/>"+"<at id=\"" + id + "\">" + name + "</at>"+"送的礼物抢占成功"+"<image url=\"" + url + "\"/>"+"<at id=\"" + id + "\">" + name + "</at>"+"的礼物墙NO.X";
        json2Map.setContent(content);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        userInfo.setUserAvatar("http://avatrar");
        userInfo.setUserName(name);

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setUserId("20000");
        userInfo1.setUserAvatar("http://avatrar2");
        userInfo1.setUserName("风风");

        List<UserInfo> userInfos = Lists.newArrayList();
        userInfos.add(userInfo);
        userInfos.add(userInfo1);

        json2Map.setUserList(userInfos);

        String str=JSON.toJSONString(json2Map);

        System.out.println("json2Map before:"+str);
        return JSON.toJSONString(json2Map);
    }

    public static void main(String[] args){

        String str=display3();
        Map<String,Object> extra = JSON.parseObject(str, Map.class);
        System.out.println("json2Map after:"+extra);

        System.out.println("json2Map after to json:"+new Gson().toJson(extra));


    }
}



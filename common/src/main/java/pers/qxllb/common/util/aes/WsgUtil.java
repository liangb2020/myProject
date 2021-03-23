package pers.qxllb.common.util.aes;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * 无须保镖帮助类
 *
 * @author
 * @createDate 2019/9/29
 */
@Slf4j
public final class WsgUtil {

    /**
     * 解密
     *
     * @param encodeBody 待加密的body
     * @param compress 是否压缩
     * @return String
     */
    public static String decode(byte[] encodeBody, int compress) {
        // 使用无线保镖解密
        byte[] bodyData ;
        String decodedData = null;

        try {
            if (compress == 1) {
                bodyData = GZipUtil.uncompress(SingDuckEncryption.decrypt(encodeBody));
            } else {
                bodyData = SingDuckEncryption.decrypt(encodeBody);
            }

            if (bodyData != null && bodyData.length > 0) {
                decodedData = new String(bodyData, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            log.error("decodeBodyByWsg error: " + e.getMessage(), e);
        }

        return decodedData;
    }

    public static String decode(String body, int compress) {
        return decode(Base64.decodeBase64(body), compress);
    }

    /**
     * 获取加密序列号
     *
     * @param body body
     * @return Integer
     */
    public static Integer resolveSecureNo(String body) {
        return SingDuckEncryption.resolveSecureNo(Base64.decodeBase64(body));
    }


    public static String encode(Integer secureNo, String plainText, int compress) {
        try {
            byte[] cipherServiceTicket;
            if (compress == 1) {
                cipherServiceTicket = SingDuckEncryption.encrypt(GZipUtil.compress(plainText), secureNo);
            } else {
                cipherServiceTicket = SingDuckEncryption.encrypt(plainText.getBytes(), secureNo);
            }

            String base64Str = Base64.encodeBase64String(cipherServiceTicket);
            if (compress == 1) {
                if (log.isDebugEnabled()) {
                    log.debug("encodeBodyByWsg origin size: {}, gzip size: {}, wsg size: {}, base64 size: {}",
                        plainText.length(), GZipUtil.compress(plainText).length, cipherServiceTicket.length,
                        base64Str.length());
                }

            }
            return base64Str;
        } catch (Exception e) {
            log.error("encode failed, " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * 加密
     *
     * @param cipherTokenBytes token
     * @param plainText 待加密的文本
     * @param compress 是否压缩
     * @return String
     */
    public static String encode(byte[] cipherTokenBytes, String plainText, int compress) {
        try {
            // 根据密文获取密钥序号
            int secureNo = SingDuckEncryption.resolveSecureNo(cipherTokenBytes);
            return encode(secureNo, plainText, compress);
        } catch (Exception e) {
            log.error("encode failed, " + e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {

        StringBuffer sb =   new StringBuffer();
        //request
        //sb.append("dTCuZCeRocYM/noX4eDnBXAs6wZIT2pH6W2Wn7wDIIYbp480Tf6LvV8h5gC918GzjY5w6kH987cBo1ncZpPNjv8NBEFf/yG8km8O8y5eZzVr52OGlS/Okc0mR7JAf0k64bmPkX8dWDk+ETI1dTCZuME8xBNZQLjAIXiVpatf7lmmirVlNVOw/XTCBmIskMwEM55qY6p++2Vyf8fda2TIjJl8gj8aYmzSGSSZr0BvBRDp13OEVTL6N3Rxl7Ixn6ghVBplMLGeoadotKpjSRL6+u78hKHliUB+yXe2X2h6UuzK/XC+TF/FeJqtuAW3G3USrxgXCAJSn0qVNh+kPdArZKAFmIx3WxkOhrtqgjaCeZklPTe+FMjlDJQ79MSP8Cu6SVxgYmm+HcMwEJn2aqtVcsE4jUDDGyd69P9M/90Ai7iYktW12LCmccPkZZR2672Z7DKMhVd/hzboXhdmt4D35cD1CYK9m4lUXMmGB4LMVRMCHZAmS30INNOWxy0fco9gpSPUFDmq9p8fru80eI6riqWDPoavwVCw+z+Bl0QGMCy3u1F+091onItNfVnyCiv7fCldI0CIxOgfvYwzeXxbi9RX/JDEAQ1Q32Dn0rdJ6Wzw5vc8RQft2EQ7oehfpxYBg0Y=");
        //response
        sb.append("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");
        byte[] encodeBody = Base64.decodeBase64(sb.toString());
        String bodyDecStr = WsgUtil.decode(encodeBody, 1); //压缩用1，非压缩用0
        System.out.println(bodyDecStr);
        System.out.println(JSON.toJSONString(JSON.parse(bodyDecStr),true));


        System.out.println("ThreadName:"+Thread.currentThread().getName()+",ThreadId:"+Thread.currentThread().getId());

        System.out.println(System.currentTimeMillis());

        String str="未来城光辉形象\n" +
                "惠民自己看行\n" +
                "轰轰轰发票抬头：***有限公司o\n" +
                "哈哈哈哈";
        System.out.println(str);

        System.out.println(str.replace("\n"," "));





    }



}

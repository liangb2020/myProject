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
        //sb.append("dTD8cPEy571fG5KdyxvPN1+ZCpzEdOasCumyZ2sWJqqN4TCycSC7Sd7+3GNrV55z1LyP3re2HtcNGYo5PusM7uD5");
        //response
        sb.append("dTAXauxQpemGdLPU0XZgFEqmyVF2jY8biM6zs4Gwww6WA1q8pOCIaeLFFfclRVouvd7HiqIyD8YoZyD2ygWIfllI");
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

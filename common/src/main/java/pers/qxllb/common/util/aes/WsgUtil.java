package pers.qxllb.common.util.aes;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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
        sb.append("dTD8cPEy571fG5KdyxvPN1+ZCpzEdOasCumyZ2sWJqqN4TCycSC7Sd7+3GNrV55z1LyP3re2HtcNGYo5PusM7uD5");
        //response
        //sb.append("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");
        byte[] encodeBody = Base64.decodeBase64(sb.toString());
        String bodyDecStr = WsgUtil.decode(encodeBody, 1); //压缩用1，非压缩用0
        System.out.println(bodyDecStr);
        System.out.println(JSON.toJSONString(JSON.parse(bodyDecStr),true));

        //decode
        String ut="dTDEqMzEl2Ic5yZI2%2Fe8lEKbh0idzohYv3FLgs25sTSJTg%3D%3D111";
        String utDe = SingDuckEncryption.decryptFromBase64(URLDecoder.decode(ut));
        System.out.println(utDe);

        String body="{\"a\":13}";
        String en = WsgUtil.encode(utDe.getBytes(),body,1);
        System.out.println(en);
    }



}

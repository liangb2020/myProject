package pers.qxllb.common.util.aes;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 唱鸭AES加密类
 * @CreateDate 2020/9/10 20:47
 * @Author by
 */
public class SingDuckEncryption {
    public static final String CHAR_ENCODING = "UTF-8";
    private static Map<Integer, String> KEY_MAP = new HashMap<>();
    public static final int DEFAULT_SECURE_NO = 30000;

    static {
        KEY_MAP.put(30000, "4yA41WXmX3rxXv2o");
    }

    /**
     * 用密文的序列号加密数据
     *
     * @param data              加密的字节数据
     * @param sourceEncryptData 加密原文
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] sourceEncryptData) {
        int secrureNo = resolveSecureNo(sourceEncryptData);
        return encrypt(data, secrureNo);
    }

    /**
     * AES加密
     *
     * @param data     加密的字节数据
     * @param secureNo 加密序号
     * @return
     */
    public static byte[] encrypt(byte[] data, int secureNo) {
        if (KEY_MAP.containsKey(secureNo)) {
            String key = KEY_MAP.get(secureNo);
            try {
                return encrypt(data, secureNo, key.getBytes(CHAR_ENCODING));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("encoding fail!", e);
            }
        } else {
            throw new IllegalArgumentException("encrypt secureNo not exist.");
        }
    }

    /**
     * AES加密
     *
     * @param data     加密的字节数据
     * @param secureNo 加密序号
     * @param key      加密Key
     * @return
     */
    public static byte[] encrypt(byte[] data, int secureNo, byte[] key) {
        if (data == null) {
            throw new IllegalArgumentException("data must be specified");
        }
        if (key == null) {
            throw new IllegalArgumentException("key must be specified");
        }

        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }

        byte[] valueByte = AesEncryption.encrypt(data, key);
        byte[] prefixCode = getSecureNoData(secureNo);
        byte[] resultBytes = new byte[valueByte.length + prefixCode.length];

        System.arraycopy(prefixCode, 0, resultBytes, 0, prefixCode.length);
        System.arraycopy(valueByte, 0, resultBytes, prefixCode.length, valueByte.length);

        return resultBytes;
    }

    /**
     * AES解密
     *
     * @param data 待解密的字节数据
     * @return
     */
    public static byte[] decrypt(byte[] data) {
        int secureNo = resolveSecureNo(data);
        if (KEY_MAP.containsKey(secureNo)) {
            String key = KEY_MAP.get(secureNo);
            try {
                return decrypt(data, key.getBytes(CHAR_ENCODING));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("encoding fail!", e);
            }
        } else {
            throw new IllegalArgumentException("encrypt secureNo not exist.");
        }
    }

    /**
     * AES解密
     *
     * @param data 待解密的字节数据
     * @param key  加密Key
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        if (data == null) {
            throw new IllegalArgumentException("data must be specified");
        }

        if (key == null) {
            throw new IllegalArgumentException("key must be specified");
        }

        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }

        byte[] realEncryptBytes = new byte[data.length - 2];
        System.arraycopy(data, 2, realEncryptBytes, 0, realEncryptBytes.length);
        return AesEncryption.decrypt(realEncryptBytes, key);
    }

    /**
     * AES加密
     *
     * @param data 需加密的字符串原文
     * @return 返回base64格式密文
     */
    public static String encryptToBase64(String data) {
        if (KEY_MAP.containsKey(DEFAULT_SECURE_NO)) {
            return encryptToBase64(data, DEFAULT_SECURE_NO);
        } else {
            throw new IllegalArgumentException("default key not specified.");
        }
    }

    /**
     * 用密文的序列号加密数据
     *
     * @param data                    需加密的字符串原文
     * @param base64SourceEncryptData 加密原文
     * @return 返回base64格式密文
     */
    public static String encryptToBase64UseEncryptSecureNo(String data, String base64SourceEncryptData) {
        int secrureNo = resolveSecureNo(base64SourceEncryptData);
        return encryptToBase64(data, secrureNo);
    }

    /**
     * 用指定加密序号AES加密
     *
     * @param data     需加密的字符串原文
     * @param secureNo 加密序号
     * @return 返回base64格式密文
     */
    public static String encryptToBase64(String data, int secureNo) {
        if (KEY_MAP.containsKey(secureNo)) {
            String key = KEY_MAP.get(secureNo);
            return encryptToBase64(data, secureNo, key);
        } else {
            throw new IllegalArgumentException("encrypt secureNo not exist.");
        }
    }

    /**
     * 用指定加密序号和私钥AES加密
     *
     * @param data     需加密的字符串原文
     * @param secureNo 加密序号
     * @param key      Key字符串
     * @return 返回base64格式密文
     */
    public static String encryptToBase64(String data, int secureNo, String key) {
        try {
            byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), secureNo, key.getBytes(CHAR_ENCODING));
            return new String(Base64.getEncoder().encodeToString(valueByte));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding fail!", e);
        }
    }

    /**
     * AES解密
     *
     * @param data 解密的Base64编码字符串
     * @return 加密原文
     */
    public static String decryptFromBase64(String data) {
        byte[] originalData = Base64.getDecoder().decode(data);
        int secureNo = resolveSecureNo(originalData);

        if (KEY_MAP.containsKey(secureNo)) {
            String key = KEY_MAP.get(secureNo);
            return decryptFromBase64(data, key);
        } else {
            throw new IllegalArgumentException("encrypt secureNo not exist.");
        }
    }

    /**
     * AES解密
     *
     * @param data 解密的Base64编码字符串
     * @param key  Key字符串
     * @return 加密原文
     */
    public static String decryptFromBase64(String data, String key) {
        try {
            byte[] originalData = Base64.getDecoder().decode(data);
            byte[] valueByte = decrypt(originalData, key.getBytes(CHAR_ENCODING));
            return new String(valueByte, CHAR_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("aes decrypt fail!", e);
        }
    }

    /**
     * 用指定加密序号和私钥AES加密
     *
     * @param data 需加密的字符串原文
     * @param key  base64格式编码的Key
     * @return 返回base64格式密文
     */
    public static String encryptWithKeyBase64(String data, int secureNo, String key) {
        try {
            byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), secureNo, Base64.getDecoder().decode(key));
            return new String(Base64.getEncoder().encodeToString(valueByte));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * AES解密
     *
     * @param data 解密的Base64编码字符串
     * @param key  base64格式编码的Key
     * @return
     */
    public static String decryptWithKeyBase64(String data, String key) {
        try {
            byte[] originalData = Base64.getDecoder().decode(data);
            byte[] valueByte = decrypt(originalData, Base64.getDecoder().decode(key));
            return new String(valueByte, CHAR_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    /**
     * 返回所有加密序号
     *
     * @return
     */
    public static List<Integer> getSecureNos() {
        return KEY_MAP.keySet().stream().collect(Collectors.toList());
    }

    /**
     * 获取加密序号
     *
     * @param base64EncrypStr base64位格式，加密数据
     * @return
     */
    public static int resolveSecureNo(String base64EncrypStr) {
        byte[] encodedBytes = Base64.getDecoder().decode(base64EncrypStr);
        return resolveSecureNo(encodedBytes);
    }

    /**
     * 获取加密序号
     *
     * @param bytes
     * @return
     */
    public static int resolveSecureNo(byte[] bytes) {
        byte num = 0;
        int secureNo = num ^ (bytes[0] >= 0 ? bytes[0] : bytes[0] + 256);
        secureNo <<= 8;
        secureNo ^= bytes[1] >= 0 ? bytes[1] : bytes[1] + 256;
        return secureNo;
    }

    /**
     * 获取序号编码
     *
     * @param secureNo
     * @return
     */
    private static byte[] getSecureNoData(int secureNo) {
        byte[] bytes = new byte[] {(byte)(255 & secureNo >> 8), (byte)(255 & secureNo)};
        return bytes;
    }

    public static void main(String[] args) {
        String key = KEY_MAP.get(DEFAULT_SECURE_NO);
        //String key = "abcdefghijklmnop";
        String data = "abcd123456";

        String encrypStr = encryptToBase64(data);
        System.out.println(encrypStr);

        byte[] encodedBytes = Base64.getDecoder().decode(encrypStr);
        try {
            String deData = new String(decrypt(encodedBytes, key.getBytes(CHAR_ENCODING)), CHAR_ENCODING);
            System.out.println(deData);
        } catch (UnsupportedEncodingException ex) {

        }
        System.out.println(resolveSecureNo(encodedBytes));
        System.out.println(decryptFromBase64(encrypStr));


        String xxx = "dTD9eiSpqM8RUkVVpa05sHLW3aKfvTPwaJ9/MOjzHs8Xgh3qfa8jt/a2CqzcXUBBkncpIwBPoNEZnSV68MiQGrVwJQzTHE+3+G6AORQ0hHPc0kDSkQszTzdvspfEn6M95DlRj9qo3AhLbcu56VwEDsowW7fU7H/l3IeEe1VqlrgDMqUidYZTriJSa9qYqEKHQTvEznjDOXfZ75fOd7Zgtth8q+v2iRYXg5zrrkT1aQyBkd6GeSx60noc6q/l7YkncoQavFNMppLR88KtAitqhn+RrfSiqMmC0vWuuePf73qEZkpc9CGoafufvmVnTvkQb7HXhL9cDAC6O85XbHDLjtVXwRs8aWQjNtVZa8qk2rjzIMTCIu24CD+qlzM4t4Wc0RE4lkpBZdBXgWYn66h75nbRPUumbr95YbbCsP24QgOudPZJsgNeDWmf+sd9GKMdUjqarwSZy9HZkTAmzSSaLr+qoMJievl/pwGVybUp3HSfOawSJXVKhhDNcNI+In2wL/o57Yl5k33R92/lMGcdBPIK2vslwxno9b5MnXqCaP4fAcSBLxEkK7tBMAzb91OTNG24Ki1l0LBO6FgUXQpydZdRcvBhVBpi/POhfvnKfBhtNIruSj5k1uCqE56fTJ/K/dyDRV3vO5OCOccJHGDUQSY5gmaPFNl722meVC902dKZP0sGShzBcHLPO/3xnkH92UWu7Hu/SlaU3ZZPnX5piqBHekUEmT2Mx8ntHxa5U+5a3pzjSsQK+dpmcU8d9g9O9cA0ba2YTDPu8AY1MlxhmaifTSvfJZf0+yM0Q0rO97LGVeVh0oI1qC3SDUXHHx70FVR0mr/gn9w9LR6n4t0G4K48WQEkV9S652K5lfxCoCMvCJh3vbJZGNBRVbcSX1rvDTTUdh2+Qfm3iFnfjpUtZkLoNDNhHGyAwzir32J6x7aRbgFRjOjObqC5Vxk/BLajPCfH010XBbDLXBIPwJi8fKDcyThl6isPEB9nbpAH1EftHQupmjYLDn5LZvxJrgC362Ntns9BU7Qj/X/yuW5acE9IINr6X/VDXjtKd5vTQhoFYkH6WAzvnjYb67jInSnwHMbnwYVnOsfyxyPqq+IZBi0aEOY4wcC0XtU9LHZJo2MOR55e8mauKFHsy+xNJPQmdVMugWLgHvBP8IOnXzfJflrgP/brd9k1+uWRXEBvVrsFY5W27iAIOR3bokchTYUAw800EaYid47OgMF/Pc+pmEbcG6d2hByl4p4Jhqb4BUQ7NBiQVnnDZc3mO3QkQZ1DTGa47hE8S9GCw2RUcB7tz/njZTqYgtQIdH/5dx4eADffThckdGCg/gvg9xnF+VoCqi+1+vsKDVXUWoLNFpQykUxTgxSgMt+KSfoxUXD4UifNAdn1Y1uW57t35aSJYaSXjCb4gWQgA4kJrduY63C1Cq/Rec2eVZT3DCHab/es+d5gJqdKaumKxeOgcNVKPqB6SR/8i02qx02q0yP259iPCgmy3APv+TzAF37PlP489V0m28iG9oB2eM1r4Nw2VbUH+fRkMKqnXUkTCq7Te1KE83EzyeV/m+YiKetrY5vz7ykm0raF0DFTwkHMo/QK55vkUt3eINyzbKJ3fkk94wU2OrrEgNCLDup1AfExBkFFD3VL/VzdnHNhPdt8U+tEkWGvZLWilma/9PjpxoHjn4RqdqGzOP7oRw52jZcllUvOMgQNf9BTc+UWsWiF0Ow+OLxMy8A=";
        encodedBytes = Base64.getDecoder().decode(xxx);
        try {
            encodedBytes = GZipUtil.uncompress(SingDuckEncryption.decrypt(encodedBytes));
            String deData = new String(encodedBytes, CHAR_ENCODING);
            System.out.println(deData);
        } catch (UnsupportedEncodingException ex) {

        }
    }
}

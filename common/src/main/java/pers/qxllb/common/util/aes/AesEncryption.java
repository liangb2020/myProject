package pers.qxllb.common.util.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description
 * @CreateDate 2020/9/10 19:57
 * @Author by
 */
public class AesEncryption {

    public static final String AES_CBC_PKCS7PADDING = "AES/CBC/PKCS7Padding";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
            // 填充PKCS7Padding支持
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS7PADDING);
            byte[] ivKey = new byte[key.length];
            for (int i = 0; i < key.length; i++) {
                ivKey[key.length - i - 1] = key[i];
            }
            //使用CBC模式
            IvParameterSpec iv = new IvParameterSpec(ivKey);
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, seckey, iv);
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
            // 填充PKCS7Padding支持
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS7PADDING);
            byte[] ivKey = new byte[key.length];
            for (int i = 0; i < key.length; i++) {
                ivKey[key.length - i - 1] = key[i];
            }
            //使用CBC模式，需要一个向量iv
            IvParameterSpec iv = new IvParameterSpec(ivKey);
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, seckey, iv);
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static void main(String[] args){

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(30));
        //
        CountDownLatch cdl = new CountDownLatch(1);

        String st = "i love uuuu ";

        //并发1000
        for(int i=0;i<1000;i++){
            new Thread(()->{
                try {
                    cdl.await();  //hand 住线程，等所有线程篡创建OK了，再并发开始业务
                    long start = System.currentTimeMillis();
                    System.out.println(encrypt(st.getBytes(),"4yA41WXmX3rxXv2o".getBytes())+"------"+Thread.currentThread().getState().name()+"--"+(System.currentTimeMillis()-start)+"\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        cdl.countDown();//可以开始并

    }
}

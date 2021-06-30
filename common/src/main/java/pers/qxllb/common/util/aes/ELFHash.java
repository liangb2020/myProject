package pers.qxllb.common.util.aes;

/**
 * 字符串哈希算法
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/29 17:00
 */
public class ELFHash {

    public static long eLFHash(String str) {
        long hash = 0;
        long x = 0;
        for(int i = 0; i < str.length(); i++){
            hash = (hash << 4) + str.charAt(i);
            if((x = hash & 0xF0000000L) != 0){
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }
        return hash & 0x7FFFFFFF;
    }

    public static void main(String[] args){
        System.out.println(eLFHash("1fasdfsadfdsafsdafsdaf"));
    }

}

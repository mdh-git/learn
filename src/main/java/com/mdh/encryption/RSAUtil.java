package com.mdh.encryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * DESCRIPTION: TODO
 *
 * @author donghao.ma
 * @date 2019/6/4 14:49
 */
public class RSAUtil {

    public static void main(String args[]) throws IOException{

        //定义一个BASE64Encoder 
        BASE64Encoder encode = new BASE64Encoder();
        //要转换的base64
        String base64 = "NnBrNX+xSeN+WuMbVlfo0ulqCfXGwFJf3FdZmC1VLP9KT2qaygwQNT2YG/Xm6J/o7M2fHLlHfilv6/oNbDYsACiKocofJT3GgMsXzCYnob0OGqg0Ug9GpXHwcNqp8UwKDqF6wk5Y5pzg0FrH0Z4vhuljiap5RbBuvCJ+OD1s8jc=";

        //新建一个BASE64Decoder 
        BASE64Decoder decode = new BASE64Decoder();
        //将base64转换为byte[]
        byte[] b = decode.decodeBuffer(base64);
        //打印转换后的byte[]
        System.out.println(new String(b));
        for (int i = 0; i < b.length; i++){
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase());
            //String str = "NnBrNX+xSeN+WuMbVlfo0ulqCfXGwFJf3FdZmC1VLP9KT2qaygwQNT2YG/Xm6J/o7M2fHLlHfilv6/oNbDYsACiKocofJT3GgMsXzCYnob0OGqg0Ug9GpXHwcNqp8UwKDqF6wk5Y5pzg0FrH0Z4vhuljiap5RbBuvCJ+OD1s8jc=";
            //System.out.println(parseHexStr2Byte(str));
        }
    }


    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}

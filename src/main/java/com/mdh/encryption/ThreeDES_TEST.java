package com.mdh.encryption;

import java.net.URLEncoder;

/**
 *
 * @author madonghao
 * @date 2018/10/23
 */
public class ThreeDES_TEST {

    public static void main(String[] args) throws Exception {
        final String key = "cf410f84904a44cc8a7f48fc4134e8f9";
        // 加密流程
        String telePhone = "18516157906";
        ThreeDES threeDES = new ThreeDES();
        String telePhone_encrypt = "";
        telePhone_encrypt = threeDES.encryptThreeDESECB(URLEncoder.encode(telePhone, "UTF-8"), key);
        System.out.println("3DES加密:" + telePhone_encrypt);

        // 解密流程
        String tele_decrypt = threeDES.decryptThreeDESECB(telePhone_encrypt, key);
        System.out.println("模拟代码解密:" + tele_decrypt);
    }
}

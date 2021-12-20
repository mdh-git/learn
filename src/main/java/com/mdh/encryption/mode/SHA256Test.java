package com.mdh.encryption.mode;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: madonghao
 * @Date: 2019/6/4 14:49
 */
public class SHA256Test {

    public static void main(String[] args) {
        String encoding = SHA256encoding("哈哈");
        System.out.println(encoding);
    }

    /**
     *
     * @param data 传入要加密的数据  可以传入byte数组、字符串、还有输入流，看自己的意愿（sha256Hex这个方法）
     * @return
     */
    public static String SHA256encoding(String data) {
        String getEncoding = DigestUtils.sha256Hex(data);
        return getEncoding;
    }
}

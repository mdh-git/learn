package com.mdh.encryption.mode;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: madonghao
 * @Date: 2019/6/4 14:49
 */
public class MD5Test {

    public static void main(String[] args) {
        String encoding = encoding("哈哈");
        System.out.println(encoding);
    }

    /**
     *
     * @param data 传入要加密的数据  可以传入byte数组、字符串、还有输入流，看自己的意愿（md5Hex这个方法）
     * @return
     */
    public static String encoding(String data) {
        String getEncoding = DigestUtils.md5Hex(data);
        return getEncoding;
    }
}

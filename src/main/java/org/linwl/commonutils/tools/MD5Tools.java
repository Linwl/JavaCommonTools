package org.linwl.commonutils.tools;

import cn.hutool.crypto.SecureUtil;

/**
 * @program: JavaCommonTools
 * @description:
 * @author: linwl
 * @create: 2020-08-13 14:48
 **/
public class MD5Tools {

    private MD5Tools() {}

    /**
     * 加密
     *
     * @param data 加密字符
     * @param secret 加密盐
     * @return
     */
    public static String encryString(String data, String secret) {
        return SecureUtil.md5(data + secret);
    }
}

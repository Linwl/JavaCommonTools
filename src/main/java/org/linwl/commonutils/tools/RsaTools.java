package org.linwl.commonutils.tools;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * @program: JavaCommonTools
 * @description: rsa加密工具
 * @author: linwl
 * @create: 2020-08-13 14:43
 **/
public class RsaTools {
    private RsaTools(){}

    /**
     * 加密
     * @param publicKey 公钥(base64)
     * @param unencryptStr 未加密字符
     * @return
     */
    public static String encrypt(String publicKey,String unencryptStr){
        RSA rsa = SecureUtil.rsa(null, publicKey);
        return new String(rsa.encryptHex(unencryptStr, KeyType.PublicKey));
    }

    /**
     * 解密
     * @param privateKey 私钥(base64)
     * @param encryptStr 加密字符
     * @return
     */
    public static String decrypt(String privateKey,String encryptStr){
        RSA rsa = SecureUtil.rsa(privateKey, null);
        return new String(rsa.decrypt(encryptStr,KeyType.PrivateKey));
    }
}

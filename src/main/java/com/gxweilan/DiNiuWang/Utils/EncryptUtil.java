package com.gxweilan.DiNiuWang.Utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.codec.binary.Base64;


/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/6 15:16
 */
public class EncryptUtil {
    public static String MD5EncryptMethod(String str){
        String md5Str = DigestUtils.md5Hex(str);
        return md5Str;
    }

    public static String SHA1EncryptMethod(String str){
        String sha1Str = DigestUtils.sha1Hex(str);
        return sha1Str;
    }

    public static String SHA256EncryptMethod(String str){
        String sha256Str = DigestUtils.sha256Hex(str);
        return sha256Str;
    }

    public static String base64EncryptMethod(byte[] binaryData){
        String base64Str = Base64.encodeBase64String(binaryData);
        return base64Str;
    }
}

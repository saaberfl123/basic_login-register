package com.saber.layerd.Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class saltUtil
{
    public static String generateSalt(int length)
    {
        SecureRandom random=new SecureRandom();
        byte[] salt=new byte[length];
        random.nextBytes(salt);
        //转化为BASE64字符串
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String md5WithSalt(String password, String salt)
    {
        try
        {
            // 拼接
            String combined = password + salt;
            // 获取MD5实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算哈希（指定UTF-8防止乱码）
            byte[] digest = md.digest(combined.getBytes(StandardCharsets.UTF_8));
            // 转为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest)
            {
                sb.append(String.format("%02x", b & 0xFF));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不存在", e);
        }
    }


}

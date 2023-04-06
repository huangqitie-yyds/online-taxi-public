package com.huangqitie.internalcommon.util;

public class RedisPrefixUtils {
    //乘客验证码的前缀
    public static String verificationCodePrefix = "verification-code-";

    //Token的前缀
    public static String tokenPrefix = "token-";

    /**
     * 根据手机号，生成key
     * @param phone
     * @param identity
     * @return
     */
    public static String generatorKeyByPhone(String phone, String identity) {
        return verificationCodePrefix + identity + "-" + phone;
    }

    /**
     * 根据手机号和用户标识生成key
     *
     * @param passengerPhone
     * @param identity
     * @return
     */
    public static String generateTokenKey(String passengerPhone, String identity, String tokenType) {
        return tokenPrefix + passengerPhone + "-" + identity + "-" + tokenType;
    }

}

package cn.ctyun.core.base.utils;

import java.util.regex.Pattern;

import com.google.common.base.Strings;

public class ValidationUtils {

    /**
     * 正则表达式：验证邮箱
     */
    private static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证手机号
     */
    private static final String PHONE_REGEX = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147)|(19[8-9])|(166))\\d{8}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式：验证邮编
     */
    public static final String REGEX_ZIP_CODE = "\\d{6}";
    /**
     * 校验邮箱
     * 
     * @param email
     *            邮箱
     * @return
     */
    public static boolean isEmail(final String email) {
        if (!Strings.isNullOrEmpty(email)) {
            return Pattern.matches(EMAIL_REGEX, email);
        } else {
            return false;
        }

    }

    /**
     * 校验中国手机号
     * 
     * @param phoneNumber
     *            手机号
     * @return
     */
    public static boolean isChinaPhoneNumber(final String phoneNumber) {
        if (!Strings.isNullOrEmpty(phoneNumber)) {
            return Pattern.matches(PHONE_REGEX, phoneNumber);
        } else {
            return false;
        }
    }

    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     * 
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     * 
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 验证邮编号
     * @return
     */
    public static boolean isZipCode(String zipCode) {
        return Pattern.matches(REGEX_ZIP_CODE, zipCode);
    }

    public static void main(String[] args) {
        System.out.println(ValidationUtils.isChinaPhoneNumber("18112345678"));

    }

}

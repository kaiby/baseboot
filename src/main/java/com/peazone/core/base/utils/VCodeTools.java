package com.peazone.core.base.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Strings;
import com.peazone.core.base.rest.RPCClient.RestPostClient;

public class VCodeTools {

    private static Logger logger = LogManager.getLogger(VCodeTools.class);

    private static final String regEx="[^0-9]";

    /**
     * 过期时长15分钟
     */
    private static final int EXPIRATION_QUOTA = 15;
    private static Map<String, VCodeTools> VCODE_CACHE_MAP = new HashMap<String, VCodeTools>();

    private String businessCode;
    private String mobilePhone;
    private String vCode;
    private Date expirationTime;

    private static String getRandomVCode() {
        String vCode = String.valueOf(new Random().nextInt(899999) + 100000);// 生成短信验证码
        return vCode;
    }

    /**
     * 获取验证码
     * 
     * @param businessCode
     *            页面编码
     * @param mobilePhone
     *            手机号
     * @param vCode
     *            验证码
     * @return 操作结果 -1--参数为空；0-- 验证码已发送；1--验证码有效；2--验证码已失效；3--验证码不匹配；99--验证码发送失败
     */
    public static int sendVerifyVCode(String businessCode, String mobilePhone, String vCode) {
        if (Strings.isNullOrEmpty(businessCode) || Strings.isNullOrEmpty(mobilePhone)) {
            return -1;
        }
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, EXPIRATION_QUOTA);
        String key = businessCode + mobilePhone;
        if (VCodeTools.VCODE_CACHE_MAP.containsKey(key)) {
            VCodeTools vCodeItem = VCodeTools.VCODE_CACHE_MAP.get(key);
            if (vCodeItem.getExpirationTime().compareTo(new Date()) <= 0) {
                if (Strings.isNullOrEmpty(vCode)) {
                    VCodeTools.VCODE_CACHE_MAP.remove(key);
                }
                return 2;
            } else {
                if (vCodeItem.getVCode().equals(vCode)) {
                    return 1;
                } else {
                    return 3;
                }

            }
        } else {
            vCode = VCodeTools.getRandomVCode();
            VCodeTools vCodeTools = new VCodeTools();
            vCodeTools.setBusinessCode(businessCode);
            vCodeTools.setMobilePhone(mobilePhone);
            vCodeTools.setVCode(vCode);
            vCodeTools.setExpirationTime(c.getTime());
            VCodeTools.VCODE_CACHE_MAP.put(key, vCodeTools);
            logger.info("key: {}, vCode:{}", key, vCode);
            String smsContent = "您的验证码为:" + vCode + "，有效期" + VCodeTools.EXPIRATION_QUOTA + "分钟。";

            try {
                RestPostClient client = new RestPostClient();
                Map bodyMap = new HashMap();
                bodyMap.put("mobilePhone", mobilePhone);
                bodyMap.put("content", smsContent);
                String ip = PropertyUtils.getCoreRouterUrl();
                String uri = "/sms/SendSMSByMobile";
                String resultJson = client.callRestRPC(ip + uri, null, bodyMap);
            } catch (Exception e) {
                return 99;
            }
            return 0;
        }
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getVCode() {
        return vCode;
    }

    public void setVCode(String vCode) {
        this.vCode = vCode;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public static void main(String[] args) {
        System.out.println(VCodeTools.sendVerifyVCode("123", "123", ""));
        System.out.println(VCodeTools.sendVerifyVCode("123", "123", ""));
    }

    /**
     * 验证验证码是否正确
     * @param cache
     * @param code
     * @return
     */
    public static Boolean checkPwd(String cache, String code) {
        if (Strings.isNullOrEmpty(cache) || Strings.isNullOrEmpty(code)) {
            return false;
        }
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(cache);
        String trim = m.replaceAll("").trim();
        if (code.equals(trim)) {
            return true;
        } else {
            return false;
        }
    }

}

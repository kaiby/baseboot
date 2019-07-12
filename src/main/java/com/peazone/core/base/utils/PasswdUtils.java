//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.peazone.core.base.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class PasswdUtils {
    public static final int DEFAULT_PREFIX = 32;
    public static int prefix = 32;

    public PasswdUtils() {
    }

    public static String generate(String source) {
        StringBuilder sb = new StringBuilder();
        String md5 = DigestUtils.md5Hex(source);
        String random = RandomStringUtils.randomAlphanumeric(prefix);
        String sha = DigestUtils.sha384Hex(random + md5);
        sb.append(random).append(sha);
        return sb.toString();
    }

    public static boolean validate(String source, String passwd) {
        String md5 = DigestUtils.md5Hex(source);
        String random = passwd.substring(0, prefix);
        String sha = DigestUtils.sha384Hex(random + md5);
        return passwd.equals(random + sha);
    }
}

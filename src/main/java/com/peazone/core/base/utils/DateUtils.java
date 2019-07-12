package com.peazone.core.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 获取当前时间
 * 
 * @author zhouxb
 *
 */
public class DateUtils {

    public static final String YYYYMMddHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String YYYYMMddHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_dd_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_dd_HH_MM_SSZ = "yyyy-MM-dd HH:mm:ss'Z'";
    private static final String UTC = "UTC";

    // 取得本地时间：
    private static final Calendar cal = Calendar.getInstance();
    // 取得时间偏移量：
    private static final int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
    // 取得夏令时差：
    private static final int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

    public static long getUTCTimeStr() {
        /**
         * 从本地时间里扣除这些差量，即可以取得UTC时间：
         */
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        long mills = cal.getTimeInMillis();
        return mills;
    }

    public static String getUTCTimeStrZ() {
        cal.setTimeInMillis(getUTCTimeStr());
        SimpleDateFormat foo = new SimpleDateFormat(YYYY_MM_dd_HH_MM_SSZ);
        // 从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, (zoneOffset + dstOffset));
        return foo.format(cal.getTime());

    }

    /**
     * 本地时间转化UTC时间
     * 
     * @param millis
     * @return
     */
    public static String local2UTC(long millis) {
        cal.setTimeInMillis(millis);
        SimpleDateFormat foo = new SimpleDateFormat(YYYY_MM_dd_HH_MM_SSZ);
        // 从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return foo.format(cal.getTime());

    }

    /**
     * 本地时间转化UTC时间
     * 
     * @param date
     * @return
     */
    public static String local2UTC(Date date) {
        cal.setTimeInMillis(date.getTime());
        SimpleDateFormat foo = new SimpleDateFormat(YYYY_MM_dd_HH_MM_SSZ);
        // 从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return foo.format(cal.getTime());

    }

    /**
     * UTC转化为本地时间
     * 
     * @param utcTime
     * @param utcTimePatten
     * @param localTimePatten
     * @return
     */
    public static String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone(UTC));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

    /**
     * UTC转化为本地时间
     * 
     * @param utcTime
     * @param utcTimePatten
     * @param localTimePatten
     * @return
     */
    public static Date utc2LocalDate(String utcTime, String utcTimePatten, String localTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone(UTC));
        Date gpsUTCDate = null;
        Date loclDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        try {
            loclDate = localFormater.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return loclDate;
    }

    public static String getTime() {
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return time;
    }

    public static String getTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static long getUnixTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }

    public static Date getDate_2037_12_31() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_dd_HH_MM_SS);
        try {
            return dateFormat.parse("2037-12-31 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.getTime("MMddHHmmssSSS"));
    }
}

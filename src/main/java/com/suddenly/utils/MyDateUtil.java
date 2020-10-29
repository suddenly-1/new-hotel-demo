package com.suddenly.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtil {

    // Date转换时间戳
    // long time = new Date().getTime();

    // 时间戳转换Date
    // long time = new Date().getTime();
    // Date date = new Date(time);

    /**
     * 描述：字符串转换为Date
     * @param dateStr
     **/
    public static Date stringConvertDate(String dateStr) {
        return MyDateUtil.stringConvertDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 描述：字符串转换为Date
     * @param dateStr
     * @param partten
     **/
    public static Date stringConvertDate(String dateStr, String partten) {
        try {
            return new SimpleDateFormat(partten).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 描述：字符串转换时间戳
     * @param dateStr
     **/
    public static Long stringConvertTimestamp(String dateStr){
        return MyDateUtil.stringConvertTimestamp(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 描述：字符串转换时间戳
     * @param dateStr
     * @param partten
     **/
    public static Long stringConvertTimestamp(String dateStr, String partten){
        try {
            return new SimpleDateFormat(partten).parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 描述：Date转换字符串
     * @param date
     **/
    public static String dateConvertString(Date date) {
        return MyDateUtil.dateConvertString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 描述：Date转换字符串
     * @param date
     * @param partten
     **/
    public static String dateConvertString(Date date, String partten) {
        return new SimpleDateFormat(partten).format(date);
    }

    /**
     * 描述：时间戳转换字符串
     * @param timestamp
     **/
    public static String timestampConvertString(Long timestamp){
        return MyDateUtil.timestampConvertString(timestamp, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 描述：时间戳转换字符串
     * @param timestamp
     * @param partten
     **/
    public static String timestampConvertString(Long timestamp, String partten){
        return new SimpleDateFormat(partten).format(timestamp);
    }


    /**
     * 描述：计算时间差
     * @param startTimeStr
     * @param endTimeStr
     **/
    public static String getTimeDifference(String startTimeStr, String endTimeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        Date endTime  = null;
        try {
            startTime = format.parse(startTimeStr);
            endTime = format.parse(endTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long between = endTime.getTime() - startTime.getTime();

//        long day = between / (24 * 60 * 60 * 1000);
//        long hour = (between / (60 * 60 * 1000) - day * 24);
//        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
//        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//        String result = day + "天" + hour + "时" + min + "分" + s + "秒";

        long hour = (between / (60 * 60 * 1000));
        long min = ((between / (60 * 1000) - hour * 60));
        long s = (between / 1000 - hour * 60 * 60 - min * 60);
        String result = hour + "时" + min + "分" + s + "秒";
        return result;
    }
}

package com.weique.overhaul.v2.app.utils;

import com.weique.overhaul.v2.app.common.Constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeUtil {
    /**
     *
     */
    public static final int DATE_TYPE_YEAR = 0;

    public static Date stringToDateForFotmat(String stringDate, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(stringDate);
    }

    /**
     * string 转 long
     *
     * @param time time
     * @return long
     */
    public static long StringToLong(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constant.YMDHM, Locale.CHINA);
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * long  转 string
     *
     * @param time time
     * @return String
     */
    public static String LongToString(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat(Constant.YMDHMS, Locale.CHINA);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 比较当前时间和服务器返回时间大小
     *
     * @param nowDate
     * @param startDate
     * @return
     */
    public static int compareDate(String nowDate, String startDate, String endDate) {
        SimpleDateFormat df = new SimpleDateFormat(Constant.YMDHMS1, Locale.CHINA);

        try {
            Date now = df.parse(nowDate);
            Date startTime = df.parse(startDate);
            Date endTime = df.parse(endDate);
//            if (now.before(startTime)) {
//                return true;//未到
//            } else {
//                return false;//超期
//            }

            if (now.after(startTime) && (now.before(endTime) || now.equals(endTime))) {
                return 200;
            } else if (now.equals(startTime) && now.before(endTime)) {
                return 200;
            } else if (now.before(startTime)) {
                return 100;
            } else if (now.after(endTime)) {
                return 101;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    /**
     * 时间格式转换
     *
     * @param oldS   oldS 要转换的串
     * @param format format 新格式
     * @return
     */
    public static String changedFormat(String oldS, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        String newFormatDate = oldS;
        try {
            Date date = dateFormat.parse(oldS);
            newFormatDate = dateToStringByFormit(date, format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newFormatDate;
    }


    /**
     * 时间格式转换
     *
     * @param oldS   oldS 要转换的串
     * @param format format 新格式
     * @return
     */
    public static String changedFormat(String oldS, String format, String newFormat) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        String newFormatDate = oldS;
        try {
            Date date = dateFormat.parse(oldS);
            newFormatDate = dateToStringByFormit(date, newFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newFormatDate;
    }

    /**
     * date转String
     *
     * @param date   date
     * @param format 要转换的目标格式
     * @return String
     */
    public static String dateToStringByFormit(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    /**
     * @param type type @Calendar.HOUR_OF_DAY
     * @return 根据类型  获取当前 时间
     */
    public static String getCurrentTimeByType(int type) {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(type));
    }

    /**
     * @return 根据类型  获取当前 时间
     */
    public static String getCurrentTimeByFormit(String formit) {
        return dateToStringByFormit(new Date(), formit);
    }


    /**
     *      * 获取两个日期之间的所有日期
     *      *
     *      * @param startTime
     *      *            开始日期
     *      * @param endTime
     *      *            结束日期
     *      * @return
     *     
     */
    public static List<String> getDays(String startTime, String endTime) {

// 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd", Locale.CHINA);
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(format.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

}

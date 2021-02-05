package com.weique.overhaul.v2.app.utils;

import android.content.Context;
import android.provider.Settings;

import com.weique.overhaul.v2.app.common.BaseSingleton;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android Studio.
 * User: Mr.GK
 * Date: 2019/3/30
 * Time: 19:39
 */
public class StringUtil extends BaseSingleton {

    /**
     * 实例化
     */
    public static StringUtil getInstance() {
        return getSingleton(StringUtil.class);
    }


    /**
     * 判断字符串是 空
     *
     * @param str null、“ ”、“null”都返回true
     * @return
     */
    public static boolean isNullString(String str) {
        return (null == str || str.trim().isEmpty() || "null".equals(str.trim()
                .toLowerCase(Locale.getDefault())));
    }

    /**
     * 判断字符串不是 空
     *
     * @param str null、“ ”、“null”都返回true
     * @return
     */
    public static boolean isNotNullString(String str) {
        return !isNullString(str);
    }

    public static String formatNullString(String str) {
        return isNotNullString(str) ? str : "";
    }

    /**
     * 特殊比较字符串 ""、null、"null"
     *
     * @param lhs
     * @param rhs
     * @return
     */
    public static boolean equalSpecialStr(String lhs, String rhs) {
        try {
            if (isNullString(lhs) && isNullString(rhs)) {
                return true;
            } else {
                if (lhs.equals(rhs)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 截取数字
     *
     * @return
     */
    public static String cutNumber(String content) {
        if (isNotNullString(content)) {
            Pattern p = Pattern.compile("[^0-9]");
            Matcher m = p.matcher(content);
            return m.replaceAll("");
        } else {
            return "";
        }
    }

    public static boolean isNumeric(String str) {
        if (isNotNullString(str)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            return pattern.matcher(str).matches();
        } else {
            return false;
        }
    }

    /*
     *替换日期格式
     * 2016-05-13 --> 2016/05/13
     */
    public static String replaceDate(String Date) {
        return Date.replaceAll("-", "/");
    }


    /**
     * 时间转换
     * 900 --> 09:00
     * 2100 --> 21:00
     */
    public static String changeTime(String start_time) {
        String h;
        String m;
        if (start_time.length() < 4) {
            h = "0" + start_time.substring(0, 1);
            m = start_time.substring(1);
        } else {
            h = start_time.substring(0, 2);
            m = start_time.substring(2);
        }
        return h + ":" + m;
    }

    /**
     * 根据设备生成一个唯一标识
     *
     * @return
     */
    public static String generateOpenUDID(Context context) {
        // Try to get the ANDROID_ID
        String OpenUDID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (OpenUDID == null || OpenUDID.equals("9774d56d682e549c")
                | OpenUDID.length() < 15) {
            // if ANDROID_ID is null, or it's equals to the GalaxyTab generic
            // ANDROID_ID or bad, generates a new one
            final SecureRandom random = new SecureRandom();
            OpenUDID = new BigInteger(64, random).toString(16);
        }
        return OpenUDID;
    }

    /**
     * String 转 double 并保留2位小数
     * 6.2041   -->  6.20
     *
     * @param str
     * @return
     */
    public static String getDecimal(String str) {
        Double cny = Double.parseDouble(str);//6.2041
        DecimalFormat df = new DecimalFormat("0.00");
        String result = df.format(cny);
        return result + "";
    }

    public static String getDecimalMoney(String str) {
        if (StringUtil.isNullString(str)) {
            return null;
        }
        DecimalFormat myformat = new DecimalFormat("###,###");
        return myformat.format(Integer.valueOf(str));
    }


    public static String encryption(String defaultVal) {
        if (defaultVal.length() > 1 && defaultVal.length() < 4) {
            String substring = defaultVal.substring(0, 1);
            return substring + "***";
        } else if (defaultVal.length() >= 4) {
            String substring = defaultVal.substring(0, 3);
            return substring + "***";
        }
        return defaultVal;
    }

    public static String setText(String str) {
        if (StringUtil.isNullString(str)) {
            return "";
        }
        return str;
    }

}

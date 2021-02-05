package com.weique.overhaul.v2.app.utils;

public class StringFormatUtil {
    /**
     * 判断是否是整数或者是小数
     *
     * @param str str
     * @return true：是，false不是
     */

    public static boolean validateNumber(String str) {
        if (StringUtil.isNullString(str)) {
            return false;
        }
        // 说明一下的是该正则只能识别4位小数；如果不限制小数位数的话，写成[+-]?[0-9]+(\\.[0-9]+)?就可以了
        // [+-]?[0-9]+(\.[0-9]{1,4})?
        return str.matches("[+-]?[0-9]+(\\\\.[0-9]+)?");

    }
}

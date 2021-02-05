package com.weique.overhaul.v2.app.utils;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.common.Constant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 验签 工具
 *
 * @author GreatKing
 */
public class SignUtil {
    /**
     * 通用  添加公共参数
     *
     * @param map
     * @return
     */
    public static Map<String, Object> paramSign(Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>(10);
        }
        try {
            if (UserInfoUtil.getUserInfo() != null) {
                map.put(Constant.USER_ID, (UserInfoUtil.getUserInfo().getUid()));
                map.put(Constant.TOKEN, (UserInfoUtil.getUserInfo().getToken()));
                map.put(Constant.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
                map.put(Constant.NONCE, String.valueOf(Math.random()));
                Iterator<Map.Entry<String, Object>> entryIterator = map.entrySet().iterator();
                boolean hasDepartmentId = false;
                while (entryIterator.hasNext()) {
                    Map.Entry<String, Object> next = entryIterator.next();
                    if (next.getValue() == null) {
                        entryIterator.remove();
                    }
                    if (!hasDepartmentId && Constant.DEPARTMENT_ID.equals(next.getKey())) {
                        if (StringUtil.isNotNullString(next.getValue().toString())) {
                            hasDepartmentId = true;
                        }
                    }
                }
                if (!hasDepartmentId) {
                    map.put(Constant.DEPARTMENT_ID, (UserInfoUtil.getUserInfo().getDepartmentId()));
                }
                Map<String, Object> newMap = new TreeMap<>(map);
                String newMapString = newMap.toString().replaceAll(" ", "");
                String sign = ArmsUtils.encodeToMD5(newMapString).toUpperCase();
                newMap.put(Constant.SIGNATURE, sign);
                return newMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 版本更新使用
     *
     * @param map
     * @return
     */
    public static Map<String, String> paramSigns(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>(10);
        }
        try {
            if (UserInfoUtil.getUserInfo() != null) {
                map.put(Constant.USER_ID, (UserInfoUtil.getUserInfo().getUid()));
                map.put(Constant.TOKEN, (UserInfoUtil.getUserInfo().getToken()));
                map.put(Constant.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
                map.put(Constant.NONCE, String.valueOf(Math.random()));
                Iterator<Map.Entry<String, String>> entryIterator = map.entrySet().iterator();
                boolean hasDepartmentId = false;
                while (entryIterator.hasNext()) {
                    Map.Entry<String, String> next = entryIterator.next();
                    if (next.getValue() == null) {
                        entryIterator.remove();
                    }
                    if (hasDepartmentId == false && Constant.DEPARTMENT_ID.equals(next.getKey())) {
                        hasDepartmentId = true;
                    }
                }
                if (!hasDepartmentId) {
                    map.put(Constant.DEPARTMENT_ID, (UserInfoUtil.getUserInfo().getDepartmentId()));
                }
                Map<String, String> newMap = new TreeMap<>(map);
                String newMapString = newMap.toString().replaceAll(" ", "");
                String sign = ArmsUtils.encodeToMD5(newMapString).toUpperCase();
                newMap.put(Constant.SIGNATURE, sign);
                return newMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

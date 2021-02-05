package com.weique.overhaul.v2.app.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapUtils {
    /**
     * 实体类转Map
     *
     * @param object
     * @return
     */
    public static Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new HashMap();
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                try {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object o = field.get(object);
                    if (null == o || field.getName().equals("CREATOR") || field.getName().equals("CreateTime") || field.getName().equals("UpdateTime")) {
                        continue;
                    }
                    map.put(field.getName(), o);
                    field.setAccessible(flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 实体类转Map 保留 null
     *
     * @param object
     * @return
     */
    public static Map<String, Object> entityToMapHasNull(Object object) {
        Map<String, Object> map = new HashMap();
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                try {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object o = field.get(object);
                    if (null == o || field.getName().equals("CREATOR") || field.getName().equals("CreateTime") || field.getName().equals("UpdateTime")) {
                        continue;
                    }
                    map.put(field.getName(), o);
                    field.setAccessible(flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    //Map转Object
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                if (map.containsKey(field.getName())) {
                    field.set(obj, map.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * @param map map
     * @return Object
     * @throws Exception Exception
     */
    public static <T> T mapValueSetToObject(Map<String, Object> map, T o) throws Exception {
        try {
            if (map == null)
                return null;
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                if (map.containsKey(field.getName())) {
                    if (map.get(field.getName()) != null && StringUtil.isNotNullString(Objects.requireNonNull(map.get(field.getName())).toString())) {
                        field.set(o, map.get(field.getName()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
}

package com.weique.overhaul.v2.app.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.weique.overhaul.v2.mvp.model.entity.LoginInfoBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 登录名称管理
 */
public class LoginNamePasswordUtil {
    /**
     * 最大缓存登录名个数  暂时写  一个后面需要再 写list
     */
    private static final int MAX_NAME_NUMBER = 1;

    /**
     * 登录成功缓存的
     * 登录账号 信息
     */
    private static final String ACACHE_LOGIN_NEME_KEY = "aCache_Login_Neme_Key";
    /**
     * 获取 上次登录时间的key
     */
    private static final String LAST_LOAIN_TIME_KEY = "LAST_LOAIN_TIME";

    public List<LoginInfoBean> getAllLoginName(Context context) {
        List<LoginInfoBean> loginInfos = new ArrayList<>();
        try {
            ACache loginInfo = ACache.getLoginInfo(context);
            JSONArray array = loginInfo.getAsJSONArray(ACACHE_LOGIN_NEME_KEY);
            if (array != null && array.length() > 0) {
                for (int i = 0; i < array.length();i++){
                    LoginInfoBean bean = new Gson().fromJson(array.get(i).toString(), LoginInfoBean.class);
                    loginInfos.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return loginInfos;
        }
        return loginInfos;
    }

    /**
     * 添加登录明到缓存
     *
     * @param context       context
     * @param loginInfoBean loginInfoBean
     */
    public void addLoginName(Context context, LoginInfoBean loginInfoBean) {
        try {
            List<LoginInfoBean> allLoginName = getAllLoginName(context);
            if (allLoginName == null) {
                allLoginName = new ArrayList<>();
            } else {
                Iterator<LoginInfoBean> iterator = allLoginName.iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    i++;
                    LoginInfoBean next = iterator.next();
                    if (i >= MAX_NAME_NUMBER) {
                        iterator.remove();
                        continue;
                    }
                    if (next.getName().equals(loginInfoBean.getName())) {
                        iterator.remove();
                    }
                }
            }
            allLoginName.add(loginInfoBean);
            ACache loginInfo = ACache.getLoginInfo(context);
            loginInfo.put(ACACHE_LOGIN_NEME_KEY, allLoginName.toString());
            loginInfo.put(LAST_LOAIN_TIME_KEY, String.valueOf(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLastLoginTime(Context context) {
        ACache loginInfo = ACache.getLoginInfo(context);
        return loginInfo.getAsString(LAST_LOAIN_TIME_KEY);
    }
}

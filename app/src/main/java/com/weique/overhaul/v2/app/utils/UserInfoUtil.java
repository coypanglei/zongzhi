package com.weique.overhaul.v2.app.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;

import org.json.JSONArray;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: Mr.GK
 * Date: 2019/3/30
 * Time: 19:39
 *
 * @author GK
 */

public class UserInfoUtil {
    /**
     * 登录成功后的消息tag
     */
    public static final String TAG_MSG_LOGIN = "loginSuccess";

    /**
     * 党建中心用户uid
     */
    public static final String PARTY_USER_UID = "USER_INFO";


    /**
     * 党建中心积分存储
     */
    public static final String PARTY_USER_INTEGRAL = "INTEGRAL";

    /**
     * 党建中心 用户信息 新建
     *
     * @param globalUserInfoBean
     */
    public static void saveUserInfo(GlobalUserInfoBean globalUserInfoBean) {
        try {
            if (globalUserInfoBean == null) {
                return;
            }
//            if(StringUtil.isNullString(globalUserInfoBean.getToken())){
//                if (getUserInfo() != null && StringUtil.isNotNullString(getUserInfo().getToken())) {
//                    globalUserInfoBean.setToken(getUserInfo().getToken());
//                }
//            }
            Constant.globalUserInfoBean = globalUserInfoBean;
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            //登录成功刷新我的页面余额
            aCache.put(PARTY_USER_UID, new Gson().toJson(globalUserInfoBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 党建中心 用户信息 更新
     *
     * @param globalUserInfoBean
     */
    public static void updateUserInfo(GlobalUserInfoBean globalUserInfoBean) {
        try {
            if (globalUserInfoBean == null) {
                return;
            }
            Constant.globalUserInfoBean = globalUserInfoBean;
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            //登录成功刷新我的页面余额
            aCache.put(PARTY_USER_UID, new Gson().toJson(globalUserInfoBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户信息 更新 头像 缓存和  已有界面  头像
     *
     * @param url
     */
    public static void updateUserInfoPhoto(String url) {
        if (StringUtil.isNullString(url)) {
            return;
        }
        GlobalUserInfoBean globalUserInfoBean = null;
        try {
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            //登录成功刷新我的页面余额
            String userInfo = aCache.getAsString(PARTY_USER_UID);
            globalUserInfoBean = new Gson().fromJson(userInfo, GlobalUserInfoBean.class);
            globalUserInfoBean.setHeadUrl(url);
            Constant.globalUserInfoBean = globalUserInfoBean;
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), RouterHub.APP_MAINACTIVITY_MYFRAGMENT);
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     *
     * @return GlobalUserInfoBean
     */
    public static GlobalUserInfoBean getUserInfo() {
        if (Constant.globalUserInfoBean != null) {
            return Constant.globalUserInfoBean;
        }
        GlobalUserInfoBean globalUserInfoBean = null;
        try {
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            //登录成功刷新我的页面余额
            String userInfo = aCache.getAsString(PARTY_USER_UID);
            globalUserInfoBean = new Gson().fromJson(userInfo, GlobalUserInfoBean.class);
            Constant.globalUserInfoBean = globalUserInfoBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return globalUserInfoBean;
    }


    /**
     * 退出登录以后删除用户信息  记得添加一些 回写信息  就是推出后 还想要保留的信息
     */
    public static void loginOut(Context context) {
        Constant.globalUserInfoBean = null;
        ACache aCache = ACache.get(context);
        aCache.clear();
        //推出后要保留的数据
        aCache.put(ACacheConstant.IS_FIRST_START_APP, ACacheConstant.IS_FIRST_START_APP);
        aCache.put(ACacheConstant.AGREE_WITH_THE_PROTOCOL, ACacheConstant.AGREE_WITH_THE_PROTOCOL);
    }

    /**
     * 历史搜索key
     */
    public static final String HISTORY_SEARCH_KEY = "HISTORY_SEARCH_KEY";

    /**
     * 添加一个搜索关键字
     *
     * @param keyword keyword
     */
    public static void addSearchKeyword(String keyword) {
        try {
            if (StringUtil.isNullString(keyword)) {
                return;
            }
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            JSONArray hitoryArray = aCache.getAsJSONArray(HISTORY_SEARCH_KEY);
            if (hitoryArray == null) {
                aCache.put(HISTORY_SEARCH_KEY, new JSONArray());
            } else {
                if (hitoryArray.length() > 10) {
                    hitoryArray.remove(hitoryArray.length() - 1);
                }
                //去除相同的 字
                for (int i = 0; i < hitoryArray.length(); i++) {
                    if (keyword.equals(hitoryArray.get(i))) {
                        hitoryArray.remove(i);
                        break;
                    }
                }
                hitoryArray.put(keyword);
            }
            aCache.put(HISTORY_SEARCH_KEY, hitoryArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanSearchKeyword() {
        try {
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            aCache.remove(HISTORY_SEARCH_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getSearchKeywordList() {
        List<String> list = new ArrayList<>();
        try {
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            JSONArray asJSONArray = aCache.getAsJSONArray(HISTORY_SEARCH_KEY);
            if (asJSONArray == null) {
                aCache.put(HISTORY_SEARCH_KEY, new JSONArray());
            }
            if (asJSONArray != null) {
                //去除相同的 字
                for (int i = asJSONArray.length() - 1; i >= 0; i--) {
                    list.add((String) asJSONArray.get(i));
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 快捷输入
     */
    public static final String SHORTCUT = "SHORTCUT";

    /**
     * 添加快捷输入
     *
     * @param keyword keyword
     */
    public static void addShortcut(String keyword) {
        try {
            if (StringUtil.isNullString(keyword)) {
                return;
            }
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            JSONArray shortcutArray = aCache.getAsJSONArray(SHORTCUT);
            if (shortcutArray == null) {
                aCache.put(SHORTCUT, new JSONArray());
            } else {
                if (shortcutArray.length() > 10) {
                    shortcutArray.remove(shortcutArray.length() - 1);
                }
                //去除相同的 字
                for (int i = 0; i < shortcutArray.length(); i++) {
                    if (keyword.equals(shortcutArray.get(i))) {
                        shortcutArray.remove(i);
                        break;
                    }
                }
                shortcutArray.put(keyword);
            }
            aCache.put(SHORTCUT, shortcutArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanShortcut() {
        try {
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            aCache.remove(SHORTCUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getShortcut() {
        List<String> list = new ArrayList<>();
        try {
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            JSONArray asJSONArray = aCache.getAsJSONArray(SHORTCUT);
            if (asJSONArray == null) {
                aCache.put(SHORTCUT, new JSONArray());
            }
            if (asJSONArray != null) {
                //去除相同的 字
                for (int i = asJSONArray.length() - 1; i >= 0; i--) {
                    list.add((String) asJSONArray.get(i));
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 党建中心 用户信息 读取
     *
     * @return
     */
    public static String getTodayAddIntegral() {
        String integral = "0";
        try {
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            //登录成功刷新我的页面余额
            integral = aCache.getAsString(PARTY_USER_INTEGRAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return integral;
    }

    /**
     * 党建中心 用户信息 保存用户今日积分
     *
     * @param integral
     */
    public static void saveTodayAddIntegral(int integral) {
        try {
            ACache aCache = ACache.get(AppManager.getAppManager().getmApplication());
            //登录成功刷新我的页面余额
            aCache.put(PARTY_USER_INTEGRAL, integral + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUserInfoName(String name) {
        if (StringUtil.isNullString(name)) {
            return;
        }
        try {
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE, "", name), RouterHub.APP_MAINACTIVITY_MYFRAGMENT);
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE, "", name), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

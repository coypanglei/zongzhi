package com.weique.overhaul.v2.app.utils;

import android.view.View;

import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

import java.util.Iterator;
import java.util.Map;

/**
 * todo 后面 所有的界面 权限控制  尽量全部 在这里控制
 * <p>
 * 针对权限等级控制 界面显示问题
 *
 * @author GreatKing
 */
public class AccessControlUtil {


    /**
     * 根据  用户 level 控制  为  网格时  显示的布局
     * 网格员可见
     */
    public static void controlByLevelGrid(View... view) {
        try {
            for (View vv : view) {
                if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.GRIDDING) {
                    vv.setVisibility(View.VISIBLE);
                } else {
                    vv.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据  用户 level 控制  为  社区时  显示的布局
     * 社区可见
     */
    public static void controlByLevelCommunity(View... view) {
        try {
            for (View vv : view) {
                if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.COMMUNITY) {
                    vv.setVisibility(View.VISIBLE);
                } else {
                    vv.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据  用户 level 控制  为  社区时  显示的布局
     * 街道可见
     */
    public static void controlByLevelStreet(View... view) {
        try {
            for (View vv : view) {
                if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.STREET) {
                    vv.setVisibility(View.VISIBLE);
                } else {
                    vv.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单个
     * 根据  根据 activity 名称  比对 首页 menu 菜单 控制 view  是否显示
     *
     * @param aClass activityName
     * @param view   view
     */
    public static void fromActivityNameComparisonMenuControlView(Class aClass, View view) {
        try {
            String menuString = ACache.get(AppManager.getAppManager().getmApplication()).getAsString(ACacheConstant.MENU_DATA);
            if (StringUtil.isNullString(menuString)) {
                view.setVisibility(View.GONE);
            } else {
                if (menuString.contains(aClass.getSimpleName())) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断有没有访问  Class 的权限
     * 根据  根据 activity 名称  比对 首页 menu 菜单 控制 view  是否显示
     *
     * @param aClass activityName
     */
    public static boolean fromActivityNameComparisonMenuControlView(Class aClass) {
        try {
            String menuString = ACache.get(AppManager.getAppManager().getmApplication()).getAsString(ACacheConstant.MENU_DATA);
            if (StringUtil.isNullString(menuString)) {
                return false;
            } else {
                if (menuString.contains(aClass.getSimpleName())) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 多个
     * 根据  根据 activity 名称  比对 首页 menu 菜单 控制 view  是否显示
     *
     * @param viewMap activity 名称  和其对应的是否显示的view
     */
    public static void fromActivityNameComparisonMenuControlView(Map<String, View> viewMap) {
        try {
            Iterator<String> iterator = viewMap.keySet().iterator();
            String menuString = ACache.get(AppManager.getAppManager().getmApplication()).getAsString(ACacheConstant.MENU_DATA);
            if (StringUtil.isNullString(menuString)) {
                while (iterator.hasNext()) {
                    String activityName = iterator.next();
                    viewMap.get(activityName).setVisibility(View.GONE);
                }
            } else {
                while (iterator.hasNext()) {
                    String activityName = iterator.next();
                    if (menuString.contains(activityName)) {
                        viewMap.get(activityName).setVisibility(View.VISIBLE);
                    } else {
                        viewMap.get(activityName).setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

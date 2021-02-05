package com.weique.overhaul.v2.app.utils;

import com.blankj.utilcode.util.MetaDataUtils;

/**
 * 渠道信息获取方法
 *
 * @author wyj
 * Date:2019-05-06
 */
public class MetaDataUtil {
    /**
     * 渠道信息
     *
     * @return
     */
    public static String getChannelName() {
        return MetaDataUtils.getMetaDataInApp("channelName");
    }

    /**
     * 获取应用名称
     *
     * 
     * @return
     */
    public static String getAppName() {
        return MetaDataUtils.getMetaDataInApp("appName");
    }

    /**
     * 获取应用闪屏页 版权
     *
     * 
     * @return
     */
    public static String getCopyright() {
        return MetaDataUtils.getMetaDataInApp("copyright");
    }

    /**
     * 是否显示用户协议
     *
     * 
     * @return
     */
    public static boolean isChinaElectronics() {
        return  Boolean.parseBoolean(MetaDataUtils.getMetaDataInApp("isChinaElectronics"));
    }

    /**
     * 闪频也icon
     *
     * 
     * @return
     */
    public static int getSplashIcon() {
        return  Integer.parseInt(MetaDataUtils.getMetaDataInApp("splashIcon"));
    }

    /**
     * 覆盖范围
     *
     * @param 
     * @return
     */
    public static String getCoverage() {
        return MetaDataUtils.getMetaDataInApp("coverage");
    }
}

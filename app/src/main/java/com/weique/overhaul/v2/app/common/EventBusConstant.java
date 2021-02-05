package com.weique.overhaul.v2.app.common;

/**
 * Eventbus  和 ARouer  的常量值
 *
 * @author GreatKing
 */
public class EventBusConstant {
    /**
     * 新建信息
     */
    public static final int ADD = 0;
    /**
     * 修改信息
     */
    public static final int ALERT = 1;
    /**
     * 删除
     */
    public static final int DELETE = 2;
    /**
     * 查询
     */
    public static final int SELECT = 3;
    /**
     * 是否有新消息
     */
    public static final int HAS_NEW_MESSAGE = 4;
    /**
     * 每次查看新消息 都去 请求一次  是否还有新消息
     */
    public static final int REQUEST_AGAIN = 5;
    /**
     * 下载进度
     */
    public static final int DOWNLOAD_PREGRESS = 6;
    public static final int DOWNLOAD_PREGRESS_OK = 7;
    /**
     * 更新头像
     */
    public static final int UPDATE_HEAD_PHOTO = 8;
    /**
     * 更新名称
     */
    public static final int UPDATE_HEAD_NAME = 14;

    /**
     * 获取消息相关信息
     */
    public static final int GET_NOTICE = 15;


    /**
     * 刷新首页
     */
    public static final int IS_REFRESH = 9;
    /**
     * 刷新首页
     */
    public static final int SET_SCAN_ICON_STATUS = 10;
    /**
     * 通用更新
     */
    public static final int COMMON_UPDATE = 11;
    /**
     * 通用跳转
     */
    public static final int COMMON_JUMP = 12;
    /**
     * 更新   百度自定位数据
     */
    public static final int SELF_LOCALIZATION = 13;

    /**
     * 更新 上级地址
     */
    public static final int UPDATE_UP_ADDRESS = 100;
    /**
     * 更新坐标
     */
    public static final int UPDATE_UP_LOCATION = 200;
    /**
     * 更新事件分类
     */
    public static final int UPDATE_UP_EVENT_SORT = 300;
    /**
     * 更新上一个界面
     */
    public static final int UPDATE_UP_ACTIVITY = 301;

    /**
     * APP在后台
     */
    public static final int ISRUNNINGFOREGROUND_NO = 101;

    /**
     * app在前台
     */
    public static final int ISRUNNINGFOREGROUND_YES = 102;


    /**
     *  搜索的key
     */
    public static final  int SEARCH_KEY= 17;


}

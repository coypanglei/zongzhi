package com.weique.overhaul.v2.mvp.model.entity;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LawWorksOrderStatusBean {
    /**
     * 全部状态
     */
    public static final int ALL = -1;
    /**
     * 已暂存
     */
    private static final int SAVE = 0;
    /**
     * 已退回
     */
    private static final int BACK = 1;
    /**
     * 已提交 待审核
     */
    private static final int SUBMIT = 2;
    /**
     * 已审核 待接单
     */
    public static final int AUDIT = 3;
    /**
     * 已结单 待反馈
     */
    public static final int ORDER = 4;
    /**
     * 已反馈待评价
     */
    public static final int FEEDBACK = 5;
    /**
     * 已评价带结算
     */
    public static final int EVALUATE = 6;
    /**
     * 已结算 结束订单
     */
    public static final int BALANCE = 7;

    /**
     * 从 接单开始到 结算
     */
    public static int APP_ORDER_FLOW[] = {AUDIT, ORDER, FEEDBACK, EVALUATE, BALANCE};
    /**
     * 订单筛选条件
     */
    public static int APP_ORDER_SCREEN[] = {ALL, AUDIT, ORDER, FEEDBACK, EVALUATE, BALANCE};

    @IntDef(value = {ALL, AUDIT, ORDER, FEEDBACK, EVALUATE, BALANCE})
    @Retention(RetentionPolicy.SOURCE)//源码级别
    private @interface LawWorksOrderStatusEnum {

    }

    /**
     * 获取汽车类型
     *
     * @param status status
     * @return
     */
    public static String getStatusStrByStatus(@LawWorksOrderStatusEnum int status) {
        String statusStr = "";
        switch (status) {
            case ALL:
                statusStr = "全部";
                break;
            case AUDIT:
                statusStr = "待接单";
                break;
            case ORDER:
                statusStr = "待反馈";
                break;
            case FEEDBACK:
                statusStr = "待评价";
                break;
            case EVALUATE:
                statusStr = "待结算";
                break;
            case BALANCE:
                statusStr = "已结算";
                break;
            default:
        }
        return statusStr;
    }
}

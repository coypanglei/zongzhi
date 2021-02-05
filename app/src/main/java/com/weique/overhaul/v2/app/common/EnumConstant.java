package com.weique.overhaul.v2.app.common;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 枚举
 *
 * @author Administrator
 */
public class EnumConstant {
    /**
     * 获取动态表单enum
     */
    @IntDef({DynamicBeanByType.COLLECT, DynamicBeanByType.EVENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DynamicBeanByType {
        /**
         * 信息采集标识
         */
        int COLLECT = 0;
        /**
         * 事件上报标识
         */
        int EVENT = 1;
    }


    /**
     * 经济管理选项
     */
    @IntDef({EconomicManagementEnum.PM_MANAGE_BASE,
            EconomicManagementEnum.PM_MANAGE_PROGRESS,
            EconomicManagementEnum.PM_MANAGE_INSPECT_STATE,
            EconomicManagementEnum.FIRM_MANAGE_BASE,
            EconomicManagementEnum.FIRM_MANAGE_DEMAND,
            EconomicManagementEnum.FIRM_MANAGE_APPROVE,
            EconomicManagementEnum.FIRM_MANAGE_LAW,
            EconomicManagementEnum.FIRM_MANAGE_ECONOMICS,
            EconomicManagementEnum.PROJECT_INFO_BASE,
            EconomicManagementEnum.FIRM_INFO_BASE,
            EconomicManagementEnum.DETAILS_OF_THE_MATTER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface EconomicManagementEnum {
        /**
         * 项目管理基础信息
         */
        int PM_MANAGE_BASE = 4;
        /**
         * 项目进展
         */
        int PM_MANAGE_PROGRESS = 5;
        /**
         * 项目进展
         */
        int PM_MANAGE_INSPECT_STATE = 6;

        /**
         * 企业管理基本信息
         */
        int FIRM_MANAGE_BASE = 7;
        /**
         * 需求
         */
        int FIRM_MANAGE_DEMAND = 0;
        /**
         * 审批
         */
        int FIRM_MANAGE_APPROVE = 1;
        /**
         * 执法
         */
        int FIRM_MANAGE_LAW = 2;
        /**
         * 经济
         */
        int FIRM_MANAGE_ECONOMICS = 3;


        int PROJECT_INFO_BASE = 1001;

        int FIRM_INFO_BASE = 1002;

        /**
         * 事项详情
         */
        int DETAILS_OF_THE_MATTER = 1003;
    }

}

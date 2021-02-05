package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class EnterpriseIssueListBean {

    /**
     * pageCount : 1
     * list : [{"企业名称":"卖鸡公司","包挂领导":null,"问题类型":0,"问题内容":"测试","创建时间":"2020-03-11 17:05","问题回复状态":"提交"}]
     */

    private int pageCount;
    private List<ListBean> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * 企业名称 : 卖鸡公司
         * 包挂领导 : null
         * 问题类型 : 0
         * 问题内容 : 测试
         * 创建时间 : 2020-03-11 17:05
         * 问题回复状态 : 提交
         */

        private String Id;
        private String 企业名称;
        private String 包挂领导;
        private String 问题类型;
        private String 问题内容;
        private String 创建时间;
        private String 问题回复状态;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String get企业名称() {
            return 企业名称;
        }

        public void set企业名称(String 企业名称) {
            this.企业名称 = 企业名称;
        }

        public Object get包挂领导() {
            return 包挂领导;
        }

        public void set包挂领导(String 包挂领导) {
            this.包挂领导 = 包挂领导;
        }

        public String get问题类型() {
            return 问题类型;
        }

        public void set问题类型(String 问题类型) {
            this.问题类型 = 问题类型;
        }

        public String get问题内容() {
            return 问题内容;
        }

        public void set问题内容(String 问题内容) {
            this.问题内容 = 问题内容;
        }

        public String get创建时间() {
            return 创建时间;
        }

        public void set创建时间(String 创建时间) {
            this.创建时间 = 创建时间;
        }

        public String get问题回复状态() {
            return 问题回复状态;
        }

        public void set问题回复状态(String 问题回复状态) {
            this.问题回复状态 = 问题回复状态;
        }
    }
}

package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class BusinessQuestion {


    /**
     * pageCount : 5
     * list : [{"Id":"00795aa1-2376-4aaa-9021-c0a502e6823a","企业名称":"卖鸡公司","企业地址":"世界名车馆","企业法人":"","企业联系电话":"12312312","问题类型":"企业用工","问题内容":"比较经济基础","类型":1,"类型字符串":"提交","企业提交问题时间":"2020-03-11 21:11"},{"Id":"48376647-4809-49c5-bdfd-13ef39b4c892","企业名称":"卖鸡公司","企业地址":"世界名车馆","企业法人":"王伟","企业联系电话":"12312312","问题类型":"企业用工","问题内容":"滴滴打车","类型":1,"类型字符串":"提交","企业提交问题时间":"2020-03-11 21:30"},{"Id":"af345a8b-5e72-4032-a4b8-6d5109f101d9","企业名称":"卖鸡公司","企业地址":"世界名车馆","企业法人":"王伟","企业联系电话":"12312312","问题类型":"物流运输","问题内容":"头发长谢谢","类型":1,"类型字符串":"提交","企业提交问题时间":"2020-03-11 21:35"},{"Id":"4447ec33-89c2-400b-91e0-812918937cc6","企业名称":"卖鸡公司","企业地址":"世界名车馆","企业法人":"王伟","企业联系电话":"12312312","问题类型":"企业用工","问题内容":"11111111","类型":1,"类型字符串":"提交","企业提交问题时间":"2020-03-12 10:46"},{"Id":"06c7b274-598e-458d-815d-ab79e8f44232","企业名称":"卖鸡公司","企业地址":"世界名车馆","企业法人":"王伟","企业联系电话":"12312312","问题类型":"疫情防控","问题内容":"测试","类型":2,"类型字符串":"回复","企业提交问题时间":"2020-03-11 17:05"}]
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
         * Id : 00795aa1-2376-4aaa-9021-c0a502e6823a
         * 企业名称 : 卖鸡公司
         * 企业地址 : 世界名车馆
         * 企业法人 :
         * 企业联系电话 : 12312312
         * 问题类型 : 企业用工
         * 问题内容 : 比较经济基础
         * 类型 : 1
         * 类型字符串 : 提交
         * 企业提交问题时间 : 2020-03-11 21:11
         */

        private String Id;
        private String 企业名称;
        private String 企业地址;
        private String 企业法人;
        private String 企业联系电话;
        private String 问题类型;
        private String 问题内容;
        private int 类型;
        private String 类型字符串;
        private String 企业提交问题时间;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String get企业名称() {
            return 企业名称;
        }

        public void set企业名称(String 企业名称) {
            this.企业名称 = 企业名称;
        }

        public String get企业地址() {
            return 企业地址;
        }

        public void set企业地址(String 企业地址) {
            this.企业地址 = 企业地址;
        }

        public String get企业法人() {
            return 企业法人;
        }

        public void set企业法人(String 企业法人) {
            this.企业法人 = 企业法人;
        }

        public String get企业联系电话() {
            return 企业联系电话;
        }

        public void set企业联系电话(String 企业联系电话) {
            this.企业联系电话 = 企业联系电话;
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

        public int get类型() {
            return 类型;
        }

        public void set类型(int 类型) {
            this.类型 = 类型;
        }

        public String get类型字符串() {
            return 类型字符串;
        }

        public void set类型字符串(String 类型字符串) {
            this.类型字符串 = 类型字符串;
        }

        public String get企业提交问题时间() {
            return 企业提交问题时间;
        }

        public void set企业提交问题时间(String 企业提交问题时间) {
            this.企业提交问题时间 = 企业提交问题时间;
        }
    }
}

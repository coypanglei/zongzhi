package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class BusinessInterViewListBean {


    /**
     * pageCount : 3.386801774577965E7
     * list : [{"最后更新时间":"deserunt ut m","走访日期":"elit dolor","问题和困难":null,"企业名称":"ipsum reprehenderit","Id":"amet reprehenderit irure ipsum laborum","领导姓名":"occaecat irure culpa","图片地址集合":null,"企业意见和建议":null},{"最后更新时间":"reprehenderit qui sed","走访日期":"cillum ipsum","问题和困难":null,"企业名称":"irure","Id":"in id","领导姓名":"dolore mollit","图片地址集合":null,"企业意见和建议":null},{"最后更新时间":"laboris deserunt culpa","走访日期":"reprehenderit velit","问题和困难":null,"企业名称":"voluptate","Id":"nulla dolor","领导姓名":"est id anim Lorem","图片地址集合":null,"企业意见和建议":null},{"最后更新时间":"in ut quis","走访日期":"nisi in ullamco et","问题和困难":null,"企业名称":"fugiat Ut anim","Id":"tem","领导姓名":"eu aliquip","图片地址集合":null,"企业意见和建议":null},{"最后更新时间":"enim","走访日期":"eiusmod","问题和困难":null,"企业名称":"amet est sit voluptate","Id":"sed laborum quis fugiat in","领导姓名":"amet exercit","图片地址集合":null,"企业意见和建议":null}]
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
         * 最后更新时间 : deserunt ut m
         * 走访日期 : elit dolor
         * 问题和困难 : null
         * 企业名称 : ipsum reprehenderit
         * Id : amet reprehenderit irure ipsum laborum
         * 领导姓名 : occaecat irure culpa
         * 图片地址集合 : null
         * 企业意见和建议 : null
         */

        private String 最后更新时间;
        private String 走访日期;
        private String 问题和困难;
        private String 企业名称;
        private String Id;
        private String 领导姓名;
        private String 图片地址集合;
        private String 企业意见和建议;

        public String get最后更新时间() {
            return 最后更新时间;
        }

        public void set最后更新时间(String 最后更新时间) {
            this.最后更新时间 = 最后更新时间;
        }

        public String get走访日期() {
            return 走访日期;
        }

        public void set走访日期(String 走访日期) {
            this.走访日期 = 走访日期;
        }

        public String get问题和困难() {
            return 问题和困难;
        }

        public void set问题和困难(String 问题和困难) {
            this.问题和困难 = 问题和困难;
        }

        public String get企业名称() {
            return 企业名称;
        }

        public void set企业名称(String 企业名称) {
            this.企业名称 = 企业名称;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String get领导姓名() {
            return 领导姓名;
        }

        public void set领导姓名(String 领导姓名) {
            this.领导姓名 = 领导姓名;
        }

        public String get图片地址集合() {
            return 图片地址集合;
        }

        public void set图片地址集合(String 图片地址集合) {
            this.图片地址集合 = 图片地址集合;
        }

        public String get企业意见和建议() {
            return 企业意见和建议;
        }

        public void set企业意见和建议(String 企业意见和建议) {
            this.企业意见和建议 = 企业意见和建议;
        }
    }
}

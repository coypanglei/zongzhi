package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class PartyAnswerItemBean {


    /**
     * pageCount : 1
     * subject : [{"Id":"afd0174b-f54c-4b18-a292-9f310165164c","Title":"一年级加减法","Point":5,"CreateTime":"2019/11/15","Type":"已完成"},{"Id":"f3f2c7ff-4d48-4ef5-9494-9d478691990f","Title":"测试MP4","Point":5,"CreateTime":"2019/11/19","Type":"已完成"},{"Id":"4f54a7db-e927-4eaf-abb5-f7ef28318d07","Title":"一年级乘法","Point":5,"CreateTime":"2019/11/18","Type":"已完成"}]
     */

    private int pageCount;
    private List<SubjectBean> subject;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<SubjectBean> getSubject() {
        return subject;
    }

    public void setSubject(List<SubjectBean> subject) {
        this.subject = subject;
    }

    public static class SubjectBean {
        /**
         * Id : afd0174b-f54c-4b18-a292-9f310165164c
         * Title : 一年级加减法
         * Point : 5
         * CreateTime : 2019/11/15
         * Type : 已完成
         */

        private String Id;
        private String Title;
        private int Point;
        private String CreateTime;
        private String Type;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public int getPoint() {
            return Point;
        }

        public void setPoint(int Point) {
            this.Point = Point;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }
    }
}

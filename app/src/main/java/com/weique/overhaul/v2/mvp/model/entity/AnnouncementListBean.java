package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class AnnouncementListBean {

    /**
     * pageCount : 1
     * list : [{"Id":"ec599657-6ac8-408c-bafb-59228af93d4e","Title":"通知公告4","EnumNoticeLevel":0,"Summary":"通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4","CreateTime":"2020/01/10"},{"Id":"40a074b3-f746-41f8-839e-676559662ae1","Title":"通知公告3","EnumNoticeLevel":0,"Summary":"通知公告3通知公告3通知公告3通知公告3通知公告3","CreateTime":"2020/01/10"},{"Id":"f31520fe-d211-479c-b348-210ccf82d31f","Title":"通知公告2","EnumNoticeLevel":0,"Summary":"通知公告2通知公告2通知公告2通知公告2","CreateTime":"2020/01/10"},{"Id":"a2664d78-d76e-433a-ba11-4a49101e29f1","Title":"通知公告1","EnumNoticeLevel":0,"Summary":"通知公告1","CreateTime":"2020/01/10"}]
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
         * Id : ec599657-6ac8-408c-bafb-59228af93d4e
         * Title : 通知公告4
         * EnumNoticeLevel : 0
         * Summary : 通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4通知公告4
         * CreateTime : 2020/01/10
         */

        private String Id;
        private String Title;
        private int EnumNoticeLevel;
        private String Summary;
        private String CreateTime;
        private String Content;

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

        public int getEnumNoticeLevel() {
            return EnumNoticeLevel;
        }

        public void setEnumNoticeLevel(int EnumNoticeLevel) {
            this.EnumNoticeLevel = EnumNoticeLevel;
        }

        public String getSummary() {
            return Summary;
        }

        public void setSummary(String Summary) {
            this.Summary = Summary;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }
    }
}

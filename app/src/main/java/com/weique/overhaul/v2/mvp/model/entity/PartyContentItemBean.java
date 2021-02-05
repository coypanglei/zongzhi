package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

/**
 * @author  GK
 */
public class PartyContentItemBean {


    private int PageCount;
    private List<ListBean> list;

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * Id : efe9ba20-8e2c-45bd-a6c9-370c3ec0d665
         * Title : 2019-11-08
         * Author : 测试管理员
         * ReleaseTime : 2019/11/08
         * CoverPicturePath : /img/nonePic.jpg
         */

        private String Id;
        private String Title;
        private String Author;
        private String ReleaseTime;
        private String CoverPicturePath;

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

        public String getAuthor() {
            return Author;
        }

        public void setAuthor(String Author) {
            this.Author = Author;
        }

        public String getReleaseTime() {
            return ReleaseTime;
        }

        public void setReleaseTime(String ReleaseTime) {
            this.ReleaseTime = ReleaseTime;
        }

        public String getCoverPicturePath() {
            return CoverPicturePath;
        }

        public void setCoverPicturePath(String CoverPicturePath) {
            this.CoverPicturePath = CoverPicturePath;
        }
    }
}

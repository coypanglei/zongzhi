package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class PartyRecommendedItemBean {

    private List<TypesBean> types;
    private List<NewsBean> news;
    private List<SubsBean> subs;
    private List<NotificationsBean> notifications;

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public List<SubsBean> getSubs() {
        return subs;
    }

    public void setSubs(List<SubsBean> subs) {
        this.subs = subs;
    }

    public List<NotificationsBean> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationsBean> notifications) {
        this.notifications = notifications;
    }

    public static class TypesBean {
        /**
         * Id : 98395ffb-eb8c-4c06-8ec5-0f6c50555bde
         * Name : 类型7
         */

        private String Id;
        private String Name;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }

    public static class NewsBean {
        /**
         * Id : 6aeb0d1d-7da1-4ce9-8ec4-3ce5c2e8a501
         * Title : 2019-11-06
         * Author : 测试管理员
         * Release : 2019/11/06
         * CoverPicturePath : /img/nonePic.jpg
         */

        private String Id;
        private String Title;
        private String Author;
        private String Release;
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

        public String getRelease() {
            return Release;
        }

        public void setRelease(String Release) {
            this.Release = Release;
        }

        public String getCoverPicturePath() {
            return CoverPicturePath;
        }

        public void setCoverPicturePath(String CoverPicturePath) {
            this.CoverPicturePath = CoverPicturePath;
        }
    }

    public static class SubsBean {
        /**
         * Id : fad44034-69ca-41d9-8e4b-70d860986789
         * Title : 12312
         * Author : 大苏打
         * Release : 2019/11/06
         * CoverPicturePath : /Uploads/CustomerData/图片/file_1573005424000637086310249421153.png
         */

        private String Id;
        private String Title;
        private String Author;
        private String Release;
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

        public String getRelease() {
            return Release;
        }

        public void setRelease(String Release) {
            this.Release = Release;
        }

        public String getCoverPicturePath() {
            return CoverPicturePath;
        }

        public void setCoverPicturePath(String CoverPicturePath) {
            this.CoverPicturePath = CoverPicturePath;
        }
    }

    public static class NotificationsBean {
        /**
         * Id : ff671e2d-9580-441a-8248-ceb87ee38511
         * Title : 这是个通知
         * Author : 测试管理员
         * Release : 2019/11/06
         * CoverPicturePath : /img/nonePic.jpg
         */

        private String Id;
        private String Title;
        private String Author;
        private String Release;
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

        public String getRelease() {
            return Release;
        }

        public void setRelease(String Release) {
            this.Release = Release;
        }

        public String getCoverPicturePath() {
            return CoverPicturePath;
        }

        public void setCoverPicturePath(String CoverPicturePath) {
            this.CoverPicturePath = CoverPicturePath;
        }
    }
}

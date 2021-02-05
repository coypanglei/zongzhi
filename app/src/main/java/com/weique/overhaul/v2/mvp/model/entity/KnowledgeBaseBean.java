package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class KnowledgeBaseBean {


    /**
     * list : [{"Id":"f6191b1c-9160-4cad-b75f-768312fb78d2","Title":"哈哈哈哈","Labels":["嗷嗷嗷"],"TypeName":"法律法规","Content":"<p>233333333333333<img src=\"http://192.168.0.160/Uploads/CustomerData/image/20191226/6371295799057177032303873.png\" title=\"微信图片_20191124145404.png\" alt=\"微信图片_20191124145404.png\"/><img src=\"http://192.168.0.160/Uploads/CustomerData/image/20191226/6371295800495188361078896.png\" title=\"微信图片_20191124145404.png\" alt=\"微信图片_20191124145404.png\"/><\/p>"}]
     * pageCount : 1
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
         * Id : f6191b1c-9160-4cad-b75f-768312fb78d2
         * Title : 哈哈哈哈
         * Labels : ["嗷嗷嗷"]
         * TypeName : 法律法规
         * Content : <p>233333333333333<img src="http://192.168.0.160/Uploads/CustomerData/image/20191226/6371295799057177032303873.png" title="微信图片_20191124145404.png" alt="微信图片_20191124145404.png"/><img src="http://192.168.0.160/Uploads/CustomerData/image/20191226/6371295800495188361078896.png" title="微信图片_20191124145404.png" alt="微信图片_20191124145404.png"/></p>
         */

        private String Id;
        private String Title;
        private String TypeName;
        private String Content;
        private List<String> Labels;

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

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public List<String> getLabels() {
            return Labels;
        }

        public void setLabels(List<String> Labels) {
            this.Labels = Labels;
        }
    }
}

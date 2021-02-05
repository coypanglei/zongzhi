package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class BusinessInformation {


    /**
     * pageCount : 1
     * list : [{"Id":"1f56bc4e-385e-4055-a6fe-7b8ac398f604","Name":"猪头店","FullPath":"徐州市鼓楼区/铜沛街道办事处/植物园社区/御林华府网格","LegalPerson":"123","Tel":"213","Address":"123"}]
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
         * Id : 1f56bc4e-385e-4055-a6fe-7b8ac398f604
         * Name : 猪头店
         * FullPath : 徐州市鼓楼区/铜沛街道办事处/植物园社区/御林华府网格
         * LegalPerson : 123
         * Tel : 213
         * Address : 123
         */

        private String Id;
        private String Name;
        private String FullPath;
        private String LegalPerson;
        private String Tel;
        private String Address;
        private int Number;

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

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public String getLegalPerson() {
            return LegalPerson;
        }

        public void setLegalPerson(String LegalPerson) {
            this.LegalPerson = LegalPerson;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public int getNumber() {
            return Number;
        }

        public void setNumber(int number) {
            Number = number;
        }
    }
}

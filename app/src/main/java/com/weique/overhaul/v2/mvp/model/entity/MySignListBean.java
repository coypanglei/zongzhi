package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class MySignListBean {


    /**
     * pageCount : 1
     * list : [{"Id":"891777e3-eb69-40aa-b8d7-0c3a4e44fa1e","CreateTime":"2019-12-24 15:58","Address":"中国江苏省徐州市鼓楼区环城街道殷庄路辅路","PointsInJson":"{\"lat\":34.300182,\"lng\":117.213986}"},{"Id":"f5033917-6336-41f8-8200-7af4e16595f0","CreateTime":"2019-12-24 15:45","Address":"中国江苏省徐州市鼓楼区环城街道殷庄路辅路","PointsInJson":"{\"lat\":34.300129,\"lng\":117.214432}"},{"Id":"42224562-1bff-4619-8c74-f2762a6b160b","CreateTime":"2019-12-24 15:57","Address":"中国江苏省徐州市鼓楼区环城街道殷庄路辅路","PointsInJson":"{\"lat\":34.300182,\"lng\":117.213986}"}]
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
         * Id : 891777e3-eb69-40aa-b8d7-0c3a4e44fa1e
         * CreateTime : 2019-12-24 15:58
         * Address : 中国江苏省徐州市鼓楼区环城街道殷庄路辅路
         * PointsInJson : {"lat":34.300182,"lng":117.213986}
         */

        private String Id;
        private String CreateTime;
        private String Address;
        private String PointsInJson;
        private String Name;
        //0 签到  1签退
        private int EnumCheckInStatus;
        private boolean IsInGrid;

        public int getEnumCheckInStatus() {
            return EnumCheckInStatus;
        }

        public void setEnumCheckInStatus(int enumCheckInStatus) {
            EnumCheckInStatus = enumCheckInStatus;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getPointsInJson() {
            return PointsInJson;
        }

        public void setPointsInJson(String PointsInJson) {
            this.PointsInJson = PointsInJson;
        }

        public boolean isInGrid() {
            return IsInGrid;
        }

        public void setInGrid(boolean inGrid) {
            IsInGrid = inGrid;
        }
    }
}

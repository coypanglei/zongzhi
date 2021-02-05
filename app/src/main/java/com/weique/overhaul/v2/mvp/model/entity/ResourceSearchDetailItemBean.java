package com.weique.overhaul.v2.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class ResourceSearchDetailItemBean {


    /**
     * pageCount : 1
     * list : [{"Id":"b19fa3ec-c192-45dd-912b-867670872616","DepartmentId":"91c0b3e2-f0f0-4d85-8ecf-ff6cfadd6a03","IVImageUrlsInJson":"[\"http://192.168.0.160/Uploads/CustomerData/image/file_1575435726000637110613262479119.jpg\"]","ElementTypeId":"96c755aa-609c-4b10-b750-9df67a66b045","PointInJson":"{\"lng\":117.355171,\"lat\":31.84698}","UpdateTime":"2019/12/4 13:02:08","Memo":"他在游泳","FullPath":"幸福家园/18号楼","RowNum":"1","total":"3"},{"Id":"e808b935-c94d-4cb9-a636-cf2402b32165","DepartmentId":"91c0b3e2-f0f0-4d85-8ecf-ff6cfadd6a03","IVImageUrlsInJson":"[]","ElementTypeId":"96c755aa-609c-4b10-b750-9df67a66b045","PointInJson":"{\"lng\":117.354947,\"lat\":31.847187}","UpdateTime":"2019/12/4 12:57:16","Memo":"他还活着","FullPath":"幸福家园/18号楼","RowNum":"2","total":"3"},{"Id":"27d18b26-87c7-4634-9170-73b0290395b1","DepartmentId":"91c0b3e2-f0f0-4d85-8ecf-ff6cfadd6a03","IVImageUrlsInJson":"[\"http://192.168.0.160/Uploads/CustomerData/image/file_1575435420000637110610209374332.jpg\"]","ElementTypeId":"96c755aa-609c-4b10-b750-9df67a66b045","PointInJson":"{\"lng\":117.354641,\"lat\":31.84708}","UpdateTime":"2019/12/4 12:57:02","Memo":"他在游泳","FullPath":"幸福家园/18号楼","RowNum":"3","total":"3"}]
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

    public static class ListBean implements Serializable {
        /**
         * Id : b19fa3ec-c192-45dd-912b-867670872616
         * DepartmentId : 91c0b3e2-f0f0-4d85-8ecf-ff6cfadd6a03
         * IVImageUrlsInJson : ["http://192.168.0.160/Uploads/CustomerData/image/file_1575435726000637110613262479119.jpg"]
         * ElementTypeId : 96c755aa-609c-4b10-b750-9df67a66b045
         * PointInJson : {"lng":117.355171,"lat":31.84698}
         * UpdateTime : 2019/12/4 13:02:08
         * Memo : 他在游泳
         * FullPath : 幸福家园/18号楼
         * RowNum : 1
         * total : 3
         */

        private String Id;
        private String DepartmentId;
        private String IVImageUrlsInJson;
        private String ElementTypeId;
        private String PointInJson;
        private String UpdateTime;
        private String CreateTime;
        private String Memo;
        private String FullPath;
        private String RowNum;
        private String total;
        private String cId;
        private boolean IsComplete;

        public boolean isComplete() {
            return IsComplete;
        }

        public void setComplete(boolean complete) {
            IsComplete = complete;
        }

        public String getcId() {
            return cId;
        }

        public void setcId(String cId) {
            this.cId = cId;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(String DepartmentId) {
            this.DepartmentId = DepartmentId;
        }

        public String getIVImageUrlsInJson() {
            return IVImageUrlsInJson;
        }

        public void setIVImageUrlsInJson(String IVImageUrlsInJson) {
            this.IVImageUrlsInJson = IVImageUrlsInJson;
        }

        public String getElementTypeId() {
            return ElementTypeId;
        }

        public void setElementTypeId(String ElementTypeId) {
            this.ElementTypeId = ElementTypeId;
        }

        public String getPointInJson() {
            return PointInJson;
        }

        public void setPointInJson(String PointInJson) {
            this.PointInJson = PointInJson;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public String getRowNum() {
            return RowNum;
        }

        public void setRowNum(String RowNum) {
            this.RowNum = RowNum;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }
    }
}

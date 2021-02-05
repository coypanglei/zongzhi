package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class ResourceSearchItemBean {


    /**
     * pageCount : 1
     * interViewRecord : [{"Id":"27d18b26-87c7-4634-9170-73b0290395b1","ResourceId":"b269d5d6-eeea-4cb7-b96c-184cc6e06763","ResourceName":"小王八","ElementTypeId":"96c755aa-609c-4b10-b750-9df67a66b045","DepartmentFullPath":"合肥市瑶海区/牌楼街道/牌楼社区/一号网格"},{"Id":"27373434-ede1-4669-be6b-aa79628aea42","ResourceId":"f6f1f67a-0bf9-4cc2-be03-861590e5a9d6","ResourceName":"小红","ElementTypeId":"96c755aa-609c-4b10-b750-9df67a66b045","DepartmentFullPath":"合肥市瑶海区/牌楼街道/牌楼社区/一号网格"}]
     */

    private int pageCount;
    private List<InterViewRecordBean> interViewRecord;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<InterViewRecordBean> getInterViewRecord() {
        return interViewRecord;
    }

    public void setInterViewRecord(List<InterViewRecordBean> interViewRecord) {
        this.interViewRecord = interViewRecord;
    }

    public static class InterViewRecordBean {
        /**
         * Id : 27d18b26-87c7-4634-9170-73b0290395b1
         * ResourceId : b269d5d6-eeea-4cb7-b96c-184cc6e06763
         * ResourceName : 小王八
         * ElementTypeId : 96c755aa-609c-4b10-b750-9df67a66b045
         * DepartmentFullPath : 合肥市瑶海区/牌楼街道/牌楼社区/一号网格
         */

        private String Id;
        private String ResourceId;
        private String ResourceName;
        private String ElementTypeId;
        private String DepartmentFullPath;
        private String Name;
        private String CreateTime;
        private String Count;

        public String getCount() {
            return Count;
        }

        public void setCount(String count) {
            Count = count;
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

        public String getResourceId() {
            return ResourceId;
        }

        public void setResourceId(String ResourceId) {
            this.ResourceId = ResourceId;
        }

        public String getResourceName() {
            return ResourceName;
        }

        public void setResourceName(String ResourceName) {
            this.ResourceName = ResourceName;
        }

        public String getElementTypeId() {
            return ElementTypeId;
        }

        public void setElementTypeId(String ElementTypeId) {
            this.ElementTypeId = ElementTypeId;
        }

        public String getDepartmentFullPath() {
            return DepartmentFullPath;
        }

        public void setDepartmentFullPath(String DepartmentFullPath) {
            this.DepartmentFullPath = DepartmentFullPath;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }
    }
}

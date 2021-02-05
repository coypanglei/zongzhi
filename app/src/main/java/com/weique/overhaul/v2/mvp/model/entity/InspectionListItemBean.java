package com.weique.overhaul.v2.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class InspectionListItemBean {

    /**
     * pageCount : 1
     * inspectionRecord : [{"Id":"d544375a-07d1-4b18-82b4-1d76346e8976","ResourceId":"22209d61-dcb1-41d5-a496-0a5a64bb096f","ElementTypeId":"938448e8-cd4d-4aa3-b84a-e4ff0f1ee911","ResourceName":"苹果新天地","Name":"弟地","DepartmentFullPath":"合肥市瑶海区/牌楼街道/牌楼社区/一号网格"},{"Id":"961dc1db-5db0-4504-b593-6f441d985b0a","ResourceId":"fd3e629a-65f9-4d7d-abf5-c301f20d0dd6","ElementTypeId":"b1656480-305e-45c4-be5c-279d093ec743","ResourceName":"大路灯","Name":"路灯","DepartmentFullPath":"合肥市瑶海区/牌楼街道/牌楼社区/一号网格"}]
     */

    private int pageCount;
    private List<InspectionRecordBean> inspectionRecord;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<InspectionRecordBean> getInspectionRecord() {
        return inspectionRecord;
    }

    public void setInspectionRecord(List<InspectionRecordBean> inspectionRecord) {
        this.inspectionRecord = inspectionRecord;
    }

    public static class InspectionRecordBean implements Serializable {
        /**
         * Id : d544375a-07d1-4b18-82b4-1d76346e8976
         * ResourceId : 22209d61-dcb1-41d5-a496-0a5a64bb096f
         * ElementTypeId : 938448e8-cd4d-4aa3-b84a-e4ff0f1ee911
         * ResourceName : 苹果新天地
         * Name : 弟地
         * DepartmentFullPath : 合肥市瑶海区/牌楼街道/牌楼社区/一号网格
         */

        private String Id;
        private String ResourceId;
        private String ElementTypeId;
        private String ResourceName;
        private String Name;
        private String DepartmentFullPath;
        private String CreateTime;
        private String Count;

        public String getCount() {
            return Count;
        }

        public void setCount(String count) {
            Count = count;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
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

        public String getElementTypeId() {
            return ElementTypeId;
        }

        public void setElementTypeId(String ElementTypeId) {
            this.ElementTypeId = ElementTypeId;
        }

        public String getResourceName() {
            return ResourceName;
        }

        public void setResourceName(String ResourceName) {
            this.ResourceName = ResourceName;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getDepartmentFullPath() {
            return DepartmentFullPath;
        }

        public void setDepartmentFullPath(String DepartmentFullPath) {
            this.DepartmentFullPath = DepartmentFullPath;
        }
    }
}

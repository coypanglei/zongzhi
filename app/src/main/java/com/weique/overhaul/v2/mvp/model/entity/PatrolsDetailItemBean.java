package com.weique.overhaul.v2.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class PatrolsDetailItemBean {

    /**
     * TaskId : e3973010-9658-4f22-8f6c-7158a3b5b60d
     * elements : [{"name":"","PointsInJson":"{\"lat\":34.300033,\"lng\":117.213948}","status":false,"Id":"9e951f07-4653-491f-9efd-5834c26327be"}]
     * totalCount : 1
     */

    private String TaskId;
    private int totalCount;
    private List<ElementsBean> elements;

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String TaskId) {
        this.TaskId = TaskId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ElementsBean> getElements() {
        return elements;
    }

    public void setElements(List<ElementsBean> elements) {
        this.elements = elements;
    }

    public static class ElementsBean implements Serializable {
        /**
         * name :
         * PointsInJson : {"lat":34.300033,"lng":117.213948}
         * status : false
         * Id : 9e951f07-4653-491f-9efd-5834c26327be
         */

        private String name;
        private String PointsInJson;
        private boolean status;
        private String Id;
        private String ElementTypeId;
        private String DepartmentId;
        private String InspectionRecordId;
        private String TaskId;
        private int EnumElementProcType;

        /**
         * 走访图片
         */
        private String Memo;
        private String IVImageUrlsInJson;
        private String PointInJson;

        public String getPointInJson() {
            return PointInJson;
        }

        public void setPointInJson(String pointInJson) {
            PointInJson = pointInJson;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String memo) {
            Memo = memo;
        }

        public String getIVImageUrlsInJson() {
            return IVImageUrlsInJson;
        }

        public void setIVImageUrlsInJson(String IVImageUrlsInJson) {
            this.IVImageUrlsInJson = IVImageUrlsInJson;
        }

        public int getEnumElementProcType() {
            return EnumElementProcType;
        }

        public void setEnumElementProcType(int enumElementProcType) {
            EnumElementProcType = enumElementProcType;
        }

        public String getInspectionRecordId() {
            return InspectionRecordId;
        }

        public String getTaskId() {
            return TaskId;
        }

        public void setTaskId(String taskId) {
            TaskId = taskId;
        }

        public void setInspectionRecordId(String inspectionRecordId) {
            InspectionRecordId = inspectionRecordId;
        }

        public String getElementTypeId() {
            return ElementTypeId;
        }

        public void setElementTypeId(String elementTypeId) {
            ElementTypeId = elementTypeId;
        }

        public String getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(String departmentId) {
            DepartmentId = departmentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPointsInJson() {
            return PointsInJson;
        }

        public void setPointsInJson(String PointsInJson) {
            this.PointsInJson = PointsInJson;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }
    }
}

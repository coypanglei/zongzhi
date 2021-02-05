package com.weique.overhaul.v2.mvp.model.entity;

public class InspectionAddBean {
    private String InspectionRecordId;
    private String PointInJson;
    private String IRImageUrlsInJson;
    private String memo;
    private String Id;
    private String CreateUserId;
    private String UpdateUserId;
    private String TaskId;

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getInspectionRecordId() {
        return InspectionRecordId;
    }

    public void setInspectionRecordId(String inspectionRecordId) {
        InspectionRecordId = inspectionRecordId;
    }

    public String getPointInJson() {
        return PointInJson;
    }

    public void setPointInJson(String pointInJson) {
        PointInJson = pointInJson;
    }

    public String getIRImageUrlsInJson() {
        return IRImageUrlsInJson;
    }

    public void setIRImageUrlsInJson(String IRImageUrlsInJson) {
        this.IRImageUrlsInJson = IRImageUrlsInJson;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String createUserId) {
        CreateUserId = createUserId;
    }

    public String getUpdateUserId() {
        return UpdateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        UpdateUserId = updateUserId;
    }
}

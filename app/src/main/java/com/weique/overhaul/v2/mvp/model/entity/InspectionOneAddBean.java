package com.weique.overhaul.v2.mvp.model.entity;

public class InspectionOneAddBean {
    private String ResourceId;
    private String ResourceName;
    private String ElementTypeId;
    private String DepartmentId;
    private String CreateUserId;
    private String UpdateUserId;
    private String TaskId;

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public String getResourceId() {
        return ResourceId;
    }

    public void setResourceId(String resourceId) {
        ResourceId = resourceId;
    }

    public String getResourceName() {
        return ResourceName;
    }

    public void setResourceName(String resourceName) {
        ResourceName = resourceName;
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

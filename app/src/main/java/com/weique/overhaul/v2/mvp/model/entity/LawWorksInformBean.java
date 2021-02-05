package com.weique.overhaul.v2.mvp.model.entity;

public class LawWorksInformBean {

    /**
     * CourtNotifTypeId : 00000000-0000-0000-0000-000000000000
     * CourtNotifTypeName : 通知类型1
     * Title : 王伟试试看
     * Content : null
     * EnumCourtNotificationStatus : 3
     * EnumCourtNotificationStatusStr : 已审核
     * DepartmentId : 65a18036-eaf6-48b8-ab43-e9ee188d3ade
     * DepartmentFullPath : 徐州市铜山区
     * Id : 7dd5a0d9-fa3c-4da2-8a11-59932712cdf4
     * CreateTime : /Date(1587627035790)/
     * CreateTimeStr : 2020/04/23 15:30
     * UpdateTime : /Date(1587627049473)/
     * UpdateTimeStr : 2020/04/23 15:30
     * CreateUId : 00000000-0000-0000-0000-000000000000
     * CreateEmployeeName : 超级管理员
     * UpdateUId : 00000000-0000-0000-0000-000000000000
     * UpdateEmployeeName : 超级管理员
     * Memo :
     */

    private String CourtNotifTypeId;
    private String CourtNotifTypeName;
    private String Title;
    private String Content;
    private int EnumCourtNotificationStatus;
    private String EnumCourtNotificationStatusStr;
    private String DepartmentId;
    private String DepartmentFullPath;
    private String Id;
    private String CreateTime;
    private String CreateTimeStr;
    private String UpdateTime;
    private String UpdateTimeStr;
    private String CreateUId;
    private String CreateEmployeeName;
    private String UpdateUId;
    private String UpdateEmployeeName;
    private String Memo;
    private Boolean IsRead;

    public String getCourtNotifTypeId() {
        return CourtNotifTypeId;
    }

    public void setCourtNotifTypeId(String CourtNotifTypeId) {
        this.CourtNotifTypeId = CourtNotifTypeId;
    }

    public String getCourtNotifTypeName() {
        return CourtNotifTypeName;
    }

    public void setCourtNotifTypeName(String CourtNotifTypeName) {
        this.CourtNotifTypeName = CourtNotifTypeName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }


    public int getEnumCourtNotificationStatus() {
        return EnumCourtNotificationStatus;
    }

    public void setEnumCourtNotificationStatus(int EnumCourtNotificationStatus) {
        this.EnumCourtNotificationStatus = EnumCourtNotificationStatus;
    }

    public String getEnumCourtNotificationStatusStr() {
        return EnumCourtNotificationStatusStr;
    }

    public void setEnumCourtNotificationStatusStr(String EnumCourtNotificationStatusStr) {
        this.EnumCourtNotificationStatusStr = EnumCourtNotificationStatusStr;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getDepartmentFullPath() {
        return DepartmentFullPath;
    }

    public void setDepartmentFullPath(String DepartmentFullPath) {
        this.DepartmentFullPath = DepartmentFullPath;
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

    public String getCreateTimeStr() {
        return CreateTimeStr;
    }

    public void setCreateTimeStr(String CreateTimeStr) {
        this.CreateTimeStr = CreateTimeStr;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getUpdateTimeStr() {
        return UpdateTimeStr;
    }

    public void setUpdateTimeStr(String UpdateTimeStr) {
        this.UpdateTimeStr = UpdateTimeStr;
    }

    public String getCreateUId() {
        return CreateUId;
    }

    public void setCreateUId(String CreateUId) {
        this.CreateUId = CreateUId;
    }

    public String getCreateEmployeeName() {
        return CreateEmployeeName;
    }

    public void setCreateEmployeeName(String CreateEmployeeName) {
        this.CreateEmployeeName = CreateEmployeeName;
    }

    public String getUpdateUId() {
        return UpdateUId;
    }

    public void setUpdateUId(String UpdateUId) {
        this.UpdateUId = UpdateUId;
    }

    public String getUpdateEmployeeName() {
        return UpdateEmployeeName;
    }

    public void setUpdateEmployeeName(String UpdateEmployeeName) {
        this.UpdateEmployeeName = UpdateEmployeeName;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContent() {
        return Content;
    }

    public Boolean getRead() {
        return IsRead;
    }

    public void setRead(Boolean read) {
        IsRead = read;
    }
}

package com.weique.overhaul.v2.mvp.model.entity;

/**
 * @author GreatKing
 */
public class EventProceedRecordBean {

    /**
     * Id : 101e75e1-d7bf-4d91-b56d-414636c93052
     * Name : 测试管理员
     * DepartName : 食堂
     * HeadUrl : /Content/img/user.png
     * Memo : 11111111111
     * EnumEventProceedStatus : 4
     */

    private String Id;
    private String Name;
    private String DepartName;
    private String HeadUrl;
    private String Memo;
    private String EnumEventProceedStatus;
    private String CreateTime;
    private boolean IsInCharge = true;
    /**
     * 获取文件路径
     */
    private String FileUrls;


    public String getFileUrls() {
        return FileUrls;
    }

    public void setFileUrls(String fileUrls) {
        FileUrls = fileUrls;
    }

    /**
     * 0无协同  1.有 未到场  2.有 到场
     */
    private int EnumIsInSite;


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

    public String getDepartName() {
        return DepartName;
    }

    public void setDepartName(String DepartName) {
        this.DepartName = DepartName;
    }

    public String getHeadUrl() {
        return HeadUrl;
    }

    public void setHeadUrl(String HeadUrl) {
        this.HeadUrl = HeadUrl;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }

    public String getEnumEventProceedStatus() {
        return EnumEventProceedStatus;
    }

    public void setEnumEventProceedStatus(String EnumEventProceedStatus) {
        this.EnumEventProceedStatus = EnumEventProceedStatus;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public boolean isInCharge() {
        return IsInCharge;
    }

    public void setInCharge(boolean inCharge) {
        IsInCharge = inCharge;
    }

    public int getEnumIsInSite() {
        return EnumIsInSite;
    }

    public void setEnumIsInSite(int enumIsInSite) {
        EnumIsInSite = enumIsInSite;
    }
}

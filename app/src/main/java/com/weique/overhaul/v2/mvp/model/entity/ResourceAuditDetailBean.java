package com.weique.overhaul.v2.mvp.model.entity;

import com.weique.overhaul.v2.R;

import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2020/12/28 11:12
 */
public class ResourceAuditDetailBean {


    /**
     * Id : 3599d6cc-4f52-4548-aff9-f12251eab43e
     * ElementId : 3e342460-0ef7-4841-be9d-494a051e5374
     * ElementTypeId : 3e342460-0ef7-4841-be9d-494a051e5374
     * CustId : 64b89281-75d8-48a6-bf19-b0cd949ce33e
     * Name : 钱梅
     * FullPath : 全部分类/人/四失五类/低保
     * Status : 待处理
     * StatusValue : 0
     * Description : as地方撒旦f
     * pics : ["/Uploads/CustomerData/image/IMG_CMP_133476632637448555904751928.png"]
     * reason : as地方撒旦f
     * isEnable : false
     */

    private String Id;
    private String ElementId;
    private String ElementTypeId;
    private String CustId;
    private String Name;
    private String FullPath;
    private String Status;
    private int StatusValue;
    private String Description;
    private String reason;
    private boolean isEnable;
    private List<String> pics;

    public int getStatusColor() {
        switch (StatusValue) {
            case 0:
                return R.color.green;
            case 1:
                return R.color.blue_4D8FF7;
            case 2:
                return R.color.red;
            default:
        }
        return R.color.gray;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getElementId() {
        return ElementId;
    }

    public void setElementId(String ElementId) {
        this.ElementId = ElementId;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String CustId) {
        this.CustId = CustId;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getStatusValue() {
        return StatusValue;
    }

    public void setStatusValue(int StatusValue) {
        this.StatusValue = StatusValue;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getElementTypeId() {
        return ElementTypeId;
    }

    public void setElementTypeId(String elementTypeId) {
        ElementTypeId = elementTypeId;
    }
}

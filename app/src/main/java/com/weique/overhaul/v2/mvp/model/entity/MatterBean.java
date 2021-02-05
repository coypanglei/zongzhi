package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;

/**
 * @author GK
 * @description:
 * @date :2020/8/10 17:09
 */
public class MatterBean extends TreeBaseDataBean {

    /**
     * Name : 城市管理
     * PinYin : CSGL
     * Id : f29ce858-2376-4184-8081-461e7cd6ef0a
     * CreateTime : /Date(1595821064010)/
     * CreateTimeStr : 2020/07/27 11:37
     * UpdateTime : /Date(1595821064010)/
     * UpdateTimeStr : 2020/07/27 11:37
     * CreateUId : 00000000-0000-0000-0000-000000000000
     * CreateEmployeeName : null
     * UpdateUId : 00000000-0000-0000-0000-000000000000
     * UpdateEmployeeName : 管理员
     * Memo : null
     */

    private String PinYin;
    private String CreateTime;
    private String CreateTimeStr;
    private String UpdateTime;
    private String UpdateTimeStr;
    private String CreateUId;
    private String CreateEmployeeName;
    private String UpdateUId;
    private String UpdateEmployeeName;
    private String Memo;
    private int childrenCount;


    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String PinYin) {
        this.PinYin = PinYin;
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

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}

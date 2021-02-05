package com.weique.overhaul.v2.mvp.model.entity;

public class NewVersionInfoBean {

    /**
     * Id : 599fdd58-deb2-42c2-b3e5-7ae2bc80900d
     * VersionName : 第二版
     * VersionNumber : 102
     * APPId : 江苏微雀
     * CPUType : armeabi
     * Channel : 华为商城
     * IsForceUpdate : false
     * IsSilentInstall : false
     * ApkUrl : null
     * Memo : null
     * CreateTime : 2020/01/07
     */

    private String Id;
    private String VersionName;
    private int VersionNumber;
    private String APPId;
    private String CPUType;
    private String Channel;
    private boolean IsForceUpdate;
    private boolean IsSilentInstall;
    private String ApkUrl;
    private String Memo;
    private String CreateTime;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String VersionName) {
        this.VersionName = VersionName;
    }

    public int getVersionNumber() {
        return VersionNumber;
    }

    public void setVersionNumber(int VersionNumber) {
        this.VersionNumber = VersionNumber;
    }

    public String getAPPId() {
        return APPId;
    }

    public void setAPPId(String APPId) {
        this.APPId = APPId;
    }

    public String getCPUType() {
        return CPUType;
    }

    public void setCPUType(String CPUType) {
        this.CPUType = CPUType;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String Channel) {
        this.Channel = Channel;
    }

    public boolean isIsForceUpdate() {
        return IsForceUpdate;
    }

    public void setIsForceUpdate(boolean IsForceUpdate) {
        this.IsForceUpdate = IsForceUpdate;
    }

    public boolean isIsSilentInstall() {
        return IsSilentInstall;
    }

    public void setIsSilentInstall(boolean IsSilentInstall) {
        this.IsSilentInstall = IsSilentInstall;
    }

    public String getApkUrl() {
        return ApkUrl;
    }

    public void setApkUrl(String ApkUrl) {
        this.ApkUrl = ApkUrl;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
}

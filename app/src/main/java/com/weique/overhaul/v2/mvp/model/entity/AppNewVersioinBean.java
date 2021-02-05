package com.weique.overhaul.v2.mvp.model.entity;

public class AppNewVersioinBean {

    /**
     * code : 200
     * message : 成功
     * data : {"Id":"ec6b614d-0f94-4a62-8d27-c2611ae81fa4","VersionName":"1.1.5","VersionNumber":115,"APPId":null,"CPUType":null,"Channel":0,"IsForceUpdate":false,"IsSilentInstall":false,"ApkUrl":"/Uploads/CustomerData/file/jiawang115.apk","Memo":"1.修复版本更新漏洞","CreateTime":"2020/04/01"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Id : ec6b614d-0f94-4a62-8d27-c2611ae81fa4
         * VersionName : 1.1.5
         * VersionNumber : 115
         * APPId : null
         * CPUType : null
         * Channel : 0
         * IsForceUpdate : false
         * IsSilentInstall : false
         * ApkUrl : /Uploads/CustomerData/file/jiawang115.apk
         * Memo : 1.修复版本更新漏洞
         * CreateTime : 2020/04/01
         */

        private String Id;
        private String VersionName;
        private int VersionNumber;
        private Object APPId;
        private Object CPUType;
        private int Channel;
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

        public Object getAPPId() {
            return APPId;
        }

        public void setAPPId(Object APPId) {
            this.APPId = APPId;
        }

        public Object getCPUType() {
            return CPUType;
        }

        public void setCPUType(Object CPUType) {
            this.CPUType = CPUType;
        }

        public int getChannel() {
            return Channel;
        }

        public void setChannel(int Channel) {
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
}

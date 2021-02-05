package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class ContradictionRecordBean {


    /**
     * cAEventOrder : {"Id":"310e1a1b-d352-40cb-b883-385ba524d0ef","STANDARDADDRESSID":"f32192bd-974b-4793-8cf7-0c6b683d1705","FullPath":"徐州人家生态居住区/A2栋/3单元","GisLocations":"","Code":null,"EnumResolveType":0,"EnumCAEventOrderSaveStatus":0,"Address":"数字产业园","Memo":"放大假"}
     * cAPartyPerson : [{"Id":"7a6193a7-3307-48ba-ae7f-13ccb374aa2d","Name":"小王","EnumGender":1,"BirthDate":"/Date(947001600000)/","Nation":"汉","Job":"","JobUnit":"","CIndex":0},{"Id":"5c2fcb07-7bfd-494c-9314-bbe849787bab","Name":"小找","EnumGender":1,"BirthDate":"/Date(947001600000)/","Nation":"汉","Job":"","JobUnit":"","CIndex":1}]
     */

    private CAEventOrderBean cAEventOrder;
    private List<CAPartyPersonBean> cAPartyPerson;

    public CAEventOrderBean getCAEventOrder() {
        return cAEventOrder;
    }

    public void setCAEventOrder(CAEventOrderBean cAEventOrder) {
        this.cAEventOrder = cAEventOrder;
    }

    public List<CAPartyPersonBean> getCAPartyPerson() {
        return cAPartyPerson;
    }

    public void setCAPartyPerson(List<CAPartyPersonBean> cAPartyPerson) {
        this.cAPartyPerson = cAPartyPerson;
    }

    public static class CAEventOrderBean {
        /**
         * Id : 310e1a1b-d352-40cb-b883-385ba524d0ef
         * STANDARDADDRESSID : f32192bd-974b-4793-8cf7-0c6b683d1705
         * FullPath : 徐州人家生态居住区/A2栋/3单元
         * GisLocations :
         * Code : null
         * EnumResolveType : 0
         * EnumCAEventOrderSaveStatus : 0
         * Address : 数字产业园
         * Memo : 放大假
         */

        private String Id;
        private String StandardAddressId;
        private String FullPath;
        private String GisLocation;
        private Object Code;
        private int EnumResolveType;
        private int EnumCAEventOrderSaveStatus;
        private String Address;
        private String Memo;
        private String RefuseReason;
        private int EnumCAEventOrderType;
        private String PicPath;

        public String getPicPath() {
            return PicPath;
        }

        public void setPicPath(String picPath) {
            PicPath = picPath;
        }

        public int getEnumCAEventOrderType() {
            return EnumCAEventOrderType;
        }

        public void setEnumCAEventOrderType(int enumCAEventOrderType) {
            EnumCAEventOrderType = enumCAEventOrderType;
        }

        public String getRefuseReason() {
            return RefuseReason;
        }

        public void setRefuseReason(String refuseReason) {
            RefuseReason = refuseReason;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getStandardAddressId() {
            return StandardAddressId;
        }

        public void setStandardAddressId(String StandardAddressId) {
            this.StandardAddressId = StandardAddressId;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public String getGisLocations() {
            return GisLocation;
        }

        public void setGisLocations(String GisLocation) {
            this.GisLocation = GisLocation;
        }

        public Object getCode() {
            return Code;
        }

        public void setCode(Object Code) {
            this.Code = Code;
        }

        public int getEnumResolveType() {
            return EnumResolveType;
        }

        public void setEnumResolveType(int EnumResolveType) {
            this.EnumResolveType = EnumResolveType;
        }

        public int getEnumCAEventOrderSaveStatus() {
            return EnumCAEventOrderSaveStatus;
        }

        public void setEnumCAEventOrderSaveStatus(int EnumCAEventOrderSaveStatus) {
            this.EnumCAEventOrderSaveStatus = EnumCAEventOrderSaveStatus;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }
    }

    public static class CAPartyPersonBean {
        /**
         * Id : 7a6193a7-3307-48ba-ae7f-13ccb374aa2d
         * Name : 小王
         * EnumGender : 1
         * BirthDate : /Date(947001600000)/
         * Nation : 汉
         * Job :
         * JobUnit :
         * CIndex : 0
         */

        private String Id;
        private String Name;
        private int EnumGender;
        private String BirthDate;
        private String Nation;
        private String Job;
        private String JobUnit;
        private int CIndex;
        private String CID;
        private String Tel;

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }

        public String getCID() {
            return CID;
        }

        public void setCID(String CID) {
            this.CID = CID;
        }
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

        public int getEnumGender() {
            return EnumGender;
        }

        public void setEnumGender(int EnumGender) {
            this.EnumGender = EnumGender;
        }

        public String getBirthDate() {
            return BirthDate;
        }

        public void setBirthDate(String BirthDate) {
            this.BirthDate = BirthDate;
        }

        public String getNation() {
            return Nation;
        }

        public void setNation(String Nation) {
            this.Nation = Nation;
        }

        public String getJob() {
            return Job;
        }

        public void setJob(String Job) {
            this.Job = Job;
        }

        public String getJobUnit() {
            return JobUnit;
        }

        public void setJobUnit(String JobUnit) {
            this.JobUnit = JobUnit;
        }

        public int getCIndex() {
            return CIndex;
        }

        public void setCIndex(int CIndex) {
            this.CIndex = CIndex;
        }
    }
}

package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class StaffListBean {

    /**
     * pageCount : 1
     * list : [{"Id":"63a0468e-a22e-4573-9437-6528b1498320","Name":"小美","Gender":"男","Age":22,"Tel":"123123123","EnterpriseInfName":"管理员","CurrentAddress":"asdasd","IsDepartureHistory0":true,"IsDepartureHistory1":true,"IsDepartureHistory2":true,"IsDepartureHistory3":false,"EnumPropertyType":"自有住房","PhotoUrl":"/Uploads/CustomerData/image/file_1581588575000637172141758127511.png","ReturnToWorkTime":"2222-02-12","SID":"12321312312","Memo":null},{"Id":"5bb2b82c-7343-42c0-abb4-7869c0fb9acc","Name":"asdas","Gender":"男","Age":222,"Tel":"123213123","EnterpriseInfName":"管理员","CurrentAddress":"3123123","IsDepartureHistory0":false,"IsDepartureHistory1":false,"IsDepartureHistory2":false,"IsDepartureHistory3":false,"EnumPropertyType":"自有住房","PhotoUrl":"/Uploads/CustomerData/image/file_1581586598000637172121983426276.png","ReturnToWorkTime":"2012-02-02","SID":"123123123","Memo":null}]
     */

    private int pageCount;
    private List<ListBean> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        /**
         * Id : 0b9aa211-d9c2-4e88-b6b3-a2c49442db52
         * Name : wqeq
         * Gender : 不确定
         * Age : 22
         * Tel : 11111111
         * Domicile : 123123
         * EnumIsOutXuzhou : 否
         * EnterpriseInfName : 管理员
         * CurrentAddress : 瓦房1字巷/10号
         * IsDepartureHistory0 : true
         * IsDepartureHistory1 : true
         * IsDepartureHistory2 : false
         * IsDepartureHistory3 : false
         * EnumPropertyType : 自有住房
         * PhotoUrl : /Uploads/CustomerData/image/file_1581642860000637172684610046443.png
         * ReturnToWorkTime : 2020-02-14
         * SID : 1231231
         * Memo : null
         * IsThermometer : false
         */

        private String Id;
        private String Name;
        private String Gender;
        private int Age;
        private String Tel;
        private String Domicile;
        private String EnumIsOutXuzhou;
        private String EnterpriseInfName;
        private String CurrentAddress;
        private boolean IsDepartureHistory0;
        private boolean IsDepartureHistory1;
        private boolean IsDepartureHistory2;
        private boolean IsDepartureHistory3;
        private String EnumPropertyType;
        private String PhotoUrl;
        private String ReturnToWorkTime;
        private String SID;
        private Object Memo;
        private boolean IsThermometer;
        private double AMTemperature = 0;
        private double PMTemperature = 0;
        private boolean isIgnore = false;

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

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public String getDomicile() {
            return Domicile;
        }

        public void setDomicile(String Domicile) {
            this.Domicile = Domicile;
        }

        public String getEnumIsOutXuzhou() {
            return EnumIsOutXuzhou;
        }

        public void setEnumIsOutXuzhou(String EnumIsOutXuzhou) {
            this.EnumIsOutXuzhou = EnumIsOutXuzhou;
        }

        public String getEnterpriseInfName() {
            return EnterpriseInfName;
        }

        public void setEnterpriseInfName(String EnterpriseInfName) {
            this.EnterpriseInfName = EnterpriseInfName;
        }

        public String getCurrentAddress() {
            return CurrentAddress;
        }

        public void setCurrentAddress(String CurrentAddress) {
            this.CurrentAddress = CurrentAddress;
        }

        public boolean isIsDepartureHistory0() {
            return IsDepartureHistory0;
        }

        public void setIsDepartureHistory0(boolean IsDepartureHistory0) {
            this.IsDepartureHistory0 = IsDepartureHistory0;
        }

        public boolean isIsDepartureHistory1() {
            return IsDepartureHistory1;
        }

        public void setIsDepartureHistory1(boolean IsDepartureHistory1) {
            this.IsDepartureHistory1 = IsDepartureHistory1;
        }

        public boolean isIsDepartureHistory2() {
            return IsDepartureHistory2;
        }

        public void setIsDepartureHistory2(boolean IsDepartureHistory2) {
            this.IsDepartureHistory2 = IsDepartureHistory2;
        }

        public boolean isIsDepartureHistory3() {
            return IsDepartureHistory3;
        }

        public void setIsDepartureHistory3(boolean IsDepartureHistory3) {
            this.IsDepartureHistory3 = IsDepartureHistory3;
        }

        public String getEnumPropertyType() {
            return EnumPropertyType;
        }

        public void setEnumPropertyType(String EnumPropertyType) {
            this.EnumPropertyType = EnumPropertyType;
        }

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String PhotoUrl) {
            this.PhotoUrl = PhotoUrl;
        }

        public String getReturnToWorkTime() {
            return ReturnToWorkTime;
        }

        public void setReturnToWorkTime(String ReturnToWorkTime) {
            this.ReturnToWorkTime = ReturnToWorkTime;
        }

        public String getSID() {
            return SID;
        }

        public void setSID(String SID) {
            this.SID = SID;
        }

        public Object getMemo() {
            return Memo;
        }

        public void setMemo(Object Memo) {
            this.Memo = Memo;
        }

        public boolean isIsThermometer() {
            return IsThermometer;
        }

        public void setIsThermometer(boolean IsThermometer) {
            this.IsThermometer = IsThermometer;
        }

        public double getAMTemperature() {
            return AMTemperature;
        }

        public void setAMTemperature(double AMTemperature) {
            this.AMTemperature = AMTemperature;
        }

        public double getPMTemperature() {
            return PMTemperature;
        }

        public void setPMTemperature(double PMTemperature) {
            this.PMTemperature = PMTemperature;
        }

        public boolean isIgnore() {
            return isIgnore;
        }

        public void setIgnore(boolean ignore) {
            isIgnore = ignore;
        }
    }
}

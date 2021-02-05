package com.weique.overhaul.v2.mvp.model.entity;

public class StaffDetailBean {

    /**
     * Name : 企业员工2
     * EnumGenderValue : 2
     * Tel : 19900999999
     * SID : 320320198712121212
     * Domicile : 合肥市瑶海区
     * CurrentAddress : 合肥市瑶海区
     * EnumIsOutXuzhou : 0
     * EnumIsOutXuzhouStr : 0
     * EnumPropertyType : 自有住房
     * ReturnToWorkTime : 2020-02-26
     * PhotoUrl : /Uploads/CustomerData/image/file_1582698313000637183239074376576.png
     * PersonalTrajectorySrc : /Uploads/CustomerData/image/file_1582698322000637183239161973663.png
     * ContactPersonSrc : null
     */

    private String Name;
    private String EnumGenderValue;
    private String Tel;
    private int Age;
    private String SID;
    private String Domicile;
    private String CurrentAddress;
    private String EnumIsOutXuzhou;
    private String EnumPropertyType;
    private String ReturnToWorkTime;
    private String PhotoUrl;
    private String PersonalTrajectorySrc;
    private String ContactPersonSrc;

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEnumGenderValue() {
        return EnumGenderValue;
    }

    public void setEnumGenderValue(String EnumGenderValue) {
        this.EnumGenderValue = EnumGenderValue;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getDomicile() {
        return Domicile;
    }

    public void setDomicile(String Domicile) {
        this.Domicile = Domicile;
    }

    public String getCurrentAddress() {
        return CurrentAddress;
    }

    public void setCurrentAddress(String CurrentAddress) {
        this.CurrentAddress = CurrentAddress;
    }

    public String getEnumIsOutXuzhou() {
        return EnumIsOutXuzhou;
    }

    public void setEnumIsOutXuzhou(String EnumIsOutXuzhou) {
        this.EnumIsOutXuzhou = EnumIsOutXuzhou;
    }

    public String getEnumPropertyType() {
        return EnumPropertyType;
    }

    public void setEnumPropertyType(String EnumPropertyType) {
        this.EnumPropertyType = EnumPropertyType;
    }

    public String getReturnToWorkTime() {
        return ReturnToWorkTime;
    }

    public void setReturnToWorkTime(String ReturnToWorkTime) {
        this.ReturnToWorkTime = ReturnToWorkTime;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String PhotoUrl) {
        this.PhotoUrl = PhotoUrl;
    }

    public String getPersonalTrajectorySrc() {
        return PersonalTrajectorySrc;
    }

    public void setPersonalTrajectorySrc(String PersonalTrajectorySrc) {
        this.PersonalTrajectorySrc = PersonalTrajectorySrc;
    }

    public String getContactPersonSrc() {
        return ContactPersonSrc;
    }

    public void setContactPersonSrc(String ContactPersonSrc) {
        this.ContactPersonSrc = ContactPersonSrc;
    }
}

package com.weique.overhaul.v2.mvp.model.entity;

/**
 * @author GreatKing
 */
public class ContradictionAddBean {

    /**
     * USER_ID : 7B9D8D77-B489-4CE5-8C80-CF29A31E47A9
     * STANDARDADDRESSID : F32192BD-974B-4793-8CF7-0C6B683D1705
     * EnumResolveType : 1
     * EnumCAEventOrderSaveStatus : 1
     * CAPartyPerson : [{"Name":"小美","EnumGender":"1","Job":"职业","Nation":"汉","BirthDate":"2000-01-03","JobUnit":"工作单位","CIndex":0},{"Name":"夏红","EnumGender":"1","Job":"职业","Nation":"汉","BirthDate":"2000-01-03","JobUnit":"工作单位","CIndex":1}]
     * Address : 江苏省
     * Memo : 抢劫
     */

    private String Id;
    private String UserId;
    private String StandardAddressId;
    private String GisLocation;
    private int EnumResolveType;
    private int EnumCAEventOrderSaveStatus;
    private String CAPartyPerson;
    private String Address;
    private String Memo;
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

    public void setEnumCAEventOrderType(int EnumCAEventOrderType) {
        this.EnumCAEventOrderType = EnumCAEventOrderType;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getStandardAddressId() {
        return StandardAddressId;
    }

    public void setStandardAddressId(String StandardAddressId) {
        this.StandardAddressId = StandardAddressId;
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

    public String getCAPartyPerson() {
        return CAPartyPerson;
    }

    public void setCAPartyPerson(String CAPartyPerson) {
        this.CAPartyPerson = CAPartyPerson;
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

    public String getGisLocation() {
        return GisLocation;
    }

    public void setGisLocation(String gisLocations) {
        GisLocation = gisLocations;
    }
}

package com.weique.overhaul.v2.mvp.model.entity;

import com.weique.overhaul.v2.mvp.model.entity.interfaces.SearchShowListBean;

/**
 * @author GK
 * @description:
 * @date :2020/8/12 9:37
 */
public class PartiesBean implements SearchShowListBean {

    /**
     * EnumPartiesTypes : 0
     * EnumPartiesTypesStr : 自然人
     * Name : 111
     * SId : 111
     * Address : 111
     * Tel : 111
     * PinYin : 111
     * personInCharge : null
     * duty : null
     * BirthDate : /Date(1595952000000)/
     * BirthDateStr : 2020-07-29
     * Id : 0d40758d-4f06-4d65-9e42-cdc180d47c68
     * CreateTime : /Date(1595574353857)/
     * CreateTimeStr : 2020/07/24 15:05
     * UpdateTime : /Date(1595814624220)/
     * UpdateTimeStr : 2020/07/27 09:50
     * CreateUId : 00000000-0000-0000-0000-000000000000
     * CreateEmployeeName : null
     * UpdateUId : 00000000-0000-0000-0000-000000000000
     * UpdateEmployeeName : 管理员
     * Memo : 111
     */

    private int EnumPartiesTypes;
    private String EnumPartiesTypesStr;
    private String Name;
    private String SId;
    private String Address;
    private String Tel;
    private String PinYin;
    private int Age;
    private int EnumGender;
    private String EnumGenderStr;
    private String personInCharge;
    private String duty;
    private String BirthDate;
    private String BirthDateStr;
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

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public int getEnumGender() {
        return EnumGender;
    }

    public void setEnumGender(int enumGender) {
        EnumGender = enumGender;
    }

    public String getEnumGenderStr() {
        return EnumGenderStr;
    }

    public void setEnumGenderStr(String enumGenderStr) {
        EnumGenderStr = enumGenderStr;
    }

    public int getEnumPartiesTypes() {
        return EnumPartiesTypes;
    }

    public void setEnumPartiesTypes(int EnumPartiesTypes) {
        this.EnumPartiesTypes = EnumPartiesTypes;
    }

    public String getEnumPartiesTypesStr() {
        return EnumPartiesTypesStr;
    }

    public void setEnumPartiesTypesStr(String EnumPartiesTypesStr) {
        this.EnumPartiesTypesStr = EnumPartiesTypesStr;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSId() {
        return SId;
    }

    public void setSId(String SId) {
        this.SId = SId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String PinYin) {
        this.PinYin = PinYin;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
    }

    public String getBirthDateStr() {
        return BirthDateStr;
    }

    public void setBirthDateStr(String BirthDateStr) {
        this.BirthDateStr = BirthDateStr;
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

    @Override
    public String getViewOneData() {
        return Name;
    }

    @Override
    public String getViewTwoData() {
        return SId;
    }

}

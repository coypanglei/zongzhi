package com.weique.overhaul.v2.mvp.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GlobalUserInfoBean implements Cloneable {


    /**
     * uid : f3959cb9-1285-4352-8bbc-47c306399e36
     * Name : 2021
     * HeadUrl : /Content/img/user.png
     * Gender : 女
     * Sum : 0
     * BirthDate : 2020-08-04
     * DepartmentId : 49f7822c-0ec3-47b4-980d-d6c4a1dad689
     * Tel : 15996266231
     * SID : 3203211994205858585
     * FullPath : 徐州市鼓楼区/丰财街道办事处/白云山社区/白云小区网格/
     * DepartName : 111
     * PartyDepartmentName : 党支部分支
     * DepartmentName : 白云小区网格
     * EnumCommunityLevel : 0
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9eyJJZCI6ImYzOTU5Y2I5LTEyODUtNzNiIsIlVzZXJOYW1lIjoiMjAyMSIsIm5vbmNlIjoiMWEyZmYyMTUtNDZiZS00MmM5LTkyODItOWQ2YmVkODR nMLgEUlgqxhVa0ICuz4f-WSjyM
     */
    private String uid;
    private String Name;
    private String HeadUrl;
    private String Gender;
    private int Sum;
    private String BirthDate;
    private String DepartmentId;
    private List<GlobalUserDepartmentBean> Departments;
    private String Tel;
    private String SID;
    private String FullPath;
    private String DepartName;
    private String DepartId;
    private String PartyDepartmentName;
    private String DepartmentName;
    private int EnumCommunityLevel;
    private String token;
    private String roleName;

    public boolean isComprehensiveLawEnforcementOfficer() {
        return isComprehensiveLawEnforcementOfficer;
    }

    public void setComprehensiveLawEnforcementOfficer(boolean comprehensiveLawEnforcementOfficer) {
        isComprehensiveLawEnforcementOfficer = comprehensiveLawEnforcementOfficer;
    }

    public List<GlobalUserDepartmentBean> getDepartments() {
        return Departments;
    }

    public void setDepartments(List<GlobalUserDepartmentBean> departments) {
        Departments = departments;
    }

    private boolean isComprehensiveLawEnforcementOfficer;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getHeadUrl() {
        return HeadUrl;
    }

    public void setHeadUrl(String HeadUrl) {
        this.HeadUrl = HeadUrl;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public int getSum() {
        return Sum;
    }

    public void setSum(int Sum) {
        this.Sum = Sum;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String DepartmentId) {
        this.DepartmentId = DepartmentId;
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

    public String getFullPath() {
        return FullPath;
    }

    public void setFullPath(String FullPath) {
        this.FullPath = FullPath;
    }

    public String getDepartName() {
        return DepartName;
    }

    public void setDepartName(String DepartName) {
        this.DepartName = DepartName;
    }

    public String getPartyDepartmentName() {
        return PartyDepartmentName;
    }

    public void setPartyDepartmentName(String PartyDepartmentName) {
        this.PartyDepartmentName = PartyDepartmentName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public int getEnumCommunityLevel() {
        return EnumCommunityLevel;
    }

    public void setEnumCommunityLevel(int EnumCommunityLevel) {
        this.EnumCommunityLevel = EnumCommunityLevel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDepartId() {
        return DepartId;
    }

    public void setDepartId(String departId) {
        DepartId = departId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.toString().replace("null", "").replace(" ", "")
                .equals(obj.toString().replace("null", "").replace(" ", ""));
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "GlobalUserInfoBean{" +
                "uid='" + uid + '\'' +
                ", Name='" + Name + '\'' +
                ", HeadUrl='" + HeadUrl + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Sum=" + Sum +
                ", BirthDate='" + BirthDate + '\'' +
                ", DepartmentId='" + DepartmentId + '\'' +
                ", Tel='" + Tel + '\'' +
                ", SID='" + SID + '\'' +
                ", FullPath='" + FullPath + '\'' +
                ", DepartName='" + DepartName + '\'' +
                ", DepartId='" + DepartName + '\'' +
                ", PartyDepartmentName='" + PartyDepartmentName + '\'' +
                ", DepartmentName='" + DepartmentName + '\'' +
                ", EnumCommunityLevel=" + EnumCommunityLevel +
                ", token='" + token + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
package com.weique.overhaul.v2.mvp.model.entity;

public class ContradictionAddPeopleBean {

    /**
     * Name : 小美
     * EnumGender : 1
     * Job : 职业
     * Nation : 汉
     * BirthDate : 2000-01-03
     * JobUnit : 工作单位
     * CIndex : 0
     */

    private String Name;
    private String EnumGender;
    private String Job;
    private String Nation;
    private String BirthDate;
    private String JobUnit;
    private String Tel;
    private int CIndex;

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    private String CID;

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEnumGender() {
        return EnumGender;
    }

    public void setEnumGender(String EnumGender) {
        this.EnumGender = EnumGender;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String Job) {
        this.Job = Job;
    }

    public String getNation() {
        return Nation;
    }

    public void setNation(String Nation) {
        this.Nation = Nation;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
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

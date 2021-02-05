package com.weique.overhaul.v2.mvp.model.entity;

public class EntInfoItemBean {


    /**
     * Title : 哈哈哈哈哈哈
     * Content : 快来买呀快来买呀快来买呀快来买呀快来买呀
     */

    @BeanFieldAnnotation(order = 2)
    public String Content;
    @BeanFieldAnnotation(order = 1)
    public String Title;

    private String Item;
    private String ExecuteDate;
    private String ExecuteUnit;
    private String CurrentStatusDesc;


    private String CameraUrl;

    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCameraUrl() {
        return CameraUrl;
    }

    public void setCameraUrl(String cameraUrl) {
        CameraUrl = cameraUrl;
    }

    private String CheckDesc;

    private String CheckedDate;

    private String Checker;

    public String getChecker() {
        return Checker;
    }

    public void setChecker(String checker) {
        Checker = checker;
    }

    public String getCheckDesc() {
            return CheckDesc;

    }

    public void setCheckDesc(String checkDesc) {
        CheckDesc = checkDesc;
    }

    public String getCheckedDate() {
        return CheckedDate;
    }

    public void setCheckedDate(String checkedDate) {
        CheckedDate = checkedDate;
    }

    /**
     * ApprovalItem : 有没有瘟疫
     * ApprovalDate : /Date(1597939200000)/
     * enumEntApprovalStatus : 审批通过
     * Result : 有瘟疫 很好
     */

    private String ApprovalItem;
    private String ApprovalDate;
    private String EnumEntApprovalStatus;
    private String Result;
    /**
     * Year : 2020
     * Month : 6
     * TaxAmount : 10000.0
     */

    private int Year;
    private int Month;
    private String TaxAmount;
    /**
     * ProgDesc : 夏憨憨和憨憨夏
     * RecordDate : /Date(1597939200000)/
     */

    private String ProgDesc;
    private String RecordDate;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }


    public String getItem() {
        return Item;
    }

    public void setItem(String Item) {
        this.Item = Item;
    }

    public String getExecuteDate() {
        return ExecuteDate;
    }

    public void setExecuteDate(String ExecuteDate) {
        this.ExecuteDate = ExecuteDate;
    }

    public String getExecuteUnit() {
        return ExecuteUnit;
    }

    public void setExecuteUnit(String ExecuteUnit) {
        this.ExecuteUnit = ExecuteUnit;
    }

    public String getCurrentStatusDesc() {
        return CurrentStatusDesc;
    }

    public void setCurrentStatusDesc(String CurrentStatusDesc) {
        this.CurrentStatusDesc = CurrentStatusDesc;
    }

    public String getApprovalItem() {
        return ApprovalItem;
    }

    public void setApprovalItem(String ApprovalItem) {
        this.ApprovalItem = ApprovalItem;
    }

    public String getApprovalDate() {
        return ApprovalDate;
    }

    public void setApprovalDate(String ApprovalDate) {
        this.ApprovalDate = ApprovalDate;
    }

    public String getEnumEntApprovalStatus() {
        return EnumEntApprovalStatus;
    }

    public void setEnumEntApprovalStatus(String enumEntApprovalStatus) {
        EnumEntApprovalStatus = enumEntApprovalStatus;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int Month) {
        this.Month = Month;
    }

    public String getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(String TaxAmount) {
        this.TaxAmount = TaxAmount;
    }

    public String getProgDesc() {
        return ProgDesc;
    }

    public void setProgDesc(String ProgDesc) {
        this.ProgDesc = ProgDesc;
    }

    public String getRecordDate() {
        return RecordDate;
    }

    public void setRecordDate(String RecordDate) {
        this.RecordDate = RecordDate;
    }
}

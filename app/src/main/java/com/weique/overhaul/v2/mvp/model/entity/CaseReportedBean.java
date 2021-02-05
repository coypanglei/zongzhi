package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class CaseReportedBean implements Parcelable {


    /**
     * ComprehensiveLawEnforcementMattersId : 4dee0836-efa4-40f0-90d8-64ab58a6a99f
     * ComprehensiveLawEnforcementMattersName : 权力名称1
     * Code : ZGZD000011
     * SourceName : 1
     * SourceTel : 1
     * SourceAddress : 1
     * EnumCaseSourceStr : 投诉举报
     * ComprehensiveLawEnforcementMattersTypeName : 综合执法事项分类1
     * EnumCaseLevelStr : 轻微
     * isImportantStr : 否
     * Title : 1
     * TargetName : 姓谁名谁家住哪里家里几口人家有几亩地地里几头牛
     * TargetAddress : 世界之大处处是的江湖
     * Cause : 1
     * AcceptanceTime : /Date(1599462018770+0800)/
     * AcceptanceTimeStr : 2020-09-07
     * HappeningTime : /Date(1599461904000+0800)/
     * HappeningTimeStr : 2020-09-07
     * HappeningPlaceAddress : 1
     * PointInJson : {"lng":117.166263,"lat":34.284459}
     * ComprehensiveLawEnforcementTargetId : a7a549f7-efe2-49e0-a8e7-bc2a40620f74
     * DepartmentId : 2fbf3e32-ebf4-40d5-918d-9b6a54d9c22b
     * DepartmentFullPath : 徐州市鼓楼区/铜沛街道办事处/沈场社区/沈场村网格/
     * EnumEventSourceStr : 由我发起
     * EnumComprehensiveLawEnforcementCaseStatus : 7
     * EnumComprehensiveLawEnforcementCaseStatusStr : 已归档
     * CompletionTimeStr : 无
     * ComprehensiveLawEnforcementCaseTypeId : 0cd543b5-6f2b-4d55-a1f0-44a29670325f
     * ComprehensiveLawEnforcementCaseTypeName : 案件分类1
     * EnumComprehensiveLawSupervisionStatusStr : 正常
     * Id : 09cc57e4-d4f3-4f09-8020-0e2b5c7915a7
     * CreateTime : /Date(1599461958047+0800)/
     * CreateTimeStr : 2020/09/0714:59
     * UpdateTime : /Date(1599470209257+0800)/
     * UpdateTimeStr : 2020/09/0717:16
     * CreateUId : bb0bfcef-476e-4b85-ac00-0d68e39e81b6
     * UpdateUId : 84c87e39-fc2e-4547-97b3-321aba1a05b9
     * UpdateEmployeeName : 207
     * Memo : 1
     */

    private String ComprehensiveLawEnforcementMattersId;
    private String ComprehensiveLawEnforcementMattersName;
    private String Code;
    private String SourceName;
    private String SourceTel;
    private String SourceAddress;
    private String EnumCaseSourceStr;
    private String ComprehensiveLawEnforcementMattersTypeName;
    private String EnumCaseLevelStr;
    private String isImportantStr;
    private String Title;
    private String TargetName;
    private String TargetAddress;
    private String Cause;
    private String AcceptanceTime;
    private String AcceptanceTimeStr;
    private String HappeningTime;
    private String HappeningTimeStr;
    private String HappeningPlaceAddress;
    private String PointInJson;
    private String ComprehensiveLawEnforcementTargetId;
    private String DepartmentId;
    private String DepartmentFullPath;
    private String EnumEventSourceStr;
    private int EnumComprehensiveLawEnforcementCaseStatus;
    private String EnumComprehensiveLawEnforcementCaseStatusStr;
    private String CompletionTimeStr;
    private String ComprehensiveLawEnforcementCaseTypeId;
    private String ComprehensiveLawEnforcementCaseTypeName;
    private String EnumComprehensiveLawSupervisionStatusStr;
    private String Id;
    private String CreateTime;
    private String CreateTimeStr;
    private String UpdateTime;
    private String UpdateTimeStr;
    private String CreateUId;
    private String UpdateUId;
    private String UpdateEmployeeName;
    private String Memo;

    protected CaseReportedBean(Parcel in) {
        ComprehensiveLawEnforcementMattersId = in.readString();
        ComprehensiveLawEnforcementMattersName = in.readString();
        Code = in.readString();
        SourceName = in.readString();
        SourceTel = in.readString();
        SourceAddress = in.readString();
        EnumCaseSourceStr = in.readString();
        ComprehensiveLawEnforcementMattersTypeName = in.readString();
        EnumCaseLevelStr = in.readString();
        isImportantStr = in.readString();
        Title = in.readString();
        TargetName = in.readString();
        TargetAddress = in.readString();
        Cause = in.readString();
        AcceptanceTime = in.readString();
        AcceptanceTimeStr = in.readString();
        HappeningTime = in.readString();
        HappeningTimeStr = in.readString();
        HappeningPlaceAddress = in.readString();
        PointInJson = in.readString();
        ComprehensiveLawEnforcementTargetId = in.readString();
        DepartmentId = in.readString();
        DepartmentFullPath = in.readString();
        EnumEventSourceStr = in.readString();
        EnumComprehensiveLawEnforcementCaseStatus = in.readInt();
        EnumComprehensiveLawEnforcementCaseStatusStr = in.readString();
        CompletionTimeStr = in.readString();
        ComprehensiveLawEnforcementCaseTypeId = in.readString();
        ComprehensiveLawEnforcementCaseTypeName = in.readString();
        EnumComprehensiveLawSupervisionStatusStr = in.readString();
        Id = in.readString();
        CreateTime = in.readString();
        CreateTimeStr = in.readString();
        UpdateTime = in.readString();
        UpdateTimeStr = in.readString();
        CreateUId = in.readString();
        UpdateUId = in.readString();
        UpdateEmployeeName = in.readString();
        Memo = in.readString();
    }

    public static final Creator<CaseReportedBean> CREATOR = new Creator<CaseReportedBean>() {
        @Override
        public CaseReportedBean createFromParcel(Parcel in) {
            return new CaseReportedBean(in);
        }

        @Override
        public CaseReportedBean[] newArray(int size) {
            return new CaseReportedBean[size];
        }
    };

    public String getComprehensiveLawEnforcementMattersId() {
        return ComprehensiveLawEnforcementMattersId;
    }

    public void setComprehensiveLawEnforcementMattersId(String ComprehensiveLawEnforcementMattersId) {
        this.ComprehensiveLawEnforcementMattersId = ComprehensiveLawEnforcementMattersId;
    }

    public String getComprehensiveLawEnforcementMattersName() {
        return ComprehensiveLawEnforcementMattersName;
    }

    public void setComprehensiveLawEnforcementMattersName(String ComprehensiveLawEnforcementMattersName) {
        this.ComprehensiveLawEnforcementMattersName = ComprehensiveLawEnforcementMattersName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String SourceName) {
        this.SourceName = SourceName;
    }

    public String getSourceTel() {
        return SourceTel;
    }

    public void setSourceTel(String SourceTel) {
        this.SourceTel = SourceTel;
    }

    public String getSourceAddress() {
        return SourceAddress;
    }

    public void setSourceAddress(String SourceAddress) {
        this.SourceAddress = SourceAddress;
    }

    public String getEnumCaseSourceStr() {
        return EnumCaseSourceStr;
    }

    public void setEnumCaseSourceStr(String EnumCaseSourceStr) {
        this.EnumCaseSourceStr = EnumCaseSourceStr;
    }

    public String getComprehensiveLawEnforcementMattersTypeName() {
        return ComprehensiveLawEnforcementMattersTypeName;
    }

    public void setComprehensiveLawEnforcementMattersTypeName(String ComprehensiveLawEnforcementMattersTypeName) {
        this.ComprehensiveLawEnforcementMattersTypeName = ComprehensiveLawEnforcementMattersTypeName;
    }

    public String getEnumCaseLevelStr() {
        return EnumCaseLevelStr;
    }

    public void setEnumCaseLevelStr(String EnumCaseLevelStr) {
        this.EnumCaseLevelStr = EnumCaseLevelStr;
    }

    public String getIsImportantStr() {
        return isImportantStr;
    }

    public void setIsImportantStr(String isImportantStr) {
        this.isImportantStr = isImportantStr;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTargetName() {
        return TargetName;
    }

    public void setTargetName(String TargetName) {
        this.TargetName = TargetName;
    }

    public String getTargetAddress() {
        return TargetAddress;
    }

    public void setTargetAddress(String TargetAddress) {
        this.TargetAddress = TargetAddress;
    }

    public String getCause() {
        return Cause;
    }

    public void setCause(String Cause) {
        this.Cause = Cause;
    }

    public String getAcceptanceTime() {
        return AcceptanceTime;
    }

    public void setAcceptanceTime(String AcceptanceTime) {
        this.AcceptanceTime = AcceptanceTime;
    }

    public String getAcceptanceTimeStr() {
        return AcceptanceTimeStr;
    }

    public void setAcceptanceTimeStr(String AcceptanceTimeStr) {
        this.AcceptanceTimeStr = AcceptanceTimeStr;
    }

    public String getHappeningTime() {
        return HappeningTime;
    }

    public void setHappeningTime(String HappeningTime) {
        this.HappeningTime = HappeningTime;
    }

    public String getHappeningTimeStr() {
        return HappeningTimeStr;
    }

    public void setHappeningTimeStr(String HappeningTimeStr) {
        this.HappeningTimeStr = HappeningTimeStr;
    }

    public String getHappeningPlaceAddress() {
        return HappeningPlaceAddress;
    }

    public void setHappeningPlaceAddress(String HappeningPlaceAddress) {
        this.HappeningPlaceAddress = HappeningPlaceAddress;
    }

    public String getPointInJson() {
        return PointInJson;
    }

    public void setPointInJson(String PointInJson) {
        this.PointInJson = PointInJson;
    }

    public String getComprehensiveLawEnforcementTargetId() {
        return ComprehensiveLawEnforcementTargetId;
    }

    public void setComprehensiveLawEnforcementTargetId(String ComprehensiveLawEnforcementTargetId) {
        this.ComprehensiveLawEnforcementTargetId = ComprehensiveLawEnforcementTargetId;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getDepartmentFullPath() {
        return DepartmentFullPath;
    }

    public void setDepartmentFullPath(String DepartmentFullPath) {
        this.DepartmentFullPath = DepartmentFullPath;
    }

    public String getEnumEventSourceStr() {
        return EnumEventSourceStr;
    }

    public void setEnumEventSourceStr(String EnumEventSourceStr) {
        this.EnumEventSourceStr = EnumEventSourceStr;
    }

    public int getEnumComprehensiveLawEnforcementCaseStatus() {
        return EnumComprehensiveLawEnforcementCaseStatus;
    }

    public void setEnumComprehensiveLawEnforcementCaseStatus(int EnumComprehensiveLawEnforcementCaseStatus) {
        this.EnumComprehensiveLawEnforcementCaseStatus = EnumComprehensiveLawEnforcementCaseStatus;
    }

    public String getEnumComprehensiveLawEnforcementCaseStatusStr() {
        return EnumComprehensiveLawEnforcementCaseStatusStr;
    }

    public void setEnumComprehensiveLawEnforcementCaseStatusStr(String EnumComprehensiveLawEnforcementCaseStatusStr) {
        this.EnumComprehensiveLawEnforcementCaseStatusStr = EnumComprehensiveLawEnforcementCaseStatusStr;
    }

    public String getCompletionTimeStr() {
        return CompletionTimeStr;
    }

    public void setCompletionTimeStr(String CompletionTimeStr) {
        this.CompletionTimeStr = CompletionTimeStr;
    }

    public String getComprehensiveLawEnforcementCaseTypeId() {
        return ComprehensiveLawEnforcementCaseTypeId;
    }

    public void setComprehensiveLawEnforcementCaseTypeId(String ComprehensiveLawEnforcementCaseTypeId) {
        this.ComprehensiveLawEnforcementCaseTypeId = ComprehensiveLawEnforcementCaseTypeId;
    }

    public String getComprehensiveLawEnforcementCaseTypeName() {
        return ComprehensiveLawEnforcementCaseTypeName;
    }

    public void setComprehensiveLawEnforcementCaseTypeName(String ComprehensiveLawEnforcementCaseTypeName) {
        this.ComprehensiveLawEnforcementCaseTypeName = ComprehensiveLawEnforcementCaseTypeName;
    }

    public String getEnumComprehensiveLawSupervisionStatusStr() {
        return EnumComprehensiveLawSupervisionStatusStr;
    }

    public void setEnumComprehensiveLawSupervisionStatusStr(String EnumComprehensiveLawSupervisionStatusStr) {
        this.EnumComprehensiveLawSupervisionStatusStr = EnumComprehensiveLawSupervisionStatusStr;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ComprehensiveLawEnforcementMattersId);
        dest.writeString(ComprehensiveLawEnforcementMattersName);
        dest.writeString(Code);
        dest.writeString(SourceName);
        dest.writeString(SourceTel);
        dest.writeString(SourceAddress);
        dest.writeString(EnumCaseSourceStr);
        dest.writeString(ComprehensiveLawEnforcementMattersTypeName);
        dest.writeString(EnumCaseLevelStr);
        dest.writeString(isImportantStr);
        dest.writeString(Title);
        dest.writeString(TargetName);
        dest.writeString(TargetAddress);
        dest.writeString(Cause);
        dest.writeString(AcceptanceTime);
        dest.writeString(AcceptanceTimeStr);
        dest.writeString(HappeningTime);
        dest.writeString(HappeningTimeStr);
        dest.writeString(HappeningPlaceAddress);
        dest.writeString(PointInJson);
        dest.writeString(ComprehensiveLawEnforcementTargetId);
        dest.writeString(DepartmentId);
        dest.writeString(DepartmentFullPath);
        dest.writeString(EnumEventSourceStr);
        dest.writeInt(EnumComprehensiveLawEnforcementCaseStatus);
        dest.writeString(EnumComprehensiveLawEnforcementCaseStatusStr);
        dest.writeString(CompletionTimeStr);
        dest.writeString(ComprehensiveLawEnforcementCaseTypeId);
        dest.writeString(ComprehensiveLawEnforcementCaseTypeName);
        dest.writeString(EnumComprehensiveLawSupervisionStatusStr);
        dest.writeString(Id);
        dest.writeString(CreateTime);
        dest.writeString(CreateTimeStr);
        dest.writeString(UpdateTime);
        dest.writeString(UpdateTimeStr);
        dest.writeString(CreateUId);
        dest.writeString(UpdateUId);
        dest.writeString(UpdateEmployeeName);
        dest.writeString(Memo);
    }
}

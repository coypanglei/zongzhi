package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2020/8/15 13:06
 */
public class LawDetailBean {

    /**
     * Case : {"ComprehensiveLawEnforcementMattersId":"24cfbe34-edbc-4a85-834b-531b85876049","ComprehensiveLawEnforcementMattersName":"占道经营","Code":"ZGZD000009","SourceName":"dsfds","SourceTel":"23423","SourceAddress":"dfgfdfgdfg","EnumCaseSource":2,"EnumCaseSourceStr":"交办","ComprehensiveLawEnforcementMattersTypeName":"城市管理","EnumCaseLevel":1,"EnumCaseLevelStr":"中等","isImportant":true,"isImportantStr":"是","Title":"sdfdsf","TargetName":"sdfsd","TargetAddress":"23sdfsd","Cause":"dfgdf","CreateTime":"/Date(1597463567930)/","CreateTimeStr":"2020-08-15","HappeningTime":"/Date(1597463280000)/","HappeningTimeStr":"2020-08-15","HappeningPlaceAddress":"中国江苏省徐州市云龙区大龙湖街道镜泊东路","PointInJson":"{\"lat\":117.292349,\"lng\":34.210142}","ComprehensiveLawEnforcementTargetId":"1146a747-f6bb-4e8b-a084-0113059bad18","DepartmentId":"b90cdce0-efa1-4569-9bc2-a1713974395d","DepartmentFullPath":"徐州市鼓楼区/","EnumEventSource":0,"EnumEventSourceStr":"由我发起","SourceId":null,"EnumComprehensiveLawEnforcementCaseStatus":1,"EnumComprehensiveLawEnforcementCaseStatusStr":"受理","CompletionTime":null,"CompletionTimeStr":"无","ComprehensiveLawEnforcementCaseTypeId":"ac8be863-3b9d-4211-9b88-3936b51ccd72","ComprehensiveLawEnforcementCaseTypeName":"案件分类1","Id":"110f615e-c3d9-4449-838a-6725423de5ae","UpdateTime":"/Date(1597463567930)/","UpdateTimeStr":"2020/08/15 11:52","CreateUId":"4fedb7b2-a3dd-4ac4-b49d-363a1157ad75","CreateEmployeeName":null,"UpdateUId":"4fedb7b2-a3dd-4ac4-b49d-363a1157ad75","UpdateEmployeeName":null,"Memo":null}
     * Processes : [{"CurrentStepStatus":1,"CurrentStepStatusStr":"受理","Memo":"sdfsdfasfasdfsdfdsfa","CreateUserId":"4fedb7b2-a3dd-4ac4-b49d-363a1157ad75","CreateSignature":"dsfasd","Enclosure":"[\"/Uploads/CustomerData/image/MuMu20200529120253637330889351252694.png\"]","NextStepStatus":3,"NextStepStatusStr":"取证","NextStepDepartId":"90265727-7206-49d0-9341-371dc9d2b898","NextStepHandlerId":"e6d71e19-e649-4b18-8f85-15bc7631613a","NextStepDeadlineTime":"/Date(1597507200000)/","NextStepDeadlineTimeStr":"2020-08-16","ComprehensiveLawEnforcementCaseId":"110f615e-c3d9-4449-838a-6725423de5ae","NextStepHandlerName":"鼓楼区消防局","CreateUserName":"残疾人联合会","Id":"92afffb0-c811-4e49-82b3-08bf14d3ccf2","CreateTime":"/Date(1597463567930)/","CreateTimeStr":"2020/08/15 11:52","UpdateTime":"/Date(1581729693080)/","UpdateTimeStr":"2020/02/15 09:21","CreateUId":"4fedb7b2-a3dd-4ac4-b49d-363a1157ad75","CreateEmployeeName":"残疾人联合会","UpdateUId":"4fedb7b2-a3dd-4ac4-b49d-363a1157ad75","UpdateEmployeeName":"残疾人联合会"}]
     * Processable : false
     * Target : {"EnumPartiesTypes":0,"EnumPartiesTypesStr":"自然人","Name":"sdfsdf","SId":"12312","Address":"12312","Tel":null,"PinYin":"sdfsdf","personInCharge":null,"duty":null,"BirthDate":null,"BirthDateStr":"无","EnumGender":0,"EnumGenderStr":"不确定","Age":0,"Id":"fc1948e0-6929-4842-b911-89b4b6639bca","CreateTime":"/Date(1597214474380)/","CreateTimeStr":"2020/08/12 14:41","UpdateTime":"/Date(1597214474380)/","UpdateTimeStr":"2020/08/12 14:41","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":null}
     */

    private CaseBean Case;
    private boolean Processable;
    private TargetBean Target;
    private List<ProcessesBean> Processes;

    public CaseBean getCase() {
        return Case;
    }

    public void setCase(CaseBean Case) {
        this.Case = Case;
    }

    public boolean isProcessable() {
        return Processable;
    }

    public void setProcessable(boolean Processable) {
        this.Processable = Processable;
    }

    public TargetBean getTarget() {
        return Target;
    }

    public void setTarget(TargetBean Target) {
        this.Target = Target;
    }

    public List<ProcessesBean> getProcesses() {
        return Processes;
    }

    public void setProcesses(List<ProcessesBean> Processes) {
        this.Processes = Processes;
    }

    public static class CaseBean {
        /**
         * ComprehensiveLawEnforcementMattersId : 24cfbe34-edbc-4a85-834b-531b85876049
         * ComprehensiveLawEnforcementMattersName : 占道经营
         * Code : ZGZD000009
         * SourceName : dsfds
         * SourceTel : 23423
         * SourceAddress : dfgfdfgdfg
         * EnumCaseSource : 2
         * EnumCaseSourceStr : 交办
         * ComprehensiveLawEnforcementMattersTypeName : 城市管理
         * EnumCaseLevel : 1
         * EnumCaseLevelStr : 中等
         * isImportant : true
         * isImportantStr : 是
         * Title : sdfdsf
         * TargetName : sdfsd
         * TargetAddress : 23sdfsd
         * Cause : dfgdf
         * CreateTime : /Date(1597463567930)/
         * CreateTimeStr : 2020-08-15
         * HappeningTime : /Date(1597463280000)/
         * HappeningTimeStr : 2020-08-15
         * HappeningPlaceAddress : 中国江苏省徐州市云龙区大龙湖街道镜泊东路
         * PointInJson : {"lat":117.292349,"lng":34.210142}
         * ComprehensiveLawEnforcementTargetId : 1146a747-f6bb-4e8b-a084-0113059bad18
         * DepartmentId : b90cdce0-efa1-4569-9bc2-a1713974395d
         * DepartmentFullPath : 徐州市鼓楼区/
         * EnumEventSource : 0
         * EnumEventSourceStr : 由我发起
         * SourceId : null
         * EnumComprehensiveLawEnforcementCaseStatus : 1
         * EnumComprehensiveLawEnforcementCaseStatusStr : 受理
         * CompletionTime : null
         * CompletionTimeStr : 无
         * ComprehensiveLawEnforcementCaseTypeId : ac8be863-3b9d-4211-9b88-3936b51ccd72
         * ComprehensiveLawEnforcementCaseTypeName : 案件分类1
         * Id : 110f615e-c3d9-4449-838a-6725423de5ae
         * UpdateTime : /Date(1597463567930)/
         * UpdateTimeStr : 2020/08/15 11:52
         * CreateUId : 4fedb7b2-a3dd-4ac4-b49d-363a1157ad75
         * CreateEmployeeName : null
         * UpdateUId : 4fedb7b2-a3dd-4ac4-b49d-363a1157ad75
         * UpdateEmployeeName : null
         * Memo : null
         */

        private String ComprehensiveLawEnforcementMattersId;
        private String ComprehensiveLawEnforcementMattersName;
        private String Code;
        private String SourceName;
        private String SourceTel;
        private String SourceAddress;
        private int EnumCaseSource;
        private String EnumCaseSourceStr;
        private String ComprehensiveLawEnforcementMattersTypeName;
        private int EnumCaseLevel;
        private String EnumCaseLevelStr;
        private boolean isImportant;
        private String isImportantStr;
        private String Title;
        private String TargetName;
        private String TargetAddress;
        private String Cause;
        private String CreateTime;
        private String CreateTimeStr;
        private String HappeningTime;
        private String HappeningTimeStr;
        private String HappeningPlaceAddress;
        private String PointInJson;
        private String ComprehensiveLawEnforcementTargetId;
        private String DepartmentId;
        private String DepartmentFullPath;
        private int EnumEventSource;
        private String EnumEventSourceStr;
        private String SourceId;
        private int EnumComprehensiveLawEnforcementCaseStatus;
        private String EnumComprehensiveLawEnforcementCaseStatusStr;
        private String CompletionTime;
        private String CompletionTimeStr;
        private String ComprehensiveLawEnforcementCaseTypeId;
        private String ComprehensiveLawEnforcementCaseTypeName;
        private String Id;
        private String UpdateTime;
        private String UpdateTimeStr;
        private String CreateUId;
        private String CreateEmployeeName;
        private String UpdateUId;
        private String UpdateEmployeeName;
        private String Memo;

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

        public int getEnumCaseSource() {
            return EnumCaseSource;
        }

        public void setEnumCaseSource(int EnumCaseSource) {
            this.EnumCaseSource = EnumCaseSource;
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

        public int getEnumCaseLevel() {
            return EnumCaseLevel;
        }

        public void setEnumCaseLevel(int EnumCaseLevel) {
            this.EnumCaseLevel = EnumCaseLevel;
        }

        public String getEnumCaseLevelStr() {
            return EnumCaseLevelStr;
        }

        public void setEnumCaseLevelStr(String EnumCaseLevelStr) {
            this.EnumCaseLevelStr = EnumCaseLevelStr;
        }

        public boolean isIsImportant() {
            return isImportant;
        }

        public void setIsImportant(boolean isImportant) {
            this.isImportant = isImportant;
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

        public int getEnumEventSource() {
            return EnumEventSource;
        }

        public void setEnumEventSource(int EnumEventSource) {
            this.EnumEventSource = EnumEventSource;
        }

        public String getEnumEventSourceStr() {
            return EnumEventSourceStr;
        }

        public void setEnumEventSourceStr(String EnumEventSourceStr) {
            this.EnumEventSourceStr = EnumEventSourceStr;
        }

        public String getSourceId() {
            return SourceId;
        }

        public void setSourceId(String SourceId) {
            this.SourceId = SourceId;
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

        public String getCompletionTime() {
            return CompletionTime;
        }

        public void setCompletionTime(String CompletionTime) {
            this.CompletionTime = CompletionTime;
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

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
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
    }

    public static class TargetBean {
        /**
         * EnumPartiesTypes : 0
         * EnumPartiesTypesStr : 自然人
         * Name : sdfsdf
         * SId : 12312
         * Address : 12312
         * Tel : null
         * PinYin : sdfsdf
         * personInCharge : null
         * duty : null
         * BirthDate : null
         * BirthDateStr : 无
         * EnumGender : 0
         * EnumGenderStr : 不确定
         * Age : 0
         * Id : fc1948e0-6929-4842-b911-89b4b6639bca
         * CreateTime : /Date(1597214474380)/
         * CreateTimeStr : 2020/08/12 14:41
         * UpdateTime : /Date(1597214474380)/
         * UpdateTimeStr : 2020/08/12 14:41
         * CreateUId : 00000000-0000-0000-0000-000000000000
         * CreateEmployeeName : null
         * UpdateUId : 00000000-0000-0000-0000-000000000000
         * UpdateEmployeeName : null
         * Memo : null
         */

        private int EnumPartiesTypes;
        private String EnumPartiesTypesStr;
        private String Name;
        private String SId;
        private String Address;
        private String Tel;
        private String PinYin;
        private String personInCharge;
        private String duty;
        private String BirthDate;
        private String BirthDateStr;
        private int EnumGender;
        private String EnumGenderStr;
        private int Age;
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

        public int getEnumGender() {
            return EnumGender;
        }

        public void setEnumGender(int EnumGender) {
            this.EnumGender = EnumGender;
        }

        public String getEnumGenderStr() {
            return EnumGenderStr;
        }

        public void setEnumGenderStr(String EnumGenderStr) {
            this.EnumGenderStr = EnumGenderStr;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
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
    }

    public static class ProcessesBean {
        /**
         * CurrentStepStatus : 1
         * CurrentStepStatusStr : 受理
         * Memo : sdfsdfasfasdfsdfdsfa
         * CreateUserId : 4fedb7b2-a3dd-4ac4-b49d-363a1157ad75
         * CreateSignature : dsfasd
         * Enclosure : ["/Uploads/CustomerData/image/MuMu20200529120253637330889351252694.png"]
         * NextStepStatus : 3
         * NextStepStatusStr : 取证
         * NextStepDepartId : 90265727-7206-49d0-9341-371dc9d2b898
         * NextStepHandlerId : e6d71e19-e649-4b18-8f85-15bc7631613a
         * NextStepDeadlineTime : /Date(1597507200000)/
         * NextStepDeadlineTimeStr : 2020-08-16
         * ComprehensiveLawEnforcementCaseId : 110f615e-c3d9-4449-838a-6725423de5ae
         * NextStepHandlerName : 鼓楼区消防局
         * CreateUserName : 残疾人联合会
         * Id : 92afffb0-c811-4e49-82b3-08bf14d3ccf2
         * CreateTime : /Date(1597463567930)/
         * CreateTimeStr : 2020/08/15 11:52
         * UpdateTime : /Date(1581729693080)/
         * UpdateTimeStr : 2020/02/15 09:21
         * CreateUId : 4fedb7b2-a3dd-4ac4-b49d-363a1157ad75
         * CreateEmployeeName : 残疾人联合会
         * UpdateUId : 4fedb7b2-a3dd-4ac4-b49d-363a1157ad75
         * UpdateEmployeeName : 残疾人联合会
         */

        private int CurrentStepStatus;
        private String CurrentStepStatusStr;
        private String Memo;
        private String CreateUserId;
        private String CreateSignature;
        private String Enclosure;
        private int NextStepStatus;
        private String NextStepStatusStr;
        private String NextStepDepartId;
        private String NextStepHandlerId;
        private String NextStepDeadlineTime;
        private String NextStepDeadlineTimeStr;
        private String ComprehensiveLawEnforcementCaseId;
        private String NextStepHandlerName;
        private String CreateUserName;
        private String Id;
        private String CreateTime;
        private String CreateTimeStr;
        private String UpdateTime;
        private String UpdateTimeStr;
        private String CreateUId;
        private String CreateEmployeeName;
        private String UpdateUId;
        private String UpdateEmployeeName;

        public int getCurrentStepStatus() {
            return CurrentStepStatus;
        }

        public void setCurrentStepStatus(int CurrentStepStatus) {
            this.CurrentStepStatus = CurrentStepStatus;
        }

        public String getCurrentStepStatusStr() {
            return CurrentStepStatusStr;
        }

        public void setCurrentStepStatusStr(String CurrentStepStatusStr) {
            this.CurrentStepStatusStr = CurrentStepStatusStr;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public String getCreateUserId() {
            return CreateUserId;
        }

        public void setCreateUserId(String CreateUserId) {
            this.CreateUserId = CreateUserId;
        }

        public String getCreateSignature() {
            return CreateSignature;
        }

        public void setCreateSignature(String CreateSignature) {
            this.CreateSignature = CreateSignature;
        }

        public String getEnclosure() {
            return Enclosure;
        }

        public void setEnclosure(String Enclosure) {
            this.Enclosure = Enclosure;
        }

        public int getNextStepStatus() {
            return NextStepStatus;
        }

        public void setNextStepStatus(int NextStepStatus) {
            this.NextStepStatus = NextStepStatus;
        }

        public String getNextStepStatusStr() {
            return NextStepStatusStr;
        }

        public void setNextStepStatusStr(String NextStepStatusStr) {
            this.NextStepStatusStr = NextStepStatusStr;
        }

        public String getNextStepDepartId() {
            return NextStepDepartId;
        }

        public void setNextStepDepartId(String NextStepDepartId) {
            this.NextStepDepartId = NextStepDepartId;
        }

        public String getNextStepHandlerId() {
            return NextStepHandlerId;
        }

        public void setNextStepHandlerId(String NextStepHandlerId) {
            this.NextStepHandlerId = NextStepHandlerId;
        }

        public String getNextStepDeadlineTime() {
            return NextStepDeadlineTime;
        }

        public void setNextStepDeadlineTime(String NextStepDeadlineTime) {
            this.NextStepDeadlineTime = NextStepDeadlineTime;
        }

        public String getNextStepDeadlineTimeStr() {
            return NextStepDeadlineTimeStr;
        }

        public void setNextStepDeadlineTimeStr(String NextStepDeadlineTimeStr) {
            this.NextStepDeadlineTimeStr = NextStepDeadlineTimeStr;
        }

        public String getComprehensiveLawEnforcementCaseId() {
            return ComprehensiveLawEnforcementCaseId;
        }

        public void setComprehensiveLawEnforcementCaseId(String ComprehensiveLawEnforcementCaseId) {
            this.ComprehensiveLawEnforcementCaseId = ComprehensiveLawEnforcementCaseId;
        }

        public String getNextStepHandlerName() {
            return NextStepHandlerName;
        }

        public void setNextStepHandlerName(String NextStepHandlerName) {
            this.NextStepHandlerName = NextStepHandlerName;
        }

        public String getCreateUserName() {
            return CreateUserName;
        }

        public void setCreateUserName(String CreateUserName) {
            this.CreateUserName = CreateUserName;
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
    }
}

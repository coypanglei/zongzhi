package com.weique.overhaul.v2.mvp.model.entity;

public class LawWorksOrderDetailBean {


    private CODetailBean CODetail;
    private CORecordBean CORecord;

    public CODetailBean getCODetail() {
        return CODetail;
    }

    public void setCODetail(CODetailBean CODetail) {
        this.CODetail = CODetail;
    }

    public CORecordBean getCORecord() {
        return CORecord;
    }

    public void setCORecord(CORecordBean CORecord) {
        this.CORecord = CORecord;
    }

    public static class CODetailBean {
        /**
         * CourtOrderTypeName : 法律文书送达123
         * Code : DCD000001
         * Title : 测试订单
         * EventAddress : 徐州
         * DepartmentId : 00000000-0000-0000-0000-000000000000
         * DepartmentFullPath : 徐州市铜山区/街道测试一/测试网格/测试网格0414jh
         * CourtOrderSourcenName : 借款合同纠纷
         * CourtCode : null
         * LawerUserId : 00000000-0000-0000-0000-000000000000
         * LawerName : 100100
         * LawerTel : null
         * Applicant : null
         * ApplicantTel : null
         * ExecutedUser : null
         * ExecutedUserIDCard : null
         * EnumExecutedUserJob : 0
         * EnumExecutedUserJobStr : 无业
         * ExecutedUserNation : null
         * ExecutedUserGender : 0
         * ExecutedUserGenderStr : 不确定
         * ExecutedUserDate : /Date(1587830400000)/
         * ExecutedUserDateStr : 2020-04-26
         * ExecutedUseTel : null
         * EndDate : /Date(1587830400000)/
         * EndDateStr : 2020-04-26
         * Requirement : null
         * Description : null
         * ImgsInJson : ["http://localhost:44311/Uploads/CustomerData/image/file_1587867950000637234935506413854.jpg","http://localhost:44311/Uploads/CustomerData/image/file_1587867950000637234935512138535.jpg"]
         * RecordUrl : ["/Uploads/CustomerData/image/测试文档637234935571749018.doc","/Uploads/CustomerData/image/测试文档11637234935576915192.doc"]
         * OrderStatus : 3
         * OrderStatusStr : 已审核
         * EnumSourceType : 0
         * EnumSourceTypeStr : 协助执行事项
         * Id : b1f3b41a-146c-4c65-b17c-cc2e13a2d7bb
         * CreateTime : /Date(1587867981023)/
         * CreateTimeStr : 2020/04/26 10:26
         * UpdateTime : /Date(1587867981023)/
         * UpdateTimeStr : 2020/04/26 10:26
         * CreateUId : 00000000-0000-0000-0000-000000000000
         * CreateEmployeeName : 测试管理员
         * UpdateUId : 00000000-0000-0000-0000-000000000000
         * UpdateEmployeeName : 测试管理员
         * Memo : null
         */

        private String CourtOrderTypeName;
        private String Code;
        private String Title;
        private String EventAddress;
        private String DepartmentId;
        private String DepartmentFullPath;
        private String CourtOrderSourcenName;
        private String CourtCode;
        private String LawerUserId;
        private String LawerName;
        private String LawerTel;
        private String Applicant;
        private String ApplicantTel;
        private String ExecutedUser;
        private String ExecutedUserIDCard;
        private int EnumExecutedUserJob;
        private String EnumExecutedUserJobStr;
        private String ExecutedUserNation;
        private int ExecutedUserGender;
        private String ExecutedUserGenderStr;
        private String ExecutedUserDate;
        private String ExecutedUserDateStr;
        private String ExecutedUseTel;
        private String EndDate;
        private String EndDateStr;
        private String Requirement;
        private String Description;
        private String ImgsInJson;
        private String RecordUrl;
        private int OrderStatus;
        private String OrderStatusStr;
        private int EnumSourceType;
        private String EnumSourceTypeStr;
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

        public String getCourtOrderTypeName() {
            return CourtOrderTypeName;
        }

        public void setCourtOrderTypeName(String CourtOrderTypeName) {
            this.CourtOrderTypeName = CourtOrderTypeName;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getEventAddress() {
            return EventAddress;
        }

        public void setEventAddress(String EventAddress) {
            this.EventAddress = EventAddress;
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

        public String getCourtOrderSourcenName() {
            return CourtOrderSourcenName;
        }

        public void setCourtOrderSourcenName(String CourtOrderSourcenName) {
            this.CourtOrderSourcenName = CourtOrderSourcenName;
        }

        public String getCourtCode() {
            return CourtCode;
        }

        public void setCourtCode(String CourtCode) {
            this.CourtCode = CourtCode;
        }

        public String getLawerUserId() {
            return LawerUserId;
        }

        public void setLawerUserId(String LawerUserId) {
            this.LawerUserId = LawerUserId;
        }

        public String getLawerName() {
            return LawerName;
        }

        public void setLawerName(String LawerName) {
            this.LawerName = LawerName;
        }

        public String getLawerTel() {
            return LawerTel;
        }

        public void setLawerTel(String LawerTel) {
            this.LawerTel = LawerTel;
        }

        public String getApplicant() {
            return Applicant;
        }

        public void setApplicant(String Applicant) {
            this.Applicant = Applicant;
        }

        public String getApplicantTel() {
            return ApplicantTel;
        }

        public void setApplicantTel(String ApplicantTel) {
            this.ApplicantTel = ApplicantTel;
        }

        public String getExecutedUser() {
            return ExecutedUser;
        }

        public void setExecutedUser(String ExecutedUser) {
            this.ExecutedUser = ExecutedUser;
        }

        public String getExecutedUserIDCard() {
            return ExecutedUserIDCard;
        }

        public void setExecutedUserIDCard(String ExecutedUserIDCard) {
            this.ExecutedUserIDCard = ExecutedUserIDCard;
        }

        public int getEnumExecutedUserJob() {
            return EnumExecutedUserJob;
        }

        public void setEnumExecutedUserJob(int EnumExecutedUserJob) {
            this.EnumExecutedUserJob = EnumExecutedUserJob;
        }

        public String getEnumExecutedUserJobStr() {
            return EnumExecutedUserJobStr;
        }

        public void setEnumExecutedUserJobStr(String EnumExecutedUserJobStr) {
            this.EnumExecutedUserJobStr = EnumExecutedUserJobStr;
        }

        public String getExecutedUserNation() {
            return ExecutedUserNation;
        }

        public void setExecutedUserNation(String ExecutedUserNation) {
            this.ExecutedUserNation = ExecutedUserNation;
        }

        public int getExecutedUserGender() {
            return ExecutedUserGender;
        }

        public void setExecutedUserGender(int ExecutedUserGender) {
            this.ExecutedUserGender = ExecutedUserGender;
        }

        public String getExecutedUserGenderStr() {
            return ExecutedUserGenderStr;
        }

        public void setExecutedUserGenderStr(String ExecutedUserGenderStr) {
            this.ExecutedUserGenderStr = ExecutedUserGenderStr;
        }

        public String getExecutedUserDate() {
            return ExecutedUserDate;
        }

        public void setExecutedUserDate(String ExecutedUserDate) {
            this.ExecutedUserDate = ExecutedUserDate;
        }

        public String getExecutedUserDateStr() {
            return ExecutedUserDateStr;
        }

        public void setExecutedUserDateStr(String ExecutedUserDateStr) {
            this.ExecutedUserDateStr = ExecutedUserDateStr;
        }

        public String getExecutedUseTel() {
            return ExecutedUseTel;
        }

        public void setExecutedUseTel(String ExecutedUseTel) {
            this.ExecutedUseTel = ExecutedUseTel;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public String getEndDateStr() {
            return EndDateStr;
        }

        public void setEndDateStr(String EndDateStr) {
            this.EndDateStr = EndDateStr;
        }

        public String getRequirement() {
            return Requirement;
        }

        public void setRequirement(String Requirement) {
            this.Requirement = Requirement;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getImgsInJson() {
            return ImgsInJson;
        }

        public void setImgsInJson(String ImgsInJson) {
            this.ImgsInJson = ImgsInJson;
        }

        public String getRecordUrl() {
            return RecordUrl;
        }

        public void setRecordUrl(String RecordUrl) {
            this.RecordUrl = RecordUrl;
        }

        public int getOrderStatus() {
            return OrderStatus;
        }

        public void setOrderStatus(int OrderStatus) {
            this.OrderStatus = OrderStatus;
        }

        public String getOrderStatusStr() {
            return OrderStatusStr;
        }

        public void setOrderStatusStr(String OrderStatusStr) {
            this.OrderStatusStr = OrderStatusStr;
        }

        public int getEnumSourceType() {
            return EnumSourceType;
        }

        public void setEnumSourceType(int EnumSourceType) {
            this.EnumSourceType = EnumSourceType;
        }

        public String getEnumSourceTypeStr() {
            return EnumSourceTypeStr;
        }

        public void setEnumSourceTypeStr(String EnumSourceTypeStr) {
            this.EnumSourceTypeStr = EnumSourceTypeStr;
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

    public static class CORecordBean {
        /**
         * CourtOrderID : b1f3b41a-146c-4c65-b17c-cc2e13a2d7bb
         * Title : 测试订单
         * Content : null
         * OrderStatus : 2
         * OrderStatusStr : 提交
         * ImgsInJson : null
         * RecordUrl : null
         * Id : 6fb3ef5d-ab2b-4f45-b252-5f527e1b8ac1
         * CreateTime : /Date(1587867981023)/
         * CreateTimeStr : 2020/04/26 10:26
         * UpdateTime : /Date(1587867981023)/
         * UpdateTimeStr : 2020/04/26 10:26
         * CreateUId : 9bf43e4b-c7f5-40de-a0a9-b8bce22a427d
         * CreateEmployeeName : 测试管理员
         * UpdateUId : 9bf43e4b-c7f5-40de-a0a9-b8bce22a427d
         * UpdateEmployeeName : 测试管理员
         * Memo : null
         */

        private String CourtOrderID;
        private String Title;
        private String Content;
        private int OrderStatus;
        private String OrderStatusStr;
        private String ImgsInJson;
        private String RecordUrl;
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

        public String getCourtOrderID() {
            return CourtOrderID;
        }

        public void setCourtOrderID(String CourtOrderID) {
            this.CourtOrderID = CourtOrderID;
        }

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

        public int getOrderStatus() {
            return OrderStatus;
        }

        public void setOrderStatus(int OrderStatus) {
            this.OrderStatus = OrderStatus;
        }

        public String getOrderStatusStr() {
            return OrderStatusStr;
        }

        public void setOrderStatusStr(String OrderStatusStr) {
            this.OrderStatusStr = OrderStatusStr;
        }

        public String getImgsInJson() {
            return ImgsInJson;
        }

        public void setImgsInJson(String ImgsInJson) {
            this.ImgsInJson = ImgsInJson;
        }

        public String getRecordUrl() {
            return RecordUrl;
        }

        public void setRecordUrl(String RecordUrl) {
            this.RecordUrl = RecordUrl;
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
}


//    /**
//     * CourtOrderTypeName : 诉前协调
//     * Code : null
//     * Title : 232
//     * EventAddress : 324234
//     * DepartmentId : 00000000-0000-0000-0000-000000000000
//     * DepartmentFullPath : 徐州市铜山区
//     * CourtOrderSourcenName : 借款合同纠纷1
//     * CourtCode : 324234
//     * LawerUserId : 00000000-0000-0000-0000-000000000000
//     * LawerName : 100100
//     * LawerTel : 234234
//     * Applicant : 23423
//     * ApplicantTel : 234
//     * ExecutedUser : null
//     * ExecutedUserIDCard : 23423
//     * EnumExecutedUserJob : 1
//     * EnumExecutedUserJobStr : 公务员
//     * ExecutedUserNation : 23423
//     * ExecutedUserGender : 0
//     * ExecutedUserGenderStr : 不确定
//     * ExecutedUserDate : /Date(-2174716800000)/
//     * ExecutedUserDateStr : 1901-02-02
//     * ExecutedUseTel : wqeqw
//     * EndDate : /Date(1587052800000)/
//     * EndDateStr : 2020-04-17
//     * Requirement : wqe
//     * Description : qwe
//     * ImgsInJson : null
//     * RecordUrl : null
//     * OrderStatus : 1
//     * OrderStatusStr : 已退回
//     * Id : 604b9b09-74e6-466d-9066-1145f277be18
//     * CreateTime : /Date(1587626356830)/
//     * CreateTimeStr : 2020/04/23 15:19
//     * UpdateTime : /Date(1587631137557)/
//     * UpdateTimeStr : 2020/04/23 16:38
//     * CreateUId : 00000000-0000-0000-0000-000000000000
//     * CreateEmployeeName : 测试管理员
//     * UpdateUId : 00000000-0000-0000-0000-000000000000
//     * UpdateEmployeeName : 测试管理员
//     * Memo : null
//     */
//
//    private String CourtOrderTypeName;
//    private String Code;
//    private String Title;
//    private String EventAddress;
//    private String DepartmentId;
//    private String DepartmentFullPath;
//    private String CourtOrderSourcenName;
//    private String CourtCode;
//    private String LawerUserId;
//    private String LawerName;
//    private String LawerTel;
//    private String Applicant;
//    private String ApplicantTel;
//    private String ExecutedUser;
//    private String ExecutedUserIDCard;
//    private int EnumExecutedUserJob;
//    private String EnumExecutedUserJobStr;
//    private String ExecutedUserNation;
//    private int ExecutedUserGender;
//    private String ExecutedUserGenderStr;
//    private String ExecutedUserDate;
//    private String ExecutedUserDateStr;
//    private String ExecutedUseTel;
//    private String EndDate;
//    private String EndDateStr;
//    private String Requirement;
//    private String Description;
//    private String ImgsInJson;
//    private String RecordUrl;
//    private int OrderStatus;
//    private String OrderStatusStr;
//    private String Id;
//    private String CreateTime;
//    private String CreateTimeStr;
//    private String UpdateTime;
//    private String UpdateTimeStr;
//    private String CreateUId;
//    private String CreateEmployeeName;
//    private String UpdateUId;
//    private String UpdateEmployeeName;
//    private String Memo;
//
//    public String getCourtOrderTypeName() {
//        return CourtOrderTypeName;
//    }
//
//    public void setCourtOrderTypeName(String CourtOrderTypeName) {
//        this.CourtOrderTypeName = CourtOrderTypeName;
//    }
//
//    public String getCode() {
//        return Code;
//    }
//
//    public void setCode(String Code) {
//        this.Code = Code;
//    }
//
//    public String getTitle() {
//        return Title;
//    }
//
//    public void setTitle(String Title) {
//        this.Title = Title;
//    }
//
//    public String getEventAddress() {
//        return EventAddress;
//    }
//
//    public void setEventAddress(String EventAddress) {
//        this.EventAddress = EventAddress;
//    }
//
//    public String getDepartmentId() {
//        return DepartmentId;
//    }
//
//    public void setDepartmentId(String DepartmentId) {
//        this.DepartmentId = DepartmentId;
//    }
//
//    public String getDepartmentFullPath() {
//        return DepartmentFullPath;
//    }
//
//    public void setDepartmentFullPath(String DepartmentFullPath) {
//        this.DepartmentFullPath = DepartmentFullPath;
//    }
//
//    public String getCourtOrderSourcenName() {
//        return CourtOrderSourcenName;
//    }
//
//    public void setCourtOrderSourcenName(String CourtOrderSourcenName) {
//        this.CourtOrderSourcenName = CourtOrderSourcenName;
//    }
//
//    public String getCourtCode() {
//        return CourtCode;
//    }
//
//    public void setCourtCode(String CourtCode) {
//        this.CourtCode = CourtCode;
//    }
//
//    public String getLawerUserId() {
//        return LawerUserId;
//    }
//
//    public void setLawerUserId(String LawerUserId) {
//        this.LawerUserId = LawerUserId;
//    }
//
//    public String getLawerName() {
//        return LawerName;
//    }
//
//    public void setLawerName(String LawerName) {
//        this.LawerName = LawerName;
//    }
//
//    public String getLawerTel() {
//        return LawerTel;
//    }
//
//    public void setLawerTel(String LawerTel) {
//        this.LawerTel = LawerTel;
//    }
//
//    public String getApplicant() {
//        return Applicant;
//    }
//
//    public void setApplicant(String Applicant) {
//        this.Applicant = Applicant;
//    }
//
//    public String getApplicantTel() {
//        return ApplicantTel;
//    }
//
//    public void setApplicantTel(String ApplicantTel) {
//        this.ApplicantTel = ApplicantTel;
//    }
//
//    public String getExecutedUser() {
//        return ExecutedUser;
//    }
//
//    public void setExecutedUser(String ExecutedUser) {
//        this.ExecutedUser = ExecutedUser;
//    }
//
//    public String getExecutedUserIDCard() {
//        return ExecutedUserIDCard;
//    }
//
//    public void setExecutedUserIDCard(String ExecutedUserIDCard) {
//        this.ExecutedUserIDCard = ExecutedUserIDCard;
//    }
//
//    public int getEnumExecutedUserJob() {
//        return EnumExecutedUserJob;
//    }
//
//    public void setEnumExecutedUserJob(int EnumExecutedUserJob) {
//        this.EnumExecutedUserJob = EnumExecutedUserJob;
//    }
//
//    public String getEnumExecutedUserJobStr() {
//        return EnumExecutedUserJobStr;
//    }
//
//    public void setEnumExecutedUserJobStr(String EnumExecutedUserJobStr) {
//        this.EnumExecutedUserJobStr = EnumExecutedUserJobStr;
//    }
//
//    public String getExecutedUserNation() {
//        return ExecutedUserNation;
//    }
//
//    public void setExecutedUserNation(String ExecutedUserNation) {
//        this.ExecutedUserNation = ExecutedUserNation;
//    }
//
//    public int getExecutedUserGender() {
//        return ExecutedUserGender;
//    }
//
//    public void setExecutedUserGender(int ExecutedUserGender) {
//        this.ExecutedUserGender = ExecutedUserGender;
//    }
//
//    public String getExecutedUserGenderStr() {
//        return ExecutedUserGenderStr;
//    }
//
//    public void setExecutedUserGenderStr(String ExecutedUserGenderStr) {
//        this.ExecutedUserGenderStr = ExecutedUserGenderStr;
//    }
//
//    public String getExecutedUserDate() {
//        return ExecutedUserDate;
//    }
//
//    public void setExecutedUserDate(String ExecutedUserDate) {
//        this.ExecutedUserDate = ExecutedUserDate;
//    }
//
//    public String getExecutedUserDateStr() {
//        return ExecutedUserDateStr;
//    }
//
//    public void setExecutedUserDateStr(String ExecutedUserDateStr) {
//        this.ExecutedUserDateStr = ExecutedUserDateStr;
//    }
//
//    public String getExecutedUseTel() {
//        return ExecutedUseTel;
//    }
//
//    public void setExecutedUseTel(String ExecutedUseTel) {
//        this.ExecutedUseTel = ExecutedUseTel;
//    }
//
//    public String getEndDate() {
//        return EndDate;
//    }
//
//    public void setEndDate(String EndDate) {
//        this.EndDate = EndDate;
//    }
//
//    public String getEndDateStr() {
//        return EndDateStr;
//    }
//
//    public void setEndDateStr(String EndDateStr) {
//        this.EndDateStr = EndDateStr;
//    }
//
//    public String getRequirement() {
//        return Requirement;
//    }
//
//    public void setRequirement(String Requirement) {
//        this.Requirement = Requirement;
//    }
//
//    public String getDescription() {
//        return Description;
//    }
//
//    public void setDescription(String Description) {
//        this.Description = Description;
//    }
//
//    public String getImgsInJson() {
//        return ImgsInJson;
//    }
//
//    public void setImgsInJson(String ImgsInJson) {
//        this.ImgsInJson = ImgsInJson;
//    }
//
//    public String  getRecordUrl() {
//        return RecordUrl;
//    }
//
//    public void setRecordUrl(String RecordUrl) {
//        this.RecordUrl = RecordUrl;
//    }
//
//    public int getOrderStatus() {
//        return OrderStatus;
//    }
//
//    public void setOrderStatus(int OrderStatus) {
//        this.OrderStatus = OrderStatus;
//    }
//
//    public String getOrderStatusStr() {
//        return OrderStatusStr;
//    }
//
//    public void setOrderStatusStr(String OrderStatusStr) {
//        this.OrderStatusStr = OrderStatusStr;
//    }
//
//    public String getId() {
//        return Id;
//    }
//
//    public void setId(String Id) {
//        this.Id = Id;
//    }
//
//    public String getCreateTime() {
//        return CreateTime;
//    }
//
//    public void setCreateTime(String CreateTime) {
//        this.CreateTime = CreateTime;
//    }
//
//    public String getCreateTimeStr() {
//        return CreateTimeStr;
//    }
//
//    public void setCreateTimeStr(String CreateTimeStr) {
//        this.CreateTimeStr = CreateTimeStr;
//    }
//
//    public String getUpdateTime() {
//        return UpdateTime;
//    }
//
//    public void setUpdateTime(String UpdateTime) {
//        this.UpdateTime = UpdateTime;
//    }
//
//    public String getUpdateTimeStr() {
//        return UpdateTimeStr;
//    }
//
//    public void setUpdateTimeStr(String UpdateTimeStr) {
//        this.UpdateTimeStr = UpdateTimeStr;
//    }
//
//    public String getCreateUId() {
//        return CreateUId;
//    }
//
//    public void setCreateUId(String CreateUId) {
//        this.CreateUId = CreateUId;
//    }
//
//    public String getCreateEmployeeName() {
//        return CreateEmployeeName;
//    }
//
//    public void setCreateEmployeeName(String CreateEmployeeName) {
//        this.CreateEmployeeName = CreateEmployeeName;
//    }
//
//    public String getUpdateUId() {
//        return UpdateUId;
//    }
//
//    public void setUpdateUId(String UpdateUId) {
//        this.UpdateUId = UpdateUId;
//    }
//
//    public String getUpdateEmployeeName() {
//        return UpdateEmployeeName;
//    }
//
//    public void setUpdateEmployeeName(String UpdateEmployeeName) {
//        this.UpdateEmployeeName = UpdateEmployeeName;
//    }
//
//    public String getMemo() {
//        return Memo;
//    }
//
//    public void setMemo(String Memo) {
//        this.Memo = Memo;
//    }




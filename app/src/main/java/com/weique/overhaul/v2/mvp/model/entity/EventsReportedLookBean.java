package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author GreatKing
 */
public class EventsReportedLookBean implements Parcelable {

    /**
     * Id : e26230e0-d9c9-4051-a22b-9a46c7a4fa8c
     * CreateTime : 2019/12/18 14:24:26
     * CreateUserId : 0db5dc82-7c68-413d-bcbc-8ac86325ee28
     * UpdateTime : 2019/12/18 14:24:26
     * UpdateUserId : 0db5dc82-7c68-413d-bcbc-8ac86325ee28
     * DeleteDate :
     * ElementTypeId : a9bad5f2-4ea6-43db-82ec-228d7fcff0f1
     * EventFormTypeId : a2b75257-9de8-4ee7-b8b0-08dc94e57fa5
     * PointsInJson : {"lng":117.21495,"lat":34.300362}
     * DepartmentId : 04872d33-1501-4cf4-a268-4f6e2160479e
     * STANDARDADDRESSID : 89a5502b-be8c-43bc-87d5-469429b2b8c4
     * EnumElementDataSourceType : 1
     * name : 垃圾满了
     * memo : 该倒垃圾了
     * addr : 数字产业园
     * cover : /Uploads/CustomerData/image/file_1576650254000637122758547685216.jpg
     * SingleVideo :
     * DataStructureInJson :
     * departmentName : 数字产业园网格
     * addressName : 数字产业园
     * elementTypeName : 路灯
     * UserName : 测试管理员
     * EnumEventLevel : 0
     * EnumOrderStatus : 0
     */

    private String Id;
    private String RecordId;
    private String OldId;
    private String CreateTime;
    private String CreateUserId;
    private String UserId;
    private String UpdateTime;
    private String UpdateUserId;
    private String DeleteDate;
    private String ElementTypeId;
    private String EventFormTypeId;
    private String PointsInJson;
    private String DepartmentId;
    private String StandardAddressId;
    private int EnumElementDataSourceType;
    private String name;
    private String memo;
    private String addr;
    private String cover;
    private String SingleVideo;
    private String StructureInJson;
    private String departmentName;
    private String elementTypeName;
    private String UserName;
    private int EnumEventLevel;
    private int EnumOrderStatus;
    private String EventTypeName;
    private String comment;
    private String StandardAddressName;
    private String FullPath;
    private int EnumEventProcType = 0;
    private boolean isSaveSubmit = true;
    private String EventAddress;
    private String EventMemo;
    /**
     * 处理前的录音
     */
    private String RecordUrl;
    /**
     * 处理前的图片
     */
    private String ImgsInJson;
    /**
     * 处理前的 视频
     */
    private String VideoUrl;
    /**
     * 评论内容
     */
    private String Evaluation;
    /**
     * 评论图片
     */
    private String ImageUrl;


    /**
     * 处理后的图片
     */
    private String AfterProceedImgsInJson;
    /**
     * 处理后的录音
     */
    private String AfterProceedRecordUrl;
    /**
     * 处理后的视频
     */
    private String AfterProceedVideoUrl;

    public EventsReportedLookBean() {
    }

    protected EventsReportedLookBean(Parcel in) {
        Id = in.readString();
        RecordId = in.readString();
        OldId = in.readString();
        CreateTime = in.readString();
        CreateUserId = in.readString();
        UserId = in.readString();
        UpdateTime = in.readString();
        UpdateUserId = in.readString();
        DeleteDate = in.readString();
        ElementTypeId = in.readString();
        EventFormTypeId = in.readString();
        PointsInJson = in.readString();
        DepartmentId = in.readString();
        StandardAddressId = in.readString();
        EnumElementDataSourceType = in.readInt();
        name = in.readString();
        memo = in.readString();
        addr = in.readString();
        cover = in.readString();
        SingleVideo = in.readString();
        StructureInJson = in.readString();
        departmentName = in.readString();
        elementTypeName = in.readString();
        UserName = in.readString();
        EnumEventLevel = in.readInt();
        EnumOrderStatus = in.readInt();
        EventTypeName = in.readString();
        comment = in.readString();
        StandardAddressName = in.readString();
        FullPath = in.readString();
        EnumEventProcType = in.readInt();
        isSaveSubmit = in.readByte() != 0;
        EventAddress = in.readString();
        EventMemo = in.readString();
        RecordUrl = in.readString();
        ImgsInJson = in.readString();
        VideoUrl = in.readString();
        Evaluation = in.readString();
        ImageUrl = in.readString();
        AfterProceedImgsInJson = in.readString();
        AfterProceedRecordUrl = in.readString();
        AfterProceedVideoUrl = in.readString();
    }

    public static final Creator<EventsReportedLookBean> CREATOR = new Creator<EventsReportedLookBean>() {
        @Override
        public EventsReportedLookBean createFromParcel(Parcel in) {
            return new EventsReportedLookBean(in);
        }

        @Override
        public EventsReportedLookBean[] newArray(int size) {
            return new EventsReportedLookBean[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRecordId() {
        return RecordId;
    }

    public void setRecordId(String recordId) {
        RecordId = recordId;
    }

    public String getOldId() {
        return OldId;
    }

    public void setOldId(String oldId) {
        OldId = oldId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String createUserId) {
        CreateUserId = createUserId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getUpdateUserId() {
        return UpdateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        UpdateUserId = updateUserId;
    }

    public String getDeleteDate() {
        return DeleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        DeleteDate = deleteDate;
    }

    public String getElementTypeId() {
        return ElementTypeId;
    }

    public void setElementTypeId(String elementTypeId) {
        ElementTypeId = elementTypeId;
    }

    public String getEventFormTypeId() {
        return EventFormTypeId;
    }

    public void setEventFormTypeId(String eventFormTypeId) {
        EventFormTypeId = eventFormTypeId;
    }

    public String getPointsInJson() {
        return PointsInJson;
    }

    public void setPointsInJson(String pointsInJson) {
        PointsInJson = pointsInJson;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

    public String getStandardAddressId() {
        return StandardAddressId;
    }

    public void setStandardAddressId(String standardAddressId) {
        StandardAddressId = standardAddressId;
    }

    public int getEnumElementDataSourceType() {
        return EnumElementDataSourceType;
    }

    public void setEnumElementDataSourceType(int enumElementDataSourceType) {
        EnumElementDataSourceType = enumElementDataSourceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSingleVideo() {
        return SingleVideo;
    }

    public void setSingleVideo(String singleVideo) {
        SingleVideo = singleVideo;
    }

    public String getStructureInJson() {
        return StructureInJson;
    }

    public void setStructureInJson(String structureInJson) {
        StructureInJson = structureInJson;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getElementTypeName() {
        return elementTypeName;
    }

    public void setElementTypeName(String elementTypeName) {
        this.elementTypeName = elementTypeName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getEnumEventLevel() {
        return EnumEventLevel;
    }

    public void setEnumEventLevel(int enumEventLevel) {
        EnumEventLevel = enumEventLevel;
    }

    public int getEnumOrderStatus() {
        return EnumOrderStatus;
    }

    public void setEnumOrderStatus(int enumOrderStatus) {
        EnumOrderStatus = enumOrderStatus;
    }

    public String getEventTypeName() {
        return EventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        EventTypeName = eventTypeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStandardAddressName() {
        return StandardAddressName;
    }

    public void setStandardAddressName(String standardAddressName) {
        StandardAddressName = standardAddressName;
    }

    public String getFullPath() {
        return FullPath;
    }

    public void setFullPath(String fullPath) {
        FullPath = fullPath;
    }

    public int getEnumEventProcType() {
        return EnumEventProcType;
    }

    public void setEnumEventProcType(int enumEventProcType) {
        EnumEventProcType = enumEventProcType;
    }

    public boolean isSaveSubmit() {
        return isSaveSubmit;
    }

    public void setSaveSubmit(boolean saveSubmit) {
        isSaveSubmit = saveSubmit;
    }

    public String getEventAddress() {
        return EventAddress;
    }

    public void setEventAddress(String eventAddress) {
        EventAddress = eventAddress;
    }

    public String getEventMemo() {
        return EventMemo;
    }

    public void setEventMemo(String eventMemo) {
        EventMemo = eventMemo;
    }

    public String getRecordUrl() {
        return RecordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        RecordUrl = recordUrl;
    }

    public String getImgsInJson() {
        return ImgsInJson;
    }

    public void setImgsInJson(String imgsInJson) {
        ImgsInJson = imgsInJson;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getEvaluation() {
        return Evaluation;
    }

    public void setEvaluation(String evaluation) {
        Evaluation = evaluation;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getAfterProceedImgsInJson() {
        return AfterProceedImgsInJson;
    }

    public void setAfterProceedImgsInJson(String afterProceedImgsInJson) {
        AfterProceedImgsInJson = afterProceedImgsInJson;
    }

    public String getAfterProceedRecordUrl() {
        return AfterProceedRecordUrl;
    }

    public void setAfterProceedRecordUrl(String afterProceedRecordUrl) {
        AfterProceedRecordUrl = afterProceedRecordUrl;
    }

    public String getAfterProceedVideoUrl() {
        return AfterProceedVideoUrl;
    }

    public void setAfterProceedVideoUrl(String afterProceedVideoUrl) {
        AfterProceedVideoUrl = afterProceedVideoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(RecordId);
        dest.writeString(OldId);
        dest.writeString(CreateTime);
        dest.writeString(CreateUserId);
        dest.writeString(UserId);
        dest.writeString(UpdateTime);
        dest.writeString(UpdateUserId);
        dest.writeString(DeleteDate);
        dest.writeString(ElementTypeId);
        dest.writeString(EventFormTypeId);
        dest.writeString(PointsInJson);
        dest.writeString(DepartmentId);
        dest.writeString(StandardAddressId);
        dest.writeInt(EnumElementDataSourceType);
        dest.writeString(name);
        dest.writeString(memo);
        dest.writeString(addr);
        dest.writeString(cover);
        dest.writeString(SingleVideo);
        dest.writeString(StructureInJson);
        dest.writeString(departmentName);
        dest.writeString(elementTypeName);
        dest.writeString(UserName);
        dest.writeInt(EnumEventLevel);
        dest.writeInt(EnumOrderStatus);
        dest.writeString(EventTypeName);
        dest.writeString(comment);
        dest.writeString(StandardAddressName);
        dest.writeString(FullPath);
        dest.writeInt(EnumEventProcType);
        dest.writeByte((byte) (isSaveSubmit ? 1 : 0));
        dest.writeString(EventAddress);
        dest.writeString(EventMemo);
        dest.writeString(RecordUrl);
        dest.writeString(ImgsInJson);
        dest.writeString(VideoUrl);
        dest.writeString(Evaluation);
        dest.writeString(ImageUrl);
        dest.writeString(AfterProceedImgsInJson);
        dest.writeString(AfterProceedRecordUrl);
        dest.writeString(AfterProceedVideoUrl);
    }


    @Override
    public String toString() {
        return "EventsReportedLookBean{" +
                "Id='" + Id + '\'' +
                ", RecordId='" + RecordId + '\'' +
                ", OldId='" + OldId + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", CreateUserId='" + CreateUserId + '\'' +
                ", UserId='" + UserId + '\'' +
                ", UpdateTime='" + UpdateTime + '\'' +
                ", UpdateUserId='" + UpdateUserId + '\'' +
                ", DeleteDate='" + DeleteDate + '\'' +
                ", ElementTypeId='" + ElementTypeId + '\'' +
                ", EventFormTypeId='" + EventFormTypeId + '\'' +
                ", PointsInJson='" + PointsInJson + '\'' +
                ", DepartmentId='" + DepartmentId + '\'' +
                ", StandardAddressId='" + StandardAddressId + '\'' +
                ", EnumElementDataSourceType=" + EnumElementDataSourceType +
                ", name='" + name + '\'' +
                ", memo='" + memo + '\'' +
                ", addr='" + addr + '\'' +
                ", cover='" + cover + '\'' +
                ", SingleVideo='" + SingleVideo + '\'' +
                ", StructureInJson='" + StructureInJson + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", elementTypeName='" + elementTypeName + '\'' +
                ", UserName='" + UserName + '\'' +
                ", EnumEventLevel=" + EnumEventLevel +
                ", EnumOrderStatus=" + EnumOrderStatus +
                ", EventTypeName='" + EventTypeName + '\'' +
                ", comment='" + comment + '\'' +
                ", StandardAddressName='" + StandardAddressName + '\'' +
                ", FullPath='" + FullPath + '\'' +
                ", EnumEventProcType=" + EnumEventProcType +
                ", isSaveSubmit=" + isSaveSubmit +
                ", EventAddress='" + EventAddress + '\'' +
                ", EventMemo='" + EventMemo + '\'' +
                ", RecordUrl='" + RecordUrl + '\'' +
                ", ImgsInJson='" + ImgsInJson + '\'' +
                ", VideoUrl='" + VideoUrl + '\'' +
                ", Evaluation='" + Evaluation + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", AfterProceedImgsInJson='" + AfterProceedImgsInJson + '\'' +
                ", AfterProceedRecordUrl='" + AfterProceedRecordUrl + '\'' +
                ", AfterProceedVideoUrl='" + AfterProceedVideoUrl + '\'' +
                '}';
    }
}

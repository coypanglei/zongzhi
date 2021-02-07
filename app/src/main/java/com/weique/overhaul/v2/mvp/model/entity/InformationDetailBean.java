package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author GreatKing
 */
public class InformationDetailBean implements Parcelable, Cloneable {

    /**
     * Id : d5b0fa2f-917a-40bb-8d00-6324cde63735
     * CreateTime : /Date(1574863956000)/
     * CreateUserId : a6e16929-efba-43f3-8808-209c87ea7519
     * UpdateTime : /Date(1574863956000)/
     * UpdateUserId : a6e16929-efba-43f3-8808-209c87ea7519
     * DeleteDate : null
     * ElementTypeId : 4fc63cbc-885a-4286-8f6d-257f81ef595b
     * PointsInJson : {"lng":117.188189,"lat":34.301702}
     * DepartmentId : 27a8465b-4c18-47e0-b677-d12e6b414a44
     * STANDARDADDRESSID : 083542f4-2c4d-440c-9614-70b6fb591806
     * EnumElementDataSourceType : 1
     * name : name
     * memo : memo
     * address : address
     * Option3 : Option3
     * StandardAddressFullPath : 鼓楼生态园/18号楼
     * DepartmentFullPath : 合肥市瑶海区/牌楼街道/牌楼社区/1号网格
     */

    private String Id;
    private String ElementId;
    private String CreateTime;
    private String CreateUserId;
    private String UpdateTime;
    private String UpdateUserId;
    private String UserId;
    private String DeleteDate;
    private String DepartmentId;
    private int EnumElementDataSourceType;
    private String name;
    private String memo;
    private String address;
    private String Option3;
    private String PointsInJson;
    private String StructureInJsons;
    private String StandardAddressId;
    private String ElementTypeId;
    private String StandardAddressFullPath;
    private String DepartmentFullPath;
    private String FullPath;
    /**
     * 0：不显示，1：显示
     */
    private boolean IsAppChecked;

    public InformationDetailBean() {

    }


    protected InformationDetailBean(Parcel in) {
        Id = in.readString();
        ElementId = in.readString();
        CreateTime = in.readString();
        CreateUserId = in.readString();
        UpdateTime = in.readString();
        UpdateUserId = in.readString();
        UserId = in.readString();
        DeleteDate = in.readString();
        DepartmentId = in.readString();
        EnumElementDataSourceType = in.readInt();
        name = in.readString();
        memo = in.readString();
        address = in.readString();
        Option3 = in.readString();
        PointsInJson = in.readString();
        StructureInJsons = in.readString();
        StandardAddressId = in.readString();
        ElementTypeId = in.readString();
        StandardAddressFullPath = in.readString();
        DepartmentFullPath = in.readString();
        FullPath = in.readString();
        IsAppChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(ElementId);
        dest.writeString(CreateTime);
        dest.writeString(CreateUserId);
        dest.writeString(UpdateTime);
        dest.writeString(UpdateUserId);
        dest.writeString(UserId);
        dest.writeString(DeleteDate);
        dest.writeString(DepartmentId);
        dest.writeInt(EnumElementDataSourceType);
        dest.writeString(name);
        dest.writeString(memo);
        dest.writeString(address);
        dest.writeString(Option3);
        dest.writeString(PointsInJson);
        dest.writeString(StructureInJsons);
        dest.writeString(StandardAddressId);
        dest.writeString(ElementTypeId);
        dest.writeString(StandardAddressFullPath);
        dest.writeString(DepartmentFullPath);
        dest.writeString(FullPath);
        dest.writeByte((byte) (IsAppChecked ? 1 : 0));
    }

    public static final Creator<InformationDetailBean> CREATOR = new Creator<InformationDetailBean>() {
        @Override
        public InformationDetailBean createFromParcel(Parcel in) {
            return new InformationDetailBean(in);
        }

        @Override
        public InformationDetailBean[] newArray(int size) {
            return new InformationDetailBean[size];
        }
    };

    public String getFullPath() {
        return FullPath;
    }

    public void setFullPath(String fullPath) {
        FullPath = fullPath;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getStructureInJsons() {
        return StructureInJsons;
    }

    public void setStructureInJsons(String structureInJsons) {
        StructureInJsons = structureInJsons;
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

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String CreateUserId) {
        this.CreateUserId = CreateUserId;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getUpdateUserId() {
        return UpdateUserId;
    }

    public void setUpdateUserId(String UpdateUserId) {
        this.UpdateUserId = UpdateUserId;
    }

    public String getDeleteDate() {
        return DeleteDate;
    }

    public void setDeleteDate(String DeleteDate) {
        this.DeleteDate = DeleteDate;
    }

    public String getElementTypeId() {
        return ElementTypeId;
    }

    public void setElementTypeId(String ElementTypeId) {
        this.ElementTypeId = ElementTypeId;
    }

    public String getPointsInJson() {
        return PointsInJson;
    }

    public void setPointsInJson(String PointsInJson) {
        this.PointsInJson = PointsInJson;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getStandardAddressId() {
        return StandardAddressId;
    }

    public void setStandardAddressId(String StandardAddressId) {
        this.StandardAddressId = StandardAddressId;
    }

    public int getEnumElementDataSourceType() {
        return EnumElementDataSourceType;
    }

    public void setEnumElementDataSourceType(int EnumElementDataSourceType) {
        this.EnumElementDataSourceType = EnumElementDataSourceType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOption3() {
        return Option3;
    }

    public void setOption3(String Option3) {
        this.Option3 = Option3;
    }

    public String getElementId() {
        return ElementId;
    }

    public void setElementId(String elementId) {
        ElementId = elementId;
    }

    public String getStandardAddressFullPath() {
        return StandardAddressFullPath;
    }

    public void setStandardAddressFullPath(String StandardAddressFullPath) {
        this.StandardAddressFullPath = StandardAddressFullPath;
    }

    public boolean getIsAppChecked() {
        return IsAppChecked;
    }

    public void setIsAppChecked(boolean isAppChecked) {
        IsAppChecked = isAppChecked;
    }

    public String getDepartmentFullPath() {
        return DepartmentFullPath;
    }

    public void setDepartmentFullPath(String DepartmentFullPath) {
        this.DepartmentFullPath = DepartmentFullPath;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
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
        return "InformationDetailBean{" +
                "Id='" + Id + '\'' +
                ", ElementId='" + ElementId + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", CreateUserId='" + CreateUserId + '\'' +
                ", UpdateTime='" + UpdateTime + '\'' +
                ", UpdateUserId='" + UpdateUserId + '\'' +
                ", UserId='" + UserId + '\'' +
                ", DeleteDate='" + DeleteDate + '\'' +
                ", DepartmentId='" + DepartmentId + '\'' +
                ", EnumElementDataSourceType=" + EnumElementDataSourceType +
                ", name='" + name + '\'' +
                ", memo='" + memo + '\'' +
                ", address='" + address + '\'' +
                ", Option3='" + Option3 + '\'' +
                ", PointsInJson='" + PointsInJson + '\'' +
                ", StructureInJsons='" + StructureInJsons + '\'' +
                ", StandardAddressId='" + StandardAddressId + '\'' +
                ", ElementTypeId='" + ElementTypeId + '\'' +
                ", StandardAddressFullPath='" + StandardAddressFullPath + '\'' +
                ", DepartmentFullPath='" + DepartmentFullPath + '\'' +
                ", FullPath='" + FullPath + '\'' +
                ", IsAppChecked='" + IsAppChecked + '\'' +
                '}';
    }
}

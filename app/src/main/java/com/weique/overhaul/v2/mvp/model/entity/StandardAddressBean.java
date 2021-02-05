package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author GK
 */
public class StandardAddressBean implements Parcelable {

    public StandardAddressBean() {
    }

    /**
     * Department : {"EnumCommentLevel":0,"Name":"1号网格","Code":"0121","PinYin":"1HWG","PId":"dc3d6821-a248-4425-a0cd-01bf4fc3479d","ChargerId":"88a24b03-7a8b-440d-89e8-7595af7b3106","FullPath":"徐州市云龙区/牌楼街道/牌楼社区/1号网格","PointsInJSON":"{\"polygoys\":[{\"path\":[{\"lng\":117.256708,\"lat\":34.174844},{\"lng\":117.261  163,\"lat\":34.162},{\"lng\":117.256564,\"lat\":34.161403},{\"lng\":117.248372,\"lat\":34.169647},{\"lng\":117  .248731,\"lat\":34.169946}],\"styleOptions\":{\"strokeColor\":\"#ff6700\",\"fillColor\":\"#ff6700\",\"strokeWe  ight\":1,\"strokeOpacity\":0.6,\"fillOpacity\":0.4,\"strokeStyle\":\"solid\"}}]}","LayerLevel":0,"CSS":null,"Id":"233db99f-6f70-4ff3-b8e3-36e11ad00950","CreateUserId":"88a24b03-7a8b-440d-89e8-7595af7b3106","UpdateUserId":"88a24b03-7a8b-440d-89e8-7595af7b3106","CreateTime":"/Date(1574148073020)/","UpdateTime":"/Date(1574148079017)/","Memo":null,"IsDeleted":false,"DeletedTime":null}
     * StandardAddressType : {"Name":"街路巷、小区、段、庄、堂、排","PinYin":"JLH、XO、D、Z、T、P","CIndex":0,"Id":"07745006-7ca2-4b89-bd2c-d83c31d90660","CreateUserId":"88a24b03-7a8b-440d-89e8-7595af7b3106","UpdateUserId":"88a24b03-7a8b-440d-89e8-7595af7b3106","CreateTime":"/Date(1574157655633)/","UpdateTime":"/Date(1574157655633)/","Memo":null,"IsDeleted":false,"DeletedTime":null}
     * Name : 金港小区
     * Code : 0123
     * PinYin : JGXO
     * PId : 00000000-0000-0000-0000-000000000000
     * FullPath : 金港小区
     * DepartmentId : 233db99f-6f70-4ff3-b8e3-36e11ad00950
     * DepartmentName : null
     * StandardAddressTypeId : 07745006-7ca2-4b89-bd2c-d83c31d90660
     * StandardAddressTypeName : null
     * GisLocations : {"lng":117.25412,"lat":34.167347}
     * Css : null
     * Id : 4a43f911-111f-4594-8ebb-039537c71ea9
     * CreateUserId : 88a24b03-7a8b-440d-89e8-7595af7b3106
     * UpdateUserId : 88a24b03-7a8b-440d-89e8-7595af7b3106
     * CreateTime : /Date(1574387059060)/
     * UpdateTime : /Date(1574387059060)/
     * Memo : null
     * IsDeleted : false
     * DeletedTime : null
     */

    private DepartmentBean Department;
    private StandardAddressTypeBean StandardAddressType;
    private String Name;
    private String Code;
    private String PinYin;
    private String PId;
    private String FullPath;
    private String DepartmentId;
    private String DepartmentName;
    private String StandardAddressTypeId;
    private String StandardAddressTypeName;
    private String GisLocations;
    private String Css;
    private String Id;
    private String CreateUserId;
    private String UpdateUserId;
    private String CreateTime;
    private String UpdateTime;
    private String Memo;
    private boolean IsDeleted;
    private String DeletedTime;

    protected StandardAddressBean(Parcel in) {
        Name = in.readString();
        Code = in.readString();
        PinYin = in.readString();
        PId = in.readString();
        FullPath = in.readString();
        DepartmentId = in.readString();
        DepartmentName = in.readString();
        StandardAddressTypeId = in.readString();
        StandardAddressTypeName = in.readString();
        GisLocations = in.readString();
        Css = in.readString();
        Id = in.readString();
        CreateUserId = in.readString();
        UpdateUserId = in.readString();
        CreateTime = in.readString();
        UpdateTime = in.readString();
        Memo = in.readString();
        IsDeleted = in.readByte() != 0;
        DeletedTime = in.readString();
        Department = in.readParcelable(DepartmentBean.class.getClassLoader());
        StandardAddressType = in.readParcelable(StandardAddressTypeBean.class.getClassLoader());
    }

    public static final Creator<StandardAddressBean> CREATOR = new Creator<StandardAddressBean>() {
        @Override
        public StandardAddressBean createFromParcel(Parcel in) {
            return new StandardAddressBean(in);
        }

        @Override
        public StandardAddressBean[] newArray(int size) {
            return new StandardAddressBean[size];
        }
    };

    public DepartmentBean getDepartment() {
        return Department;
    }

    public void setDepartment(DepartmentBean Department) {
        this.Department = Department;
    }

    public StandardAddressTypeBean getStandardAddressType() {
        return StandardAddressType;
    }

    public void setStandardAddressType(StandardAddressTypeBean StandardAddressType) {
        this.StandardAddressType = StandardAddressType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String PinYin) {
        this.PinYin = PinYin;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public String getFullPath() {
        return FullPath;
    }

    public void setFullPath(String FullPath) {
        this.FullPath = FullPath;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public String getStandardAddressTypeId() {
        return StandardAddressTypeId;
    }

    public void setStandardAddressTypeId(String StandardAddressTypeId) {
        this.StandardAddressTypeId = StandardAddressTypeId;
    }

    public String getStandardAddressTypeName() {
        return StandardAddressTypeName;
    }

    public void setStandardAddressTypeName(String StandardAddressTypeName) {
        this.StandardAddressTypeName = StandardAddressTypeName;
    }

    public String getGisLocations() {
        return GisLocations;
    }

    public void setGisLocations(String GisLocations) {
        this.GisLocations = GisLocations;
    }

    public String getCss() {
        return Css;
    }

    public void setCss(String Css) {
        this.Css = Css;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String CreateUserId) {
        this.CreateUserId = CreateUserId;
    }

    public String getUpdateUserId() {
        return UpdateUserId;
    }

    public void setUpdateUserId(String UpdateUserId) {
        this.UpdateUserId = UpdateUserId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }

    public boolean isIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public String getDeletedTime() {
        return DeletedTime;
    }

    public void setDeletedTime(String DeletedTime) {
        this.DeletedTime = DeletedTime;
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

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Code);
        dest.writeString(PinYin);
        dest.writeString(PId);
        dest.writeString(FullPath);
        dest.writeString(DepartmentId);
        dest.writeString(DepartmentName);
        dest.writeString(StandardAddressTypeId);
        dest.writeString(StandardAddressTypeName);
        dest.writeString(GisLocations);
        dest.writeString(Css);
        dest.writeString(Id);
        dest.writeString(CreateUserId);
        dest.writeString(UpdateUserId);
        dest.writeString(CreateTime);
        dest.writeString(UpdateTime);
        dest.writeString(Memo);
        dest.writeByte((byte) (IsDeleted ? 1 : 0));
        dest.writeString(DeletedTime);
        dest.writeParcelable(Department, flags);
        dest.writeParcelable(StandardAddressType, flags);
    }

    public static class DepartmentBean implements Parcelable {
        /**
         * EnumCommentLevel : 0
         * Name : 1号网格
         * Code : 0121
         * PinYin : 1HWG
         * PId : dc3d6821-a248-4425-a0cd-01bf4fc3479d
         * ChargerId : 88a24b03-7a8b-440d-89e8-7595af7b3106
         * FullPath : 徐州市云龙区/牌楼街道/牌楼社区/1号网格
         * PointsInJSON : {"polygoys":[{"path":[{"lng":117.256708,"lat":34.174844},{"lng":117.261  163,"lat":34.162},{"lng":117.256564,"lat":34.161403},{"lng":117.248372,"lat":34.169647},{"lng":117  .248731,"lat":34.169946}],"styleOptions":{"strokeColor":"#ff6700","fillColor":"#ff6700","strokeWe  ight":1,"strokeOpacity":0.6,"fillOpacity":0.4,"strokeStyle":"solid"}}]}
         * LayerLevel : 0
         * CSS : null
         * Id : 233db99f-6f70-4ff3-b8e3-36e11ad00950
         * CreateUserId : 88a24b03-7a8b-440d-89e8-7595af7b3106
         * UpdateUserId : 88a24b03-7a8b-440d-89e8-7595af7b3106
         * CreateTime : /Date(1574148073020)/
         * UpdateTime : /Date(1574148079017)/
         * Memo : null
         * IsDeleted : false
         * DeletedTime : null
         */

        private int EnumCommunityLevel;
        private String Name;
        private String Code;
        private String PinYin;
        private String PId;
        private String ChargerId;
        private String FullPath;
        private String PointsInJSON;
        private int LayerLevel;
        private String CSS;
        private String Id;
        private String CreateUserId;
        private String UpdateUserId;
        private String CreateTime;
        private String UpdateTime;
        private String Memo;
        private boolean IsDeleted;
        private String DeletedTime;

        protected DepartmentBean(Parcel in) {
            EnumCommunityLevel = in.readInt();
            Name = in.readString();
            Code = in.readString();
            PinYin = in.readString();
            PId = in.readString();
            ChargerId = in.readString();
            FullPath = in.readString();
            PointsInJSON = in.readString();
            LayerLevel = in.readInt();
            CSS = in.readString();
            Id = in.readString();
            CreateUserId = in.readString();
            UpdateUserId = in.readString();
            CreateTime = in.readString();
            UpdateTime = in.readString();
            Memo = in.readString();
            IsDeleted = in.readByte() != 0;
            DeletedTime = in.readString();
        }

        public static final Creator<DepartmentBean> CREATOR = new Creator<DepartmentBean>() {
            @Override
            public DepartmentBean createFromParcel(Parcel in) {
                return new DepartmentBean(in);
            }

            @Override
            public DepartmentBean[] newArray(int size) {
                return new DepartmentBean[size];
            }
        };

        public int getEnumCommunityLevel() {
            return EnumCommunityLevel;
        }

        public void setEnumCommunityLevel(int EnumCommunityLevel) {
            this.EnumCommunityLevel = EnumCommunityLevel;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getPinYin() {
            return PinYin;
        }

        public void setPinYin(String PinYin) {
            this.PinYin = PinYin;
        }

        public String getPId() {
            return PId;
        }

        public void setPId(String PId) {
            this.PId = PId;
        }

        public String getChargerId() {
            return ChargerId;
        }

        public void setChargerId(String ChargerId) {
            this.ChargerId = ChargerId;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public String getPointsInJSON() {
            return PointsInJSON;
        }

        public void setPointsInJSON(String PointsInJSON) {
            this.PointsInJSON = PointsInJSON;
        }

        public int getLayerLevel() {
            return LayerLevel;
        }

        public void setLayerLevel(int LayerLevel) {
            this.LayerLevel = LayerLevel;
        }

        public String getCSS() {
            return CSS;
        }

        public void setCSS(String CSS) {
            this.CSS = CSS;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getCreateUserId() {
            return CreateUserId;
        }

        public void setCreateUserId(String CreateUserId) {
            this.CreateUserId = CreateUserId;
        }

        public String getUpdateUserId() {
            return UpdateUserId;
        }

        public void setUpdateUserId(String UpdateUserId) {
            this.UpdateUserId = UpdateUserId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public boolean isIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(boolean IsDeleted) {
            this.IsDeleted = IsDeleted;
        }

        public String getDeletedTime() {
            return DeletedTime;
        }

        public void setDeletedTime(String DeletedTime) {
            this.DeletedTime = DeletedTime;
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

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(EnumCommunityLevel);
            dest.writeString(Name);
            dest.writeString(Code);
            dest.writeString(PinYin);
            dest.writeString(PId);
            dest.writeString(ChargerId);
            dest.writeString(FullPath);
            dest.writeString(PointsInJSON);
            dest.writeInt(LayerLevel);
            dest.writeString(CSS);
            dest.writeString(Id);
            dest.writeString(CreateUserId);
            dest.writeString(UpdateUserId);
            dest.writeString(CreateTime);
            dest.writeString(UpdateTime);
            dest.writeString(Memo);
            dest.writeByte((byte) (IsDeleted ? 1 : 0));
            dest.writeString(DeletedTime);
        }
    }

    public static class StandardAddressTypeBean implements Parcelable {
        /**
         * Name : 街路巷、小区、段、庄、堂、排
         * PinYin : JLH、XO、D、Z、T、P
         * CIndex : 0
         * Id : 07745006-7ca2-4b89-bd2c-d83c31d90660
         * CreateUserId : 88a24b03-7a8b-440d-89e8-7595af7b3106
         * UpdateUserId : 88a24b03-7a8b-440d-89e8-7595af7b3106
         * CreateTime : /Date(1574157655633)/
         * UpdateTime : /Date(1574157655633)/
         * Memo : null
         * IsDeleted : false
         * DeletedTime : null
         */

        private String Name;
        private String PinYin;
        private int CIndex;
        private String Id;
        private String CreateUserId;
        private String UpdateUserId;
        private String CreateTime;
        private String UpdateTime;
        private String Memo;
        private boolean IsDeleted;
        private String DeletedTime;

        protected StandardAddressTypeBean(Parcel in) {
            Name = in.readString();
            PinYin = in.readString();
            CIndex = in.readInt();
            Id = in.readString();
            CreateUserId = in.readString();
            UpdateUserId = in.readString();
            CreateTime = in.readString();
            UpdateTime = in.readString();
            Memo = in.readString();
            IsDeleted = in.readByte() != 0;
            DeletedTime = in.readString();
        }

        public static final Creator<StandardAddressTypeBean> CREATOR = new Creator<StandardAddressTypeBean>() {
            @Override
            public StandardAddressTypeBean createFromParcel(Parcel in) {
                return new StandardAddressTypeBean(in);
            }

            @Override
            public StandardAddressTypeBean[] newArray(int size) {
                return new StandardAddressTypeBean[size];
            }
        };

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPinYin() {
            return PinYin;
        }

        public void setPinYin(String PinYin) {
            this.PinYin = PinYin;
        }

        public int getCIndex() {
            return CIndex;
        }

        public void setCIndex(int CIndex) {
            this.CIndex = CIndex;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getCreateUserId() {
            return CreateUserId;
        }

        public void setCreateUserId(String CreateUserId) {
            this.CreateUserId = CreateUserId;
        }

        public String getUpdateUserId() {
            return UpdateUserId;
        }

        public void setUpdateUserId(String UpdateUserId) {
            this.UpdateUserId = UpdateUserId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public boolean isIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(boolean IsDeleted) {
            this.IsDeleted = IsDeleted;
        }

        public String getDeletedTime() {
            return DeletedTime;
        }

        public void setDeletedTime(String DeletedTime) {
            this.DeletedTime = DeletedTime;
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

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Name);
            dest.writeString(PinYin);
            dest.writeInt(CIndex);
            dest.writeString(Id);
            dest.writeString(CreateUserId);
            dest.writeString(UpdateUserId);
            dest.writeString(CreateTime);
            dest.writeString(UpdateTime);
            dest.writeString(Memo);
            dest.writeByte((byte) (IsDeleted ? 1 : 0));
            dest.writeString(DeletedTime);
        }
    }

    public static class LonAndLat implements Parcelable {

        /**
         * lng : 117.25412
         * lat : 34.167347
         */

        private double lng;
        private double lat;

        protected LonAndLat(Parcel in) {
            lng = in.readDouble();
            lat = in.readDouble();
        }

        public LonAndLat(double lng, double lat) {
            this.lng = lng;
            this.lat = lat;
        }

        public LonAndLat() {
        }

        public final Creator<LonAndLat> CREATOR = new Creator<LonAndLat>() {
            @Override
            public LonAndLat createFromParcel(Parcel in) {
                return new LonAndLat(in);
            }

            @Override
            public LonAndLat[] newArray(int size) {
                return new LonAndLat[size];
            }
        };

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
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

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(lng);
            dest.writeDouble(lat);
        }
    }
}

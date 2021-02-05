package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class WorkLineLatBean implements Parcelable {

    /**
     * Id : d1e8085f-b695-4a6f-9543-2e1f213659ef
     * Address : 中国江苏省徐州市鼓楼区环城街道奔腾大道
     * PointsInJson : null
     * CreateTime : 2020/01/06 16:17
     */

    private String Id;
    private String Address;
    private String PointsInJson;
    private String CreateTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPointsInJson() {
        return PointsInJson;
    }

    public void setPointsInJson(String pointsInJson) {
        PointsInJson = pointsInJson;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public static Creator<WorkLineLatBean> getCREATOR() {
        return CREATOR;
    }

    protected WorkLineLatBean(Parcel in) {
        Id = in.readString();
        Address = in.readString();
        PointsInJson = in.readString();
        CreateTime = in.readString();
    }

    public static final Creator<WorkLineLatBean> CREATOR = new Creator<WorkLineLatBean>() {
        @Override
        public WorkLineLatBean createFromParcel(Parcel in) {
            return new WorkLineLatBean(in);
        }

        @Override
        public WorkLineLatBean[] newArray(int size) {
            return new WorkLineLatBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Address);
        parcel.writeString(PointsInJson);
        parcel.writeString(CreateTime);
    }
}

package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BaseSearchPopupBean implements Parcelable {
    @SerializedName(value = "Name", alternate = {"name"})
    private String Name;
    @SerializedName(value = "Id", alternate = {"id"})
    private String Id;

    public BaseSearchPopupBean() {
    }

    protected BaseSearchPopupBean(Parcel in) {
        Name = in.readString();
        Id = in.readString();
    }

    public static final Creator<BaseSearchPopupBean> CREATOR = new Creator<BaseSearchPopupBean>() {
        @Override
        public BaseSearchPopupBean createFromParcel(Parcel in) {
            return new BaseSearchPopupBean(in);
        }

        @Override
        public BaseSearchPopupBean[] newArray(int size) {
            return new BaseSearchPopupBean[size];
        }
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
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
        dest.writeString(Id);
    }

    @Override
    public String toString() {
        return "BaseSearchPopupBean{" +
                "Name='" + Name + '\'' +
                ", Id='" + Id + '\'' +
                '}';
    }
}

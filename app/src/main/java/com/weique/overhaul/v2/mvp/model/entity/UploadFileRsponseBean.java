package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.weique.overhaul.v2.mvp.model.entity.interfaces.FileImageAndNameBean;

public class UploadFileRsponseBean implements FileImageAndNameBean, Parcelable {

    /**
     * Id : f75c8d4a-522a-4e10-8ccf-7079677859f2
     * BussinessId : cfcf1b2f-c35d-431f-8198-ac14f3455a30
     * Url : /Uploads/CustomerData/image/157535919325670637109848000794545.jpeg
     */

    private String Id;
    private String BussinessId;
    private String Url;
    private String sourceFilePath;

    protected UploadFileRsponseBean(Parcel in) {
        Id = in.readString();
        BussinessId = in.readString();
        Url = in.readString();
        sourceFilePath = in.readString();
    }

    public static final Creator<UploadFileRsponseBean> CREATOR = new Creator<UploadFileRsponseBean>() {
        @Override
        public UploadFileRsponseBean createFromParcel(Parcel in) {
            return new UploadFileRsponseBean(in);
        }

        @Override
        public UploadFileRsponseBean[] newArray(int size) {
            return new UploadFileRsponseBean[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getBussinessId() {
        return BussinessId;
    }

    public void setBussinessId(String BussinessId) {
        this.BussinessId = BussinessId;
    }

    @Override
    public String getUrl() {
        return Url;
    }

    @Override
    public String getName() {
        String[] split = Url.split("/");
        return split[split.length - 1];
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
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
        dest.writeString(Id);
        dest.writeString(BussinessId);
        dest.writeString(Url);
        dest.writeString(sourceFilePath);
    }
}

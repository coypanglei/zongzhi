package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;


public class HomeMenuItemBean implements Parcelable {


    /**
     * Name : ""
     * CIndex : 1
     * IconURL : ""
     * Target : ""
     */

    private String Name;
    private int CIndex;
    private String IconURL;
    private int iconDrawable;
    private String Target;
    private boolean IsDisplay = true;

    public HomeMenuItemBean() {
    }

    public HomeMenuItemBean(String name, int CIndex, String iconURL, int iconDrawable, String target, boolean isDisplay) {
        Name = name;
        this.CIndex = CIndex;
        IconURL = iconURL;
        this.iconDrawable = iconDrawable;
        Target = target;
        IsDisplay = isDisplay;
    }

    public HomeMenuItemBean(String name, int CIndex, int iconDrawable, String target) {
        Name = name;
        this.CIndex = CIndex;
        this.iconDrawable = iconDrawable;
        Target = target;
    }

    public HomeMenuItemBean(String name, int CIndex, String iconURL, String target) {
        Name = name;
        this.CIndex = CIndex;
        IconURL = iconURL;
        Target = target;
    }

    protected HomeMenuItemBean(Parcel in) {
        Name = in.readString();
        CIndex = in.readInt();
        IconURL = in.readString();
        iconDrawable = in.readInt();
        Target = in.readString();
        IsDisplay = in.readByte() != 0;
    }

    public static final Creator<HomeMenuItemBean> CREATOR = new Creator<HomeMenuItemBean>() {
        @Override
        public HomeMenuItemBean createFromParcel(Parcel in) {
            return new HomeMenuItemBean(in);
        }

        @Override
        public HomeMenuItemBean[] newArray(int size) {
            return new HomeMenuItemBean[size];
        }
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCIndex() {
        return CIndex;
    }

    public void setCIndex(int CIndex) {
        this.CIndex = CIndex;
    }

    public String getIconURL() {
        return IconURL;
    }

    public void setIconURL(String iconURL) {
        IconURL = iconURL;
    }

    public int getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(int iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public boolean isDisplay() {
        return IsDisplay;
    }

    public void setDisplay(boolean display) {
        IsDisplay = display;
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
        dest.writeInt(CIndex);
        dest.writeString(IconURL);
        dest.writeInt(iconDrawable);
        dest.writeString(Target);
        dest.writeByte((byte) (IsDisplay ? 1 : 0));
    }
}

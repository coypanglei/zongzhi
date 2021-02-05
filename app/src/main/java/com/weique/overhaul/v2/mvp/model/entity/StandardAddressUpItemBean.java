package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class StandardAddressUpItemBean {

    /**
     * pageCount : 1
     * data : [{"Id":"ea9749c8-7f4c-4adb-806f-119ad115b0b4","Name":"xx小区(街路巷、小区、段、庄、堂、排)"},{"Id":"4a43f911-111f-4594-8ebb-039537c71ea9","Name":"金港小区(街路巷、小区、段、庄、堂、排)"},{"Id":"70df0a32-6d48-4940-be66-ebca14b2c0c8","Name":"一个小区(街路巷、小区、段、庄、堂、排)"}]
     */

    private int pageCount;
    private List<DataBean> data;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        public DataBean(String id, String name) {
            Id = id;
            Name = name;
        }

        /**
         * Id : ea9749c8-7f4c-4adb-806f-119ad115b0b4
         * Name : xx小区(街路巷、小区、段、庄、堂、排)
         */

        private String Id;
        private String Name;
        private String GisLocations;

        protected DataBean(Parcel in) {
            Id = in.readString();
            Name = in.readString();
            GisLocations = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getGisLocations() {
            return GisLocations;
        }

        public void setGisLocations(String gisLocations) {
            GisLocations = gisLocations;
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
            dest.writeString(Name);
            dest.writeString(GisLocations);
        }
    }
}

package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class PointsBean implements Parcelable {


    /**
     * Id : 0cca78c2-efc7-431c-899e-d0ff80cd158f
     * departmentName : 安国居测试网格
     * point : {"lng":116.83849227208576,"lat":34.84728592619352}
     * elementCount : 2
     */

    private String PointsInJSON;

    public String getPointsInJSON() {
        return PointsInJSON;
    }

    public void setPointsInJSON(String pointsInJSON) {
        PointsInJSON = pointsInJSON;
    }

    private String Id;
    private String departmentName;
    private PointBean point;
    private int elementCount;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public PointBean getPoint() {
        return point;
    }

    public void setPoint(PointBean point) {
        this.point = point;
    }

    public int getElementCount() {
        return elementCount;
    }

    public void setElementCount(int elementCount) {
        this.elementCount = elementCount;
    }

    public static class PointBean implements Parcelable {
        /**
         * lng : 116.83849227208576
         * lat : 34.84728592619352
         */

        private double lng;
        private double lat;

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.lng);
            dest.writeDouble(this.lat);
        }

        public PointBean() {
        }

        protected PointBean(Parcel in) {
            this.lng = in.readDouble();
            this.lat = in.readDouble();
        }

        public static final Creator<PointBean> CREATOR = new Creator<PointBean>() {
            @Override
            public PointBean createFromParcel(Parcel source) {
                return new PointBean(source);
            }

            @Override
            public PointBean[] newArray(int size) {
                return new PointBean[size];
            }
        };
    }



    public PointsBean() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PointsInJSON);
        dest.writeString(this.Id);
        dest.writeString(this.departmentName);
        dest.writeParcelable(this.point, flags);
        dest.writeInt(this.elementCount);
    }

    protected PointsBean(Parcel in) {
        this.PointsInJSON = in.readString();
        this.Id = in.readString();
        this.departmentName = in.readString();
        this.point = in.readParcelable(PointBean.class.getClassLoader());
        this.elementCount = in.readInt();
    }

    public static final Parcelable.Creator<PointsBean> CREATOR = new Parcelable.Creator<PointsBean>() {
        @Override
        public PointsBean createFromParcel(Parcel source) {
            return new PointsBean(source);
        }

        @Override
        public PointsBean[] newArray(int size) {
            return new PointsBean[size];
        }
    };
}

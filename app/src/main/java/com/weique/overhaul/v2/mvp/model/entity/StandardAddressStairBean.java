package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * 标准地址 中  一级界面 查询信息
 *
 * @author GK
 */
public class StandardAddressStairBean {
    @StandardAddressEnumBean
    private int currentRank;
    /**
     * 网格
     */
    public static final int GRIDDING = 0;
    /**
     * 邻里服务站
     */
    public static final int SERVICE_STATION = 1;
    /**
     * 社区
     */
    public static final int COMMUNITY = 2;
    /**
     * 街道
     */
    public static final int STREET = 3;
    /**
     * 区
     */
    public static final int AREA = 4;
    /**
     * 市
     */
    public static final int CITY = 5;
    /**
     * 省
     */
    public static final int PROVINCE = 6;
    /**
     * 国家
     */
    public static final int STATE = 7;

    /**
     * 网格及其网格以上
     */
    private String[] currentRankNameWithUp = {"网格", "邻里服务站", "社区", "街道", "区", "市", "省", "国家"};

    /**
     * 网格以下
     */
//    private String[] currentRankNameWithDown = {"街道 巷(小区)", "楼宇(建筑物)", "单元", "门牌"};

    //用 @IntDef "包住" 常量；
    // @Retention 定义策略
    // 声明构造器
    @IntDef({GRIDDING, SERVICE_STATION, COMMUNITY, STREET, AREA, CITY, PROVINCE, STATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StandardAddressEnumBean {

    }

    public void setCurrentRank(@StandardAddressEnumBean int currentRank) {
        this.currentRank = currentRank;
    }

    @StandardAddressEnumBean
    public int getCurrentRank() {
        return currentRank;
    }

    //todo  这里 写死了 需要后面注意改成灵活配置
    public String getCurrentRankNameWithUp() {
        return currentRankNameWithUp[currentRank];
    }

    //todo  这里 写死了 需要后面注意改成灵活配置
//    public String getCurrentRankNameWithDown() {
//        return currentRankNameWithDown[currentRank];
//    }

    private int pageCount;
    private int count;

    private List<DepartmentBean> department;
    private List<AddressTypeBean> addressType;
    private List<StandardAddressBean> StandardAddress;
    private DepartmentUpBean departmentUp;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AddressTypeBean> getAddressType() {
        return addressType;
    }

    public void setAddressType(List<AddressTypeBean> addressType) {
        this.addressType = addressType;
    }

    public List<DepartmentBean> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartmentBean> department) {
        this.department = department;
    }

    public DepartmentUpBean getDepartmentUp() {
        return departmentUp;
    }

    public void setDepartmentUp(DepartmentUpBean departmentUp) {
        this.departmentUp = departmentUp;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        pageCount = pageCount;
    }

    public List<StandardAddressBean> getStandardAddress() {
        return StandardAddress;
    }

    public void setStandardAddress(List<StandardAddressBean> standardAddress) {
        StandardAddress = standardAddress;
    }

    public static class DepartmentBean extends StandardAddressStairBaseBean {

        protected DepartmentBean(Parcel in) {
            super(in);
        }
    }

    public static class AddressTypeBean extends StandardAddressStairBaseBean {
        public AddressTypeBean() {
        }

        protected AddressTypeBean(Parcel in) {
            super(in);
        }
    }

    public static class DepartmentUpBean extends StandardAddressStairBaseBean {

        protected DepartmentUpBean(Parcel in) {
            super(in);
        }
    }

    public static class StandardAddressBean extends StandardAddressStairBaseBean {

        protected StandardAddressBean(Parcel in) {
            super(in);
        }

        public StandardAddressBean() {
        }
    }

    public static class StandardAddressStairBaseBean extends BaseSearchPopupBean {

        /**
         * Id : 233db99f-6f70-4ff3-b8e3-36e11ad00950
         * PId : dc3d6821-a248-4425-a0cd-01bf4fc3479d
         * Name : 1号网格
         * Code : 0121
         * AddressCode : 00120121
         * PointsInJSON : {"polygoys":[{"path":[{"lng":117.256708,"lat":34.174844},{"lng":117.261163,"lat":34.162},{"lng":117.256564,"lat":34.161403},{"lng":117.248372,"lat":34.169647},{"lng":117.248731,"lat":34.169946}],"styleOptions":{"strokeColor":"#ff6700","fillColor":"#ff6700","strokeWeight":1,"strokeOpacity":0.6,"fillOpacity":0.4,"strokeStyle":"solid"}}]}
         * Level : 0
         */

        private String PId;
        private String Code;
        private String AddressCode;
        private String PointsInJSON;
        private int Level;
        private String PinYin;
        private String FullPath;
        private String GisLocations;
        private String DepartmentId;
        private String StandardAddressTypeId;

        public StandardAddressStairBaseBean() {
        }

        protected StandardAddressStairBaseBean(Parcel in) {
            super(in);
            PId = in.readString();
            Code = in.readString();
            AddressCode = in.readString();
            PointsInJSON = in.readString();
            Level = in.readInt();
            PinYin = in.readString();
            FullPath = in.readString();
            GisLocations = in.readString();
            DepartmentId = in.readString();
            StandardAddressTypeId = in.readString();
        }

        public static final Creator<StandardAddressStairBaseBean> CREATOR = new Creator<StandardAddressStairBaseBean>() {
            @Override
            public StandardAddressStairBaseBean createFromParcel(Parcel in) {
                return new StandardAddressStairBaseBean(in);
            }

            @Override
            public StandardAddressStairBaseBean[] newArray(int size) {
                return new StandardAddressStairBaseBean[size];
            }
        };

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
            dest.writeString(PId);
            dest.writeString(Code);
            dest.writeString(AddressCode);
            dest.writeString(PointsInJSON);
            dest.writeInt(Level);
            dest.writeString(PinYin);
            dest.writeString(FullPath);
            dest.writeString(GisLocations);
            dest.writeString(DepartmentId);
            dest.writeString(StandardAddressTypeId);
        }

        public String getStandardAddressTypeId() {
            return StandardAddressTypeId;
        }

        public void setStandardAddressTypeId(String standardAddressTypeId) {
            StandardAddressTypeId = standardAddressTypeId;
        }

        public String getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(String departmentId) {
            DepartmentId = departmentId;
        }

        public String getPId() {
            return PId;
        }

        public void setPId(String PId) {
            this.PId = PId;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getAddressCode() {
            return AddressCode;
        }

        public void setAddressCode(String addressCode) {
            AddressCode = addressCode;
        }

        public String getPointsInJSON() {
            return PointsInJSON;
        }

        public void setPointsInJSON(String pointsInJSON) {
            PointsInJSON = pointsInJSON;
        }

        public int getLevel() {
            return Level;
        }

        public void setLevel(int level) {
            Level = level;
        }

        public String getPinYin() {
            return PinYin;
        }

        public void setPinYin(String pinYin) {
            PinYin = pinYin;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String fullPath) {
            FullPath = fullPath;
        }

        public String getGisLocations() {
            return GisLocations;
        }

        public void setGisLocations(String gisLocations) {
            GisLocations = gisLocations;
        }
    }


    public static class PointsInJsonBean {

        private List<PolygoysBean> polygoys;

        public List<PolygoysBean> getPolygoys() {
            return polygoys;
        }

        public void setPolygoys(List<PolygoysBean> polygoys) {
            this.polygoys = polygoys;
        }

        public static class PolygoysBean {
            /**
             * path : [{"lng":117.256708,"lat":34.174844},{"lng":117.26163,"lat":34.162},{"lng":117.256564,"lat":34.161403},{"lng":117.248372,"lat":34.169647},{"lng":117.248731,"lat":34.169946}]
             * styleOptions : {"strokeColor":"#ff6700","fillColor":"#ff6700","strokeWeight":1,"strokeOpacity":0.6,"fillOpacity":0.4,"strokeStyle":"solid"}
             */

            private StyleOptionsBean styleOptions;
            private List<PathBean> path;

            public StyleOptionsBean getStyleOptions() {
                return styleOptions;
            }

            public void setStyleOptions(StyleOptionsBean styleOptions) {
                this.styleOptions = styleOptions;
            }

            public List<PathBean> getPath() {
                return path;
            }

            public void setPath(List<PathBean> path) {
                this.path = path;
            }

            public static class StyleOptionsBean {
                /**
                 * strokeColor : #ff6700
                 * fillColor : #ff6700
                 * strokeWeight : 1
                 * strokeOpacity : 0.6
                 * fillOpacity : 0.4
                 * strokeStyle : solid
                 */

                private String strokeColor;
                private String fillColor;
                private int strokeWeight;
                private double strokeOpacity;
                private double fillOpacity;
                private String strokeStyle;

                public String getStrokeColor() {
                    return strokeColor;
                }

                public void setStrokeColor(String strokeColor) {
                    this.strokeColor = strokeColor;
                }

                public String getFillColor() {
                    return fillColor;
                }

                public void setFillColor(String fillColor) {
                    this.fillColor = fillColor;
                }

                public int getStrokeWeight() {
                    return strokeWeight;
                }

                public void setStrokeWeight(int strokeWeight) {
                    this.strokeWeight = strokeWeight;
                }

                public double getStrokeOpacity() {
                    return strokeOpacity;
                }

                public void setStrokeOpacity(double strokeOpacity) {
                    this.strokeOpacity = strokeOpacity;
                }

                public double getFillOpacity() {
                    return fillOpacity;
                }

                public void setFillOpacity(double fillOpacity) {
                    this.fillOpacity = fillOpacity;
                }

                public String getStrokeStyle() {
                    return strokeStyle;
                }

                public void setStrokeStyle(String strokeStyle) {
                    this.strokeStyle = strokeStyle;
                }
            }

            public static class PathBean implements Parcelable {
                /**
                 * lng : 117.256708
                 * lat : 34.174844
                 */

                private double lng;
                private double lat;

                public PathBean() {
                }

                public PathBean(double lng, double lat) {
                    this.lng = lng;
                    this.lat = lat;
                }

                protected PathBean(Parcel in) {
                    lng = in.readDouble();
                    lat = in.readDouble();
                }

                public static final Creator<PathBean> CREATOR = new Creator<PathBean>() {
                    @Override
                    public PathBean createFromParcel(Parcel in) {
                        return new PathBean(in);
                    }

                    @Override
                    public PathBean[] newArray(int size) {
                        return new PathBean[size];
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
    }
}

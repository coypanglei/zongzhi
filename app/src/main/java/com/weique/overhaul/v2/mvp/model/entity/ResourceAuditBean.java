package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.weique.overhaul.v2.R;

import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2020/12/28 9:59
 */
public class ResourceAuditBean {

    /**
     * TotalCount : 2
     * list : [{"Id":"e9135cd4-f287-4043-8f9f-35dbb74896da","Name":"孙艺明","FullPath":"全部分类/人/四失五类/扬言报复","Status":"待处理","Time":"2020/12/25 13:59"},{"Id":"b03c0564-3055-43ca-b5bb-b1e7e0f2edd8","Name":"赵猛劲","FullPath":"全部分类/人/实有人口/人员基础信息表","Status":"待处理","Time":"2020/12/25 14:45"}]
     */

    private int TotalCount;
    private List<ListBean> list;

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * Id : e9135cd4-f287-4043-8f9f-35dbb74896da
         * Name : 孙艺明
         * FullPath : 全部分类/人/四失五类/扬言报复
         * Status : 待处理
         * Time : 2020/12/25 13:59
         */

        private String Id;
        private String Name;
        private String FullPath;
        private String Status;
        private String Time;
        private int StatusValue;


        protected ListBean(Parcel in) {
            Id = in.readString();
            Name = in.readString();
            FullPath = in.readString();
            Status = in.readString();
            Time = in.readString();
            StatusValue = in.readInt();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public int getStatusColor() {
            switch (StatusValue) {
                case 0:
                    return R.color.green;
                case 1:
                    return R.color.blue_4D8FF7;
                case 2:
                    return R.color.red;
                default:
            }
            return R.color.gray;
        }

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

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public int getStatusValue() {
            return StatusValue;
        }

        public void setStatusValue(int statusValue) {
            StatusValue = statusValue;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Id);
            dest.writeString(Name);
            dest.writeString(FullPath);
            dest.writeString(Status);
            dest.writeString(Time);
            dest.writeInt(StatusValue);
        }
    }
}

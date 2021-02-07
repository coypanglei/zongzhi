package com.weique.overhaul.v2.mvp.model;

import android.os.Parcel;

import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.TreeBaseDataBean;

import java.util.List;

public class SearchProjectBean {


    private List<DepartmentsBean> departments;
    private List<EntInfosBean> entInfos;

    public List<DepartmentsBean> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentsBean> departments) {
        this.departments = departments;
    }

    public List<EntInfosBean> getEntInfos() {
        return entInfos;
    }

    public void setEntInfos(List<EntInfosBean> entInfos) {
        this.entInfos = entInfos;
    }

    public static class DepartmentsBean extends TreeBaseDataBean<DepartmentsBean> {
        /**
         * Id : 84cdc58e-8491-4d67-b381-00b6737c4e71
         * Name : 茅村镇
         * PId : 65a18036-eaf6-48b8-ab43-e9ee188d3ade
         * FullPath : 徐州市铜山区/茅村镇/
         * EnumCommunityLevel : 2
         */

        private String PId;
        private String FullPath;
        private int EnumCommunityLevel;

        public static final Creator<DepartmentsBean> CREATOR = new Creator<DepartmentsBean>() {
            @Override
            public DepartmentsBean createFromParcel(Parcel in) {
                return new DepartmentsBean(in);
            }

            @Override
            public DepartmentsBean[] newArray(int size) {
                return new DepartmentsBean[size];
            }
        };


        protected DepartmentsBean(Parcel in) {
            super(in);
            PId = in.readString();
            FullPath = in.readString();
            EnumCommunityLevel = in.readInt();
        }

        public DepartmentsBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(PId);
            dest.writeString(FullPath);
            dest.writeInt(EnumCommunityLevel);
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

        public int getEnumCommunityLevel() {
            return EnumCommunityLevel;
        }

        public void setEnumCommunityLevel(int EnumCommunityLevel) {
            this.EnumCommunityLevel = EnumCommunityLevel;
        }

        @Override
        public int getLevel() {
            return StandardAddressStairBean.AREA - EnumCommunityLevel;
        }
    }

    public static class EntInfosBean {
        /**
         * Id : 238ab174-e2b4-4436-bf28-c25d62b1ae0a
         * Name : 121
         */

        private String Id;
        private String Name;

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
    }
}

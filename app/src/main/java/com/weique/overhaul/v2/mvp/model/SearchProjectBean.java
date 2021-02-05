package com.weique.overhaul.v2.mvp.model;

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

    public static class DepartmentsBean {
        /**
         * Id : 84cdc58e-8491-4d67-b381-00b6737c4e71
         * Name : 茅村镇
         * PId : 65a18036-eaf6-48b8-ab43-e9ee188d3ade
         * FullPath : 徐州市铜山区/茅村镇/
         * EnumCommunityLevel : 2
         */

        private String Id;
        private String Name;
        private String PId;
        private String FullPath;
        private int EnumCommunityLevel;

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

package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class AddressBookListBean {

    /**
     * pageCount : 1
     * list : [{"Id":"fb841f8b-a007-4ed3-b450-80dc13164b8d","Name":"小美","Tel":"13838383838","EMail":null,"DepartmentFullPath":"徐州市鼓楼区丰财社区/数字产业园网格"},{"Id":"0db5dc82-7c68-413d-bcbc-8ac86325ee28","Name":"测试管理员","Tel":"0516-88888888","EMail":null,"DepartmentFullPath":"徐州市鼓楼区丰财社区"},{"Id":"19a2455e-fca6-46f3-b9b9-ef17f17b6203","Name":"小三","Tel":"17878787878","EMail":null,"DepartmentFullPath":"徐州市鼓楼区丰财社区/数字产业园网格"}]
     * department : {"Name":"数字产业园网格","Code":"432451","Level":"网格"}
     * departmentUp : {"Name":"徐州市鼓楼区丰财社区","Code":"313442","Level":"社区"}
     */

    private int pageCount;
    private DepartmentBean department;
    private DepartmentUpBean departmentUp;
    private List<ListBean> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public DepartmentBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentBean department) {
        this.department = department;
    }

    public DepartmentUpBean getDepartmentUp() {
        return departmentUp;
    }

    public void setDepartmentUp(DepartmentUpBean departmentUp) {
        this.departmentUp = departmentUp;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class DepartmentBean {
        /**
         * Name : 数字产业园网格
         * Code : 432451
         * Level : 网格
         */

        private String Name;
        private String Code;
        private String Level;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getLevel() {
            return Level;
        }

        public void setLevel(String Level) {
            this.Level = Level;
        }
    }

    public static class DepartmentUpBean {
        /**
         * Name : 徐州市鼓楼区丰财社区
         * Code : 313442
         * Level : 社区
         */

        private String Name;
        private String Code;
        private String Level;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getLevel() {
            return Level;
        }

        public void setLevel(String Level) {
            this.Level = Level;
        }
    }

    public static class ListBean extends SignStaffListCommonBean {
        /**
         * Id : fb841f8b-a007-4ed3-b450-80dc13164b8d
         * Name : 小美
         * Tel : 13838383838
         * EMail : null
         * DepartmentFullPath : 徐州市鼓楼区丰财社区/数字产业园网格
         */

        private String Tel;
        private String EMail;
        private String DepartmentFullPath;
        private String HeadUrl;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getHeadUrl() {
            return HeadUrl;
        }

        public void setHeadUrl(String headUrl) {
            HeadUrl = headUrl;
        }


        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public String getEMail() {
            return EMail;
        }

        public void setEMail(String EMail) {
            this.EMail = EMail;
        }

        public String getDepartmentFullPath() {
            return DepartmentFullPath;
        }

        public void setDepartmentFullPath(String DepartmentFullPath) {
            this.DepartmentFullPath = DepartmentFullPath;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "Tel='" + Tel + '\'' +
                    ", EMail='" + EMail + '\'' +
                    ", DepartmentFullPath='" + DepartmentFullPath + '\'' +
                    ", HeadUrl='" + HeadUrl + '\'' +
                    ", isSelect=" + isSelect +
                    '}';
        }
    }
}

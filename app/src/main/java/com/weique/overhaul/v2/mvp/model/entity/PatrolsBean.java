package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class PatrolsBean {


    /**
     * list : [{"Id":"20ceb7b8-28ac-472d-be03-451b7c14dd28","Name":"1","Memo":"1","EnumElementProcType":1,"EnumExecutStatus":0,"ExecutTime":null}]
     * pageCount : 1
     */

    private int pageCount;
    private List<ListBean> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * Id : 20ceb7b8-28ac-472d-be03-451b7c14dd28
         * Name : 1
         * Memo : 1
         * EnumElementProcType : 1
         * EnumExecutStatus : 0
         * ExecutTime : null
         */

        private String Id;
        private String Name;
        private String Memo;
        private String DeadlineTime;
        private int EnumElementProcType;
        private int EnumExecutStatus;
        private String ExecutTime;

        public String getDeadlineTime() {
            return DeadlineTime;
        }

        public void setDeadlineTime(String deadlineTime) {
            DeadlineTime = deadlineTime;
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

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public int getEnumElementProcType() {
            return EnumElementProcType;
        }

        public void setEnumElementProcType(int EnumElementProcType) {
            this.EnumElementProcType = EnumElementProcType;
        }

        public int getEnumExecutStatus() {
            return EnumExecutStatus;
        }

        public void setEnumExecutStatus(int EnumExecutStatus) {
            this.EnumExecutStatus = EnumExecutStatus;
        }

        public String getExecutTime() {
            return ExecutTime;
        }

        public void setExecutTime(String ExecutTime) {
            this.ExecutTime = ExecutTime;
        }
    }
}

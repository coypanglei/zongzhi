package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;

import java.util.List;

public class TaskListBean {

    /**
     * pageCount : 1
     * list : [{"Name":"测试信息采集","EnumMissionType":0,"EnumMissionStatus":0,"PlanNum":20,"CompleteNum":0,"Id":"eec823e2-0754-406c-92a0-4c4473095607","Memo":"dfgdfgdfgd","EnumMissitonDateType":"每月","StartTime":"2020-02-01","DeadlineTime":"2020-02-29","MissionConditions":[{"ElementTypeId":"830f1379-258c-4200-914e-6f12486f5726","Name":"刑满释放人员","PlanNum":20}]}]
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
         * Name : 测试信息采集
         * EnumMissionType : 0
         * EnumMissionStatus : 0
         * PlanNum : 20
         * CompleteNum : 0
         * Id : eec823e2-0754-406c-92a0-4c4473095607
         * Memo : dfgdfgdfgd
         * EnumMissitonDateType : 每月
         * StartTime : 2020-02-01
         * DeadlineTime : 2020-02-29
         * MissionConditions : [{"ElementTypeId":"830f1379-258c-4200-914e-6f12486f5726","Name":"刑满释放人员","PlanNum":20}]
         */

        private String Name;
        /**
         * 0采集   1.走访  2. 巡查 3.签到   4.事件
         */
        private int EnumMissionType;
        /**
         * 任务状态  0.未完成  1.已完成  2.过期
         */
        private int EnumMissionStatus;
        /**
         * 需完成数量
         */
        private int PlanNum;

        /**
         * 已完成数量
         */
        private int CompleteNum;
        private String Id;
        private String Memo;
        //周期
        private String EnumMissitonDateType;
        //周期 具体时间
        private String StartTime;
        private String DeadlineTime;
        private List<MissionConditionsBean> MissionConditions;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getEnumMissionType() {
            return EnumMissionType;
        }

        public void setEnumMissionType(int EnumMissionType) {
            this.EnumMissionType = EnumMissionType;
        }

        public int getEnumMissionStatus() {
            return EnumMissionStatus;
        }

        public void setEnumMissionStatus(int EnumMissionStatus) {
            this.EnumMissionStatus = EnumMissionStatus;
        }

        public int getPlanNum() {
            return PlanNum;
        }

        public void setPlanNum(int PlanNum) {
            this.PlanNum = PlanNum;
        }

        public int getCompleteNum() {
            return CompleteNum;
        }

        public void setCompleteNum(int CompleteNum) {
            this.CompleteNum = CompleteNum;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public String getEnumMissitonDateType() {
            return EnumMissitonDateType;
        }

        public void setEnumMissitonDateType(String EnumMissitonDateType) {
            this.EnumMissitonDateType = EnumMissitonDateType;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public String getDeadlineTime() {
            return DeadlineTime;
        }

        public void setDeadlineTime(String DeadlineTime) {
            this.DeadlineTime = DeadlineTime;
        }

        public List<MissionConditionsBean> getMissionConditions() {
            return MissionConditions;
        }

        public void setMissionConditions(List<MissionConditionsBean> MissionConditions) {
            this.MissionConditions = MissionConditions;
        }

        public static class MissionConditionsBean implements Parcelable, NameAndIdInterface {
            /**
             * ElementTypeId : 830f1379-258c-4200-914e-6f12486f5726
             * Name : 刑满释放人员
             * PlanNum : 20
             */

            private String ElementTypeId;
            private String Name;
            private int PlanNum;

            protected MissionConditionsBean(Parcel in) {
                ElementTypeId = in.readString();
                Name = in.readString();
                PlanNum = in.readInt();
            }

            public MissionConditionsBean() {
            }

            public static final Creator<MissionConditionsBean> CREATOR = new Creator<MissionConditionsBean>() {
                @Override
                public MissionConditionsBean createFromParcel(Parcel in) {
                    return new MissionConditionsBean(in);
                }

                @Override
                public MissionConditionsBean[] newArray(int size) {
                    return new MissionConditionsBean[size];
                }
            };

            public String getElementTypeId() {
                return ElementTypeId;
            }

            public void setElementTypeId(String ElementTypeId) {
                this.ElementTypeId = ElementTypeId;
            }

            @Override
            public String getId() {
                return ElementTypeId;
            }

            @Override
            public String getId2() {
                return ElementTypeId;
            }

            @Override
            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public int getPlanNum() {
                return PlanNum;
            }

            public void setPlanNum(int PlanNum) {
                this.PlanNum = PlanNum;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(ElementTypeId);
                dest.writeString(Name);
                dest.writeInt(PlanNum);
            }
        }
    }
}

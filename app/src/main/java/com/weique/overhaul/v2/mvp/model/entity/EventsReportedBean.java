package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class EventsReportedBean {


    /**
     * 提交人
     */
    public static final int SUBMITER = 0;
    /**
     * 办理人
     */
    public static final int TRANSACTOR = 1;

    /**
     * pageCount : 1
     * list : [{"EventRecordId":"1fbe1ed0-e003-4206-ab06-5e86eb257aaa","EventFormTypeId":"b5ad8951-e125-463a-91d7-0acc1d2f92c3","Title":"路灯坏了，瞎黑","Name":"路灯损坏","Time":"2019-12-17","Status":"待处理","EnumEventProceedStatus":0}]
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

    public static class ListBean implements Parcelable {


        /**
         * 暂存
         */
        public static final String TS_S = "暂存";
        /**
         * 核查
         */
        public static final String AUDIT_S = "审核";
        /**
         * 办理
         */
        public static final String TRANSACTION_S = "办理";
        /**
         * 完成
         */
        public static final String COMPLETE_S = "完成";
        /**
         * 评价
         */
        public static final String EVALUATE_S = "评价";
        /**
         * 归档
         */
        public static final String ARCHIVE_S = "归档";
        /**
         * 退回
         */
        public static final String SENDBACK_S = "退回";
        /**
         * 作废
         */
        public static final String INVALID_S = "作废";


        public static final String[] stausArray = {TS_S, AUDIT_S, TRANSACTION_S, COMPLETE_S, EVALUATE_S, ARCHIVE_S, SENDBACK_S, INVALID_S
        };
        //public static final String[] stausArray1 = {"暂存", "核查上报", "正在处理", "处理完毕", "评价", "归档", "退回"};
        private String EventRecordId;
        private String CustId;
        private String Id;
        private String EventProceedNodeId;
        private String EventFormTypeId;
        private String Title;
        private String Content;
        private int EnumEventLevel;
        private String Name;
        private String Memo;
        private String Time;
        private String DeadlineTime;
        private String Status;
        private String DepartmentId;


        /**
         * 判断是不是主管单位
         */
        private boolean IsInCharge;
        /**
         * 判断是 右协同
         */
        private boolean IsJoint = false;
        /**
         * EnumEventProceedStatus  事件处理记录状态，表示如何处理该事件
         */
        private int EnumEventProceedStatus;

        /**
         * EnumEventProceedNodeActionStatus  用于表示该节点角色是否已经处理了该事件 0. 是未处理  1. 一处理
         */
        private int EnumEventProceedNodeActionStatus;
        private int EnumOrderStatus;

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            EventRecordId = in.readString();
            CustId = in.readString();
            Id = in.readString();
            EventProceedNodeId = in.readString();
            EventFormTypeId = in.readString();
            Title = in.readString();
            Content = in.readString();
            EnumEventLevel = in.readInt();
            Name = in.readString();
            Memo = in.readString();
            Time = in.readString();
            DeadlineTime = in.readString();
            Status = in.readString();
            EnumEventProceedStatus = in.readInt();
            EnumEventProceedNodeActionStatus = in.readInt();
            EnumOrderStatus = in.readInt();
            DepartmentId = in.readString();
            IsInCharge = in.readByte() != 0;
            IsJoint = in.readByte() != 0;
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
            dest.writeString(EventRecordId);
            dest.writeString(CustId);
            dest.writeString(Id);
            dest.writeString(EventProceedNodeId);
            dest.writeString(EventFormTypeId);
            dest.writeString(Title);
            dest.writeString(Content);
            dest.writeInt(EnumEventLevel);
            dest.writeString(Name);
            dest.writeString(Memo);
            dest.writeString(Time);
            dest.writeString(DeadlineTime);
            dest.writeString(Status);
            dest.writeInt(EnumEventProceedStatus);
            dest.writeInt(EnumEventProceedNodeActionStatus);
            dest.writeInt(EnumOrderStatus);
            dest.writeString(DepartmentId);
            dest.writeByte((byte) (IsInCharge ? 1 : 0));
            dest.writeByte((byte) (IsJoint ? 1 : 0));
        }

        //用 @IntDef "包住" 常量；
        // @Retention 定义策略
        // 声明构造器
        @IntDef({EventsReportedEnumBean.TS, EventsReportedEnumBean.AUDIT,
                EventsReportedEnumBean.TRANSACTION, EventsReportedEnumBean.INVALID,
                EventsReportedEnumBean.COMPLETE, EventsReportedEnumBean.EVALUATE,
                EventsReportedEnumBean.ARCHIVE, EventsReportedEnumBean.SENDBACK})
        @Retention(RetentionPolicy.SOURCE)
        public @interface EventsReportedEnumBean {
            /**
             * 暂存
             */
            int TS = -1;
            /**
             * 核查
             */
            int AUDIT = 0;
            /**
             * 办理
             */
            int TRANSACTION = 1;
            /**
             * 完成
             */
            int COMPLETE = 2;
            /**
             * 评价
             */
            int EVALUATE = 3;
            /**
             * 归档
             */
            int ARCHIVE = 4;
            /**
             * 退回
             */
            int SENDBACK = 5;
            /**
             * 作废
             */
            int INVALID = 6;

        }

        public String getEventProceedNodeId() {
            return EventProceedNodeId;
        }

        public void setEventProceedNodeId(String nodeId) {
            EventProceedNodeId = nodeId;
        }

        public String getCurrentIndexString(int currentIndex) {
            //找补  暂存状态 和 下标加的1
            if (currentIndex == EventsReportedEnumBean.SENDBACK) {
                return "已" + stausArray[currentIndex + 1];
            } else if (currentIndex == EventsReportedEnumBean.INVALID) {
                return "已" + stausArray[currentIndex + 1];
            } else if (currentIndex == EventsReportedEnumBean.ARCHIVE) {
                return "已" + stausArray[currentIndex + 1];
            } else if (currentIndex == EventsReportedEnumBean.TRANSACTION) {
                return "办理中";
            } else if (currentIndex == EventsReportedEnumBean.COMPLETE) {
                return "待" + stausArray[currentIndex + 2];
            } else if (currentIndex == EventsReportedEnumBean.TS) {
                return stausArray[currentIndex + 1];
            } else {
                return stausArray[currentIndex + 2] + "中";
            }
        }

        public String getEventRecordId() {
            return EventRecordId;
        }

        public void setEventRecordId(String EventRecordId) {
            this.EventRecordId = EventRecordId;
        }

        public String getEventFormTypeId() {
            return EventFormTypeId;
        }

        public void setEventFormTypeId(String EventFormTypeId) {
            this.EventFormTypeId = EventFormTypeId;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public int getEnumEventProceedStatus() {
            return EnumEventProceedStatus;
        }

        public void setEnumEventProceedStatus(int EnumEventProceedStatus) {
            this.EnumEventProceedStatus = EnumEventProceedStatus;
        }

        public String getCustId() {
            return CustId;
        }

        public void setCustId(String custId) {
            CustId = custId;
        }

        public int getEnumOrderStatus() {
            return EnumOrderStatus;
        }

        public void setEnumOrderStatus(int enumOrderStatus) {
            EnumOrderStatus = enumOrderStatus;
        }

        public int getEnumEventProceedNodeActionStatus() {
            return EnumEventProceedNodeActionStatus;
        }

        public void setEnumEventProceedNodeActionStatus(int enumEventProceedNodeActionStatus) {
            EnumEventProceedNodeActionStatus = enumEventProceedNodeActionStatus;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String memo) {
            Memo = memo;
        }

        public int getEnumEventLevel() {
            return EnumEventLevel;
        }

        public void setEnumEventLevel(int enumEventLevel) {
            EnumEventLevel = enumEventLevel;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(String departmentId) {
            DepartmentId = departmentId;
        }

        public boolean isInCharge() {
            return IsInCharge;
        }

        public void setInCharge(boolean inCharge) {
            IsInCharge = inCharge;
        }

        public boolean isJoint() {
            return IsJoint;
        }

        public void setJoint(boolean joint) {
            IsJoint = joint;
        }

        public String getDeadlineTime() {
            return DeadlineTime;
        }

        public void setDeadlineTime(String deadlineTime) {
            DeadlineTime = deadlineTime;
        }
    }
}

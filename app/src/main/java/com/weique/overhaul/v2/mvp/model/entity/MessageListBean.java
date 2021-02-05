package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MessageListBean {

    /**
     * pageCount : 1
     * list : [{"Id":"7ef826e9-8c02-43b2-ba07-4a7f59729603","SenderUserName":"乔斌","ReceiveUserName":"常宝麟","Message":"您上报的事件已处理完毕","CreateTime":"2020/01/08 14:25","IsRead":false},{"Id":"6cf93737-7b11-4ad4-a97e-7e0d40f28bd0","SenderUserName":"乔斌","ReceiveUserName":"常宝麟","Message":"您上报的事件已处理完毕","CreateTime":"2020/01/08 14:18","IsRead":false},{"Id":"2d53d33d-ab32-4093-a8f6-aa1348fc8279","SenderUserName":"乔斌","ReceiveUserName":"常宝麟","Message":"您上报的事件已处理完毕","CreateTime":"2020/01/08 14:04","IsRead":false},{"Id":"77bc1eb5-5cff-4f42-8ded-ae4bedc305da","SenderUserName":"乔斌","ReceiveUserName":"常宝麟","Message":"您上报的事件已处理完毕","CreateTime":"2020/01/08 13:46","IsRead":false},{"Id":"7b22cdc1-ecb7-4d3c-b9e2-bc5e64a34ea9","SenderUserName":"乔斌","ReceiveUserName":"常宝麟","Message":"您上报的事件已处理完毕","CreateTime":"2020/01/08 14:41","IsRead":false},{"Id":"a9ead8f4-91e3-422a-ac68-d0ca354e5a4a","SenderUserName":"乔斌","ReceiveUserName":"常宝麟","Message":"您上报的事件已处理完毕","CreateTime":"2020/01/08 16:01","IsRead":false}]
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
         * Id : 7e7ba694-b419-4722-90bb-e36d8d7886a1
         * SenderUserName : 测试管理员
         * ReceiveUserName : 测试管理员
         * Message : 消息标题
         * CreateTime : 2020/04/15 19:24
         * IsRead : false
         * EnumEventLevel : 0
         * Memo : 消息内容
         * URL : ["/Uploads/CustomerData/image/0_fMEvHsN-xlVzLw1H_637225754182666659.png","/Upl oads/CustomerData/image/0_fqwsnMwAXnAdWxDH_637225754187498943.png","/Uploads/CustomerData/image/2018- 2019-2城南课表637225754373528210.xls"]
         */

        private String Id;
        private String SenderUserName;
        private String ReceiveUserName;
        private String Message;
        private String CreateTime;
        private boolean IsRead;
        private int EnumEventLevel;
        private String Memo;
        private String URL;

        protected ListBean(Parcel in) {
            Id = in.readString();
            SenderUserName = in.readString();
            ReceiveUserName = in.readString();
            Message = in.readString();
            CreateTime = in.readString();
            IsRead = in.readByte() != 0;
            EnumEventLevel = in.readInt();
            Memo = in.readString();
            URL = in.readString();
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

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getSenderUserName() {
            return SenderUserName;
        }

        public void setSenderUserName(String SenderUserName) {
            this.SenderUserName = SenderUserName;
        }

        public String getReceiveUserName() {
            return ReceiveUserName;
        }

        public void setReceiveUserName(String ReceiveUserName) {
            this.ReceiveUserName = ReceiveUserName;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public boolean isIsRead() {
            return IsRead;
        }

        public void setIsRead(boolean IsRead) {
            this.IsRead = IsRead;
        }

        public int getEnumEventLevel() {
            return EnumEventLevel;
        }

        public void setEnumEventLevel(int EnumEventLevel) {
            this.EnumEventLevel = EnumEventLevel;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
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
            dest.writeString(SenderUserName);
            dest.writeString(ReceiveUserName);
            dest.writeString(Message);
            dest.writeString(CreateTime);
            dest.writeByte((byte) (IsRead ? 1 : 0));
            dest.writeInt(EnumEventLevel);
            dest.writeString(Memo);
            dest.writeString(URL);
        }
    }
}

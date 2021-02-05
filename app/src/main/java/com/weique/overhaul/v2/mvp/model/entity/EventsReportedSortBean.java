package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;

import java.util.List;

public class EventsReportedSortBean {

    /**
     * pageCount : 1
     * eventFormType : [{"Id":"f147b1e2-1690-4c85-b998-743a12fdacdb","Name":"风险类事件","PinYin":"SJLB1","Code":"00001","PId":"20e7e114-e832-44e4-bf7f-ae38dd05a219","DataStructureInJson":"{\"StructureInJson\":[{\"Identification\":{\"type\":\"SingleLine\",\"index\":0,\"initIdx\":0},\"name\":\"名称\",\"isRequired\":true,\"isNew\":false,\"isReveal\":true,\"placeholder\":\"请输入内容\",\"isQueryField\":true,\"partition\":false,\"propertyName\":\"name\"},{\"Identification\":{\"type\":\"MultiLine\",\"index\":1,\"initIdx\":1},\"name\":\"备注\",\"isRequired\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"memo\"},{\"Identification\":{\"type\":\"MultiLine\",\"index\":2,\"initIdx\":2},\"name\":\"地址\",\"isRequired\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"address\"}],\"QueryFields\":[\"name\"],\"FieldsLength\":3,\"initIdx\":4}","IsLeaf":true},{"Id":"0d1ba5c8-5033-451d-a5e8-9319b9ea1b46","Name":"事件分类2","PinYin":"SJFL2","Code":"00002","PId":"20e7e114-e832-44e4-bf7f-ae38dd05a219","DataStructureInJson":"{\"StructureInJson\":[{\"Identification\":{\"type\":\"SingleLine\",\"index\":0,\"initIdx\":0},\"name\":\"名称\",\"isRequired\":true,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"placeholder\":\"请输入内容\",\"isQueryField\":true,\"partition\":false,\"propertyName\":\"name\"},{\"Identification\":{\"type\":\"MultiLine\",\"index\":1,\"initIdx\":1},\"name\":\"备注\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"memo\"},{\"Identification\":{\"type\":\"MultiLine\",\"index\":2,\"initIdx\":2},\"name\":\"地址\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"addr\"},{\"Identification\":{\"type\":\"SinglePic\",\"index\":3,\"initIdx\":5},\"name\":\"封面上传\",\"isRequired\":true,\"partition\":false,\"default\":\"\",\"propertyName\":\"cover\",\"maxLength\":1,\"isNew\":false}],\"QueryFields\":[\"name\"],\"FieldsLength\":4,\"initIdx\":7}","IsLeaf":true}]
     */

    private int pageCount;
    private List<ListBean> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ListBean> getListBean() {
        return list;
    }

    public void setListBean(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean extends AbstractExpandableItem<ListBean> implements Parcelable {

        private String Id;
        private String Name;
        private String PinYin;
        private String Code;
        private String PId;
        private String StructureInJson;
        private String DefaultEventFormIconUrl;
        private boolean IsLeaf;
        private String Memo;
        private int Level = 2;


        public ListBean(String id, String name, String pinYin, String code,
                        String PId, String structureInJson,
                        String defaultEventFormIconUrl, boolean isLeaf, int level, String memo) {
            Id = id;
            Name = name;
            PinYin = pinYin;
            Code = code;
            this.PId = PId;
            StructureInJson = structureInJson;
            DefaultEventFormIconUrl = defaultEventFormIconUrl;
            IsLeaf = isLeaf;
            Memo = memo;
        }

        /**
         * Id : f147b1e2-1690-4c85-b998-743a12fdacdb
         * Name : 风险类事件
         * PinYin : SJLB1
         * Code : 00001
         * PId : 20e7e114-e832-44e4-bf7f-ae38dd05a219
         * DataStructureInJson : {"StructureInJson":[{"Identification":{"type":"SingleLine","index":0,"initIdx":0},"name":"名称","isRequired":true,"isNew":false,"isReveal":true,"placeholder":"请输入内容","isQueryField":true,"partition":false,"propertyName":"name"},{"Identification":{"type":"MultiLine","index":1,"initIdx":1},"name":"备注","isRequired":false,"isNew":false,"isReveal":true,"partition":false,"propertyName":"memo"},{"Identification":{"type":"MultiLine","index":2,"initIdx":2},"name":"地址","isRequired":false,"isNew":false,"isReveal":true,"partition":false,"propertyName":"address"}],"QueryFields":["name"],"FieldsLength":3,"initIdx":4}
         * IsLeaf : true
         */


        public ListBean() {
        }

        protected ListBean(Parcel in) {
            Id = in.readString();
            Name = in.readString();
            PinYin = in.readString();
            Code = in.readString();
            PId = in.readString();
            StructureInJson = in.readString();
            DefaultEventFormIconUrl = in.readString();
            IsLeaf = in.readByte() != 0;
            Level = in.readInt();
            Memo = in.readString();
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

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPinYin() {
            return PinYin;
        }

        public void setPinYin(String PinYin) {
            this.PinYin = PinYin;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getPId() {
            return PId;
        }

        public void setPId(String PId) {
            this.PId = PId;
        }

        public String getStructureInJson() {
            return StructureInJson;
        }

        public void setDataStructureInJson(String StructureInJson) {
            this.StructureInJson = StructureInJson;
        }

        public List<ListBean> getList() {
            return super.getSubItems();
        }

        public void setList(List<ListBean> list) {
           super.setSubItems(list);
        }

        @Override
        public int getLevel() {
            return Level;
        }

        public void setLevel(int level) {
            Level = level;
        }

        public boolean isIsLeaf() {
            return IsLeaf;
        }

        public void setIsLeaf(boolean IsLeaf) {
            this.IsLeaf = IsLeaf;
        }

        public String getDefaultEventFormIconUrl() {
            return DefaultEventFormIconUrl;
        }

        public void setDefaultEventFormIconUrl(String defaultEventFormIconUrl) {
            DefaultEventFormIconUrl = defaultEventFormIconUrl;
        }

        public boolean isLeaf() {
            return IsLeaf;
        }

        public void setLeaf(boolean leaf) {
            IsLeaf = leaf;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String memo) {
            Memo = memo;
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
            dest.writeString(Name);
            dest.writeString(PinYin);
            dest.writeString(Code);
            dest.writeString(PId);
            dest.writeString(StructureInJson);
            dest.writeString(DefaultEventFormIconUrl);
            dest.writeByte((byte) (IsLeaf ? 1 : 0));
            dest.writeInt(Level);
            dest.writeString(Memo);
        }
    }
}

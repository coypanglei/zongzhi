package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class BasicInformationBean implements Parcelable, MultiItemEntity {
    /**
     * 选择框
     */
    public static final int SELECT = 0;
    /**
     * 编辑
     */
    public static final int EDIT = 1;

    public static final int TIME = 2;
    /**
     * 地址
     */
    public static final int ADDRESS = 3;

    public static final int SELECT_CHANGE = 5;
    /**
     * 标题类型
     */
    public static final int TITLE = 4;
    public static final int EDIT_FULL = 6;
    /**
     * 图片类型
     */
    public static final int IMG = 7;

    /**
     * 大的输入框
     */
    public static final int LARGE_EDIT = 8;

    /**
     * 多个图片
     */
    public static final int MORE_IMG = 9;

    /**
     * 事项 流程 对象 样式
     * 布局{@link com.weique.overhaul.v2 / item_matter_flow.xml}
     * 对象 {@link com.weique.overhaul.v2.mvp.model.entity.MatterDetailsFlowBean}
     */
    public static final int MATTER_FLOW = 10;

    /**
     * total : 17
     */

    private List<RecordsBean> records;

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public static class RecordsBean implements Parcelable {
        public RecordsBean() {

        }

        public RecordsBean(String paramtype, String titile, String name, String defaultvalue, boolean require) {
            this.require = require;
            this.paramtype = paramtype;
            this.titile = titile;
            this.name = name;
            this.defaultvalue = defaultvalue;
        }

        public RecordsBean(String paramtype, String titile, String name, String defaultvalue) {
            this.paramtype = paramtype;
            this.titile = titile;
            this.name = name;
            this.defaultvalue = defaultvalue;
        }

        /**
         * 编辑的类型
         * paramtype : select
         * 标题
         * titile : 是否阳光信贷
         * 与后台相关的字段
         * name : sfygxk
         * 默认值
         * defaultvalue : null
         * <p>
         * ordernum : 1
         * optionlist : [{"name":"是"},{"name":"否"}] 数据集
         * type : 个人信息
         * category : 基本信息
         * dateformat : null
         * value  : 存储的数据
         * selectValue 存储选择数据的id
         */

        private String selectValue;


        public String getSelectValue() {
            return selectValue;
        }

        public void setSelectValue(String selectValue) {
            this.selectValue = selectValue;
        }

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        private int SpanSize;

        public int getSpanSize() {
            return SpanSize;
        }

        public void setSpanSize(int spanSize) {
            SpanSize = spanSize;
        }

        /**
         * 是否是编辑
         */
        private boolean edit = true;
        /**
         * 是否必填
         */
        private boolean require;

        public boolean isEdit() {
            return edit;
        }

        public void setEdit(boolean edit) {
            this.edit = edit;
        }

        public boolean getRequire() {
            return require;
        }

        public void setRequire(boolean require) {
            this.require = require;
        }

        /**
         * 特殊需求专用
         */
        private String changeType;

        public String getChangeType() {
            return changeType;
        }

        public void setChangeType(String changeType) {
            this.changeType = changeType;
        }

        public boolean isRequire() {
            return require;
        }

        private String paramtype;
        private String titile;
        private String name;
        private String defaultvalue;
        private int ordernum;
        private String type;
        private String category;
        private String dateformat;
        private List<NameAndIdBean> optionlist = new ArrayList<>();
        private List<RecordsBean> list = new ArrayList<>();

        public List<RecordsBean> getList() {
            return list;
        }

        public void setList(List<RecordsBean> list) {
            this.list = list;
        }

        public String getParamtype() {
            return paramtype;
        }

        public void setParamtype(String paramtype) {
            this.paramtype = paramtype;
        }

        public String getTitile() {
            return titile;
        }

        public void setTitile(String titile) {
            this.titile = titile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDefaultvalue() {
            return defaultvalue;
        }

        public void setDefaultvalue(String defaultvalue) {
            this.defaultvalue = defaultvalue;
        }

        public int getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(int ordernum) {
            this.ordernum = ordernum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDateformat() {
            return dateformat;
        }

        public void setDateformat(String dateformat) {
            this.dateformat = dateformat;
        }

        public List<NameAndIdBean> getOptionlist() {
            return optionlist;
        }

        public void setOptionlist(List<NameAndIdBean> optionlist) {
            this.optionlist = optionlist;
        }

        @Override
        public String toString() {
            return "RecordsBean{" +
                    "selectValue='" + selectValue + '\'' +
                    ", value='" + value + '\'' +
                    ", SpanSize=" + SpanSize +
                    ", edit=" + edit +
                    ", require='" + require + '\'' +
                    ", paramtype='" + paramtype + '\'' +
                    ", titile='" + titile + '\'' +
                    ", name='" + name + '\'' +
                    ", defaultvalue='" + defaultvalue + '\'' +
                    ", ordernum=" + ordernum +
                    ", type='" + type + '\'' +
                    ", category='" + category + '\'' +
                    ", dateformat='" + dateformat + '\'' +
                    ", optionlist=" + optionlist +
                    ", list=" + list +
                    '}';
        }

        public static class OptionlistBean {
            /**
             * name : 是
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.selectValue);
            dest.writeString(this.value);
            dest.writeInt(this.SpanSize);
            dest.writeByte(this.edit ? (byte) 1 : (byte) 0);
            dest.writeByte(this.require ? (byte) 1 : (byte) 0);
            dest.writeString(this.paramtype);
            dest.writeString(this.titile);
            dest.writeString(this.name);
            dest.writeString(this.defaultvalue);
            dest.writeInt(this.ordernum);
            dest.writeString(this.type);
            dest.writeString(this.category);
            dest.writeString(this.dateformat);
            dest.writeList(this.optionlist);
            dest.writeList(this.list);
            dest.writeString(this.changeType);
        }

        protected RecordsBean(Parcel in) {
            this.selectValue = in.readString();
            this.value = in.readString();
            this.SpanSize = in.readInt();
            this.edit = in.readByte() != 0;
            this.require = in.readByte() != 0;
            this.paramtype = in.readString();
            this.titile = in.readString();
            this.name = in.readString();
            this.defaultvalue = in.readString();
            this.ordernum = in.readInt();
            this.type = in.readString();
            this.category = in.readString();
            this.dateformat = in.readString();
            this.optionlist = new ArrayList<NameAndIdBean>();
            in.readList(this.optionlist, OptionlistBean.class.getClassLoader());
            this.list = new ArrayList<RecordsBean>();
            in.readList(this.list, RecordsBean.class.getClassLoader());
            this.changeType = in.readString();
        }

        public static final Creator<RecordsBean> CREATOR = new Creator<RecordsBean>() {
            @Override
            public RecordsBean createFromParcel(Parcel source) {
                return new RecordsBean(source);
            }

            @Override
            public RecordsBean[] newArray(int size) {
                return new RecordsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.records);
    }

    public BasicInformationBean() {
    }

    protected BasicInformationBean(Parcel in) {
        this.records = new ArrayList<RecordsBean>();
        in.readList(this.records, RecordsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<BasicInformationBean> CREATOR = new Parcelable.Creator<BasicInformationBean>() {
        @Override
        public BasicInformationBean createFromParcel(Parcel source) {
            return new BasicInformationBean(source);
        }

        @Override
        public BasicInformationBean[] newArray(int size) {
            return new BasicInformationBean[size];
        }
    };
}

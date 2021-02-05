package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GreatKing
 */
public class InformationDynamicFormSelectBean implements Parcelable {

    /**
     * StructureInJson : [{"Identification":{"type":"SingleLine","index":0},"name":"名称",
     * "isRequired":true,"isNew":false,"isReveal":true,
     * "placeholder":"请输入内容","isQueryField":true,"partition":false,
     * "propertyName":"name"},{"Identification":{"type":"MultiLine","index":1},
     * "name":"备注","isRequired":false,"isNew":false,"isReveal":true,"partition":false,
     * "propertyName":"memo"},{"Identification":{"type":"MultiLine","index":2},
     * "name":"地址","isRequired":false,"isNew":false,"isReveal":true,"partition":false,
     * "propertyName":"address"},{"Identification":{"type":"Option","index":3},
     * "name":"性别","isRequired":true,"partition":false,"option":["男","女"],"propertyName":"Option3","isNew":false}]
     * QueryFields : ["name"]
     * FieldsLength : 4
     * todo 这里暂时没有用枚举  会面需要再改
     */

    public static final String SingleLine = "SingleLine";
    public static final String MultiLine = "MultiLine";
    public static final String Number = "Number";
    public static final String SinglePic = "SinglePic";
    public static final String MultiPics = "MultiPics";
    public static final String DateTime = "DateTime";
    public static final String DropdownList = "DropdownList";
    public static final String Option = "Option";
    public static final String CheckBox = "CheckBox";
    public static final String SingleVideo = "SingleVideo";
    public static final String Autograph = "Autograph";
    public static final String IdCard = "IdCard";

    public static final int SingleLineN = 1;
    public static final int MultiLineN = 2;
    public static final int NumberN = 3;
    public static final int SinglePicN = 4;
    public static final int MultiPicsN = 5;
    public static final int DateTimeN = 6;
    public static final int DropdownListN = 7;
    public static final int OptionN = 8;
    public static final int CheckBoxN = 9;
    public static final int SingleVideoN = 10;
    public static final int AutographN = 11;
    public static final int IdCardN = 12;

    private int FieldsLength;
    private ArrayList<StructureInJsonBean> StructureInJson;
    private List<String> QueryFields;


    public static final Creator<InformationDynamicFormSelectBean> CREATOR = new Creator<InformationDynamicFormSelectBean>() {
        @Override
        public InformationDynamicFormSelectBean createFromParcel(Parcel in) {
            InformationDynamicFormSelectBean informationDynamicFormSelectBean = new InformationDynamicFormSelectBean();
            informationDynamicFormSelectBean.setFieldsLength(in.readInt());
            informationDynamicFormSelectBean.setStructureInJson(in.createTypedArrayList(StructureInJsonBean.CREATOR));
            informationDynamicFormSelectBean.setQueryFields(in.createStringArrayList());
            return informationDynamicFormSelectBean;
        }

        @Override
        public InformationDynamicFormSelectBean[] newArray(int size) {
            return new InformationDynamicFormSelectBean[size];
        }
    };

    public int getFieldsLength() {
        return FieldsLength;
    }

    public void setFieldsLength(int FieldsLength) {
        this.FieldsLength = FieldsLength;
    }

    public ArrayList<StructureInJsonBean> getStructureInJson() {
        return StructureInJson;
    }

    public void setStructureInJson(ArrayList<StructureInJsonBean> StructureInJson) {
        this.StructureInJson = StructureInJson;
    }

    public List<String> getQueryFields() {
        return QueryFields;
    }

    public void setQueryFields(List<String> QueryFields) {
        this.QueryFields = QueryFields;
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
        dest.writeInt(FieldsLength);
        dest.writeTypedList(StructureInJson);
        dest.writeStringList(QueryFields);
    }

    public static class StructureInJsonBean implements MultiItemEntity, Parcelable {
        public StructureInJsonBean() {
        }

        /**
         * Identification : {"type":"SingleLine","index":0}
         * name : 名称
         * isRequired : true
         * isNew : false
         * isReveal : true
         * placeholder : 请输入内容
         * isQueryField : true
         * partition : false
         * propertyName : name
         * option : ["男","女"]
         * listType : 0
         * defaultVal :
         * default :
         * maxLength : 2
         * defaultTime : 2019-11-28
         */

        private IdentificationBean Identification;
        private String name;
        private boolean isRequired;
        private boolean isNew;
        private boolean isReveal;
        //是否脱敏
        private boolean encryption;
        //是否禁用
        private boolean IsDisabled;
        private String placeholder;
        private boolean isQueryField;
        private boolean partition;
        private String propertyName;
        private String listType;
        private String defaultVal;
        @SerializedName("default")
        private String defaultX;
        private int maxLength;
        private String defaultTime;
        private List<String> option;
        /**
         * 是否有  合并文本 监听
         */
        private boolean hasMergeInfoListener = false;

        public static final Creator<StructureInJsonBean> CREATOR = new Creator<StructureInJsonBean>() {
            @Override
            public StructureInJsonBean createFromParcel(Parcel in) {
                StructureInJsonBean bean = new StructureInJsonBean();
                bean.setIdentification(in.readParcelable(IdentificationBean.class.getClassLoader()));
                bean.setName(in.readString());
                bean.setIsRequired(in.readByte() != 0);
                bean.setIsNew(in.readByte() != 0);
                bean.setIsReveal(in.readByte() != 0);
                bean.setEncryption(in.readByte() != 0);
                bean.setDisabled(in.readByte() != 0);
                bean.setPlaceholder(in.readString());
                bean.setIsQueryField(in.readByte() != 0);
                bean.setPartition(in.readByte() != 0);
                bean.setPropertyName(in.readString());
                bean.setListType(in.readString());
                bean.setDefaultVal(in.readString());
                bean.setDefaultX(in.readString());
                bean.setMaxLength(in.readInt());
                bean.setDefaultTime(in.readString());
                bean.setOption(in.createStringArrayList());
                bean.setHasMergeInfoListener(in.readByte() != 0);
                return bean;
            }

            @Override
            public StructureInJsonBean[] newArray(int size) {
                return new StructureInJsonBean[size];
            }
        };

        public boolean isHasMergeInfoListener() {
            return hasMergeInfoListener;
        }

        public void setHasMergeInfoListener(boolean hasMergeInfoListener) {
            this.hasMergeInfoListener = hasMergeInfoListener;
        }

        public IdentificationBean getIdentification() {
            return Identification;
        }

        public void setIdentification(IdentificationBean Identification) {
            this.Identification = Identification;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isIsRequired() {
            return isRequired;
        }

        public void setIsRequired(boolean isRequired) {
            this.isRequired = isRequired;
        }

        public boolean isIsNew() {
            return isNew;
        }

        public void setIsNew(boolean isNew) {
            this.isNew = isNew;
        }

        public boolean isIsReveal() {
            return isReveal;
        }

        public void setIsReveal(boolean isReveal) {
            this.isReveal = isReveal;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        public boolean isIsQueryField() {
            return isQueryField;
        }

        public void setIsQueryField(boolean isQueryField) {
            this.isQueryField = isQueryField;
        }

        public boolean isPartition() {
            return partition;
        }

        public void setPartition(boolean partition) {
            this.partition = partition;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getListType() {
            return listType;
        }

        public void setListType(String listType) {
            this.listType = listType;
        }

        public String getDefaultVal() {
            return defaultVal;
        }

        public void setDefaultVal(String defaultVal) {
            this.defaultVal = defaultVal;
        }

        public String getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(String defaultX) {
            this.defaultX = defaultX;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }

        public String getDefaultTime() {
            return defaultTime;
        }

        public void setDefaultTime(String defaultTime) {
            this.defaultTime = defaultTime;
        }

        public List<String> getOption() {
            return option;
        }

        public void setOption(List<String> option) {
            this.option = option;
        }

        public boolean isEncryption() {
            return encryption;
        }

        public void setEncryption(boolean encryption) {
            this.encryption = encryption;
        }

        public boolean isDisabled() {
            return IsDisabled;
        }

        public void setDisabled(boolean disabled) {
            IsDisabled = disabled;
        }

        @Override
        public int getItemType() {
            if (getIdentification() != null) {
                return getIntType(getIdentification().getType());
            } else {
                return -1;
            }
        }

        private int getIntType(String type) {
            switch (type) {
                case SingleLine:
                    return SingleLineN;
                case MultiLine:
                    return MultiLineN;
                case Number:
                    return NumberN;
                case SinglePic:
                    return SinglePicN;
                case MultiPics:
                    return MultiPicsN;
                case DateTime:
                    return DateTimeN;
                case DropdownList:
                    return DropdownListN;
                case Option:
                    return OptionN;
                case CheckBox:
                    return CheckBoxN;
                case SingleVideo:
                    return SingleVideoN;
                case Autograph:
                    return AutographN;
                case IdCard:
                    return IdCardN;
                default:
                    return SingleLineN;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(Identification, flags);
            dest.writeString(name);
            dest.writeByte((byte) (isRequired ? 1 : 0));
            dest.writeByte((byte) (isNew ? 1 : 0));
            dest.writeByte((byte) (isReveal ? 1 : 0));
            dest.writeByte((byte) (encryption ? 1 : 0));
            dest.writeByte((byte) (IsDisabled ? 1 : 0));
            dest.writeString(placeholder);
            dest.writeByte((byte) (isQueryField ? 1 : 0));
            dest.writeByte((byte) (partition ? 1 : 0));
            dest.writeString(propertyName);
            dest.writeString(listType);
            dest.writeString(defaultVal);
            dest.writeString(defaultX);
            dest.writeInt(maxLength);
            dest.writeString(defaultTime);
            dest.writeStringList(option);
            dest.writeByte((byte) (hasMergeInfoListener ? 1 : 0));
        }

        public static class IdentificationBean implements Parcelable {
            public IdentificationBean() {
            }

            /**
             * type : SingleLine
             * index : 0
             */


            private String type;
            private int index;

            public static final Creator<IdentificationBean> CREATOR = new Creator<IdentificationBean>() {
                @Override
                public IdentificationBean createFromParcel(Parcel in) {
                    String type = in.readString();
                    int index = in.readInt();
                    IdentificationBean part = new IdentificationBean();
                    part.setType(type);
                    part.setIndex(index);
                    return part;
                }

                @Override
                public IdentificationBean[] newArray(int size) {
                    return new IdentificationBean[size];
                }
            };

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
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
                dest.writeString(type);
                dest.writeInt(index);
            }

            @Override
            public String toString() {
                return "IdentificationBean{" +
                        "type='" + type + '\'' +
                        ", index=" + index +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "StructureInJsonBean{" +
                    "Identification=" + Identification +
                    ", name='" + name + '\'' +
                    ", isRequired=" + isRequired +
                    ", isNew=" + isNew +
                    ", isReveal=" + isReveal +
                    ", placeholder='" + placeholder + '\'' +
                    ", isQueryField=" + isQueryField +
                    ", partition=" + partition +
                    ", propertyName='" + propertyName + '\'' +
                    ", listType='" + listType + '\'' +
                    ", defaultVal='" + defaultVal + '\'' +
                    ", defaultX='" + defaultX + '\'' +
                    ", maxLength=" + maxLength +
                    ", defaultTime='" + defaultTime + '\'' +
                    ", option=" + option +
                    '}';
        }
    }
}

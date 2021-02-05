package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author GK
 * @description:
 * @date :2020/8/7 11:31
 */
public class ThirdPartyVerifyBean {

    /**
     * IsExist : false
     * UserInfo :
     */

    private boolean IsExist;
    private GlobalUserInfoBean UserInfo;
    private SgtUserInfo SGTUser;

    public boolean isIsExist() {
        return IsExist;
    }

    public void setIsExist(boolean IsExist) {
        this.IsExist = IsExist;
    }

    public GlobalUserInfoBean getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(GlobalUserInfoBean UserInfo) {
        this.UserInfo = UserInfo;
    }

    public SgtUserInfo getSGTUser() {
        return SGTUser;
    }

    public void setSGTUser(SgtUserInfo SGTUser) {
        this.SGTUser = SGTUser;
    }

    public static class SgtUserInfo implements Parcelable {

        /**
         * JSDM : 01
         * XZJDMC : 龙固镇
         * XZJDDM : 320322102000
         * XM : 曹呈军
         * GMSFHM : 320322198102101650
         * YDDH : 18852280012
         * XZQHDM : 320322000000
         * DWMC : 网格3
         * DWBM : 320322102207003
         * YHM : 320322198102101650
         * Id : 00000000-0000-0000-0000-000000000000
         * CreateUserId : 00000000-0000-0000-0000-000000000000
         * UpdateUserId : 00000000-0000-0000-0000-000000000000
         * CreateTime : /Date(-62135596800000)/
         * UpdateTime : /Date(-62135596800000)/
         * Memo : null
         * IsDeleted : false
         * DeletedTime : null
         */

        private String JSDM;
        private String XZJDMC;
        private String XZJDDM;
        private String XM;
        private String GMSFHM;
        private String YDDH;
        private String XZQHDM;
        private String DWMC;
        private String DWBM;
        private String YHM;
        private String Id;
        private String CreateUserId;
        private String UpdateUserId;
        private String CreateTime;
        private String UpdateTime;
        private String Memo;
        private boolean IsDeleted;
        private String DeletedTime;

        protected SgtUserInfo(Parcel in) {
            JSDM = in.readString();
            XZJDMC = in.readString();
            XZJDDM = in.readString();
            XM = in.readString();
            GMSFHM = in.readString();
            YDDH = in.readString();
            XZQHDM = in.readString();
            DWMC = in.readString();
            DWBM = in.readString();
            YHM = in.readString();
            Id = in.readString();
            CreateUserId = in.readString();
            UpdateUserId = in.readString();
            CreateTime = in.readString();
            UpdateTime = in.readString();
            Memo = in.readString();
            IsDeleted = in.readByte() != 0;
            DeletedTime = in.readString();
        }

        public static final Creator<SgtUserInfo> CREATOR = new Creator<SgtUserInfo>() {
            @Override
            public SgtUserInfo createFromParcel(Parcel in) {
                return new SgtUserInfo(in);
            }

            @Override
            public SgtUserInfo[] newArray(int size) {
                return new SgtUserInfo[size];
            }
        };

        public String getJSDM() {
            return JSDM;
        }

        public void setJSDM(String JSDM) {
            this.JSDM = JSDM;
        }

        public String getXZJDMC() {
            return XZJDMC;
        }

        public void setXZJDMC(String XZJDMC) {
            this.XZJDMC = XZJDMC;
        }

        public String getXZJDDM() {
            return XZJDDM;
        }

        public void setXZJDDM(String XZJDDM) {
            this.XZJDDM = XZJDDM;
        }

        public String getXM() {
            return XM;
        }

        public void setXM(String XM) {
            this.XM = XM;
        }

        public String getGMSFHM() {
            return GMSFHM;
        }

        public void setGMSFHM(String GMSFHM) {
            this.GMSFHM = GMSFHM;
        }

        public String getYDDH() {
            return YDDH;
        }

        public void setYDDH(String YDDH) {
            this.YDDH = YDDH;
        }

        public String getXZQHDM() {
            return XZQHDM;
        }

        public void setXZQHDM(String XZQHDM) {
            this.XZQHDM = XZQHDM;
        }

        public String getDWMC() {
            return DWMC;
        }

        public void setDWMC(String DWMC) {
            this.DWMC = DWMC;
        }

        public String getDWBM() {
            return DWBM;
        }

        public void setDWBM(String DWBM) {
            this.DWBM = DWBM;
        }

        public String getYHM() {
            return YHM;
        }

        public void setYHM(String YHM) {
            this.YHM = YHM;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getCreateUserId() {
            return CreateUserId;
        }

        public void setCreateUserId(String CreateUserId) {
            this.CreateUserId = CreateUserId;
        }

        public String getUpdateUserId() {
            return UpdateUserId;
        }

        public void setUpdateUserId(String UpdateUserId) {
            this.UpdateUserId = UpdateUserId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public boolean isIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(boolean IsDeleted) {
            this.IsDeleted = IsDeleted;
        }

        public String getDeletedTime() {
            return DeletedTime;
        }

        public void setDeletedTime(String DeletedTime) {
            this.DeletedTime = DeletedTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(JSDM);
            dest.writeString(XZJDMC);
            dest.writeString(XZJDDM);
            dest.writeString(XM);
            dest.writeString(GMSFHM);
            dest.writeString(YDDH);
            dest.writeString(XZQHDM);
            dest.writeString(DWMC);
            dest.writeString(DWBM);
            dest.writeString(YHM);
            dest.writeString(Id);
            dest.writeString(CreateUserId);
            dest.writeString(UpdateUserId);
            dest.writeString(CreateTime);
            dest.writeString(UpdateTime);
            dest.writeString(Memo);
            dest.writeByte((byte) (IsDeleted ? 1 : 0));
            dest.writeString(DeletedTime);
        }
    }
}

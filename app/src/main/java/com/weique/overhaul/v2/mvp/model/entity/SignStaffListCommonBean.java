package com.weique.overhaul.v2.mvp.model.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class SignStaffListCommonBean extends AbstractExpandableItem<SignStaffListCommonBean> {
    @SerializedName(value = "Name", alternate = {"name"})
    private String Name;
    @SerializedName(value = "Id", alternate = {"id"})
    private String Id;
    private int loginDay = -1;
    private int signOut = 0;
    private int level;
    private List<? extends SignStaffListCommonBean> list;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getLoginDay() {
        return loginDay;
    }

    public void setLoginDay(int loginDay) {
        this.loginDay = loginDay;
    }

    /**+
     * Get the level of this item. The level start from 0.
     * If you don't care about the level, just return a negative.
     */
    @Override
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<? extends SignStaffListCommonBean> getList() {
        return list;
    }

    public void setList(List<? extends SignStaffListCommonBean> list) {
        this.list = list;
    }

    public int getSignOut() {
        return signOut;
    }

    public void setSignOut(int signOut) {
        this.signOut = signOut;
    }
}

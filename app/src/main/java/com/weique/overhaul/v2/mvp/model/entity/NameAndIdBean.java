package com.weique.overhaul.v2.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;

/**
 * @author GK
 * @description:
 * @date :2020/8/9 16:39
 */
public class NameAndIdBean implements NameAndIdInterface {
    @SerializedName(value = "id", alternate = {"Id"})
    private String id;
    @SerializedName(value = "name", alternate = {"Name", "text"})
    private String name;
    private String UserInfoId;

    public NameAndIdBean() {
    }

    public NameAndIdBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public NameAndIdBean(String id, String UserInfoId, String name) {
        this.id = id;
        this.UserInfoId = UserInfoId;
        this.name = name;
    }

    public String getUserInfoId() {
        return UserInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        UserInfoId = userInfoId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getId2() {
        return UserInfoId;
    }

    @Override
    public String getName() {
        return name;
    }
}

package com.weique.overhaul.v2.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameIdCheckedInterface;

/**
 * @author GK
 * @description:
 * @date :2020/8/9 16:39
 */
public class NameAndIdBean implements NameAndIdInterface, NameIdCheckedInterface {
    @SerializedName(value = "id", alternate = {"Id"})
    private String id;
    @SerializedName(value = "name", alternate = {"Name"})
    private String name;
    private String UserInfoId;
    private boolean checked = false;

    public NameAndIdBean(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean getChecked() {
        return checked;
    }
}

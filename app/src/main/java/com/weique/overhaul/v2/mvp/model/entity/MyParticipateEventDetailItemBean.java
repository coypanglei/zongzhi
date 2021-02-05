package com.weique.overhaul.v2.mvp.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MyParticipateEventDetailItemBean implements MultiItemEntity {

    private int type;
    private String url;


    public MyParticipateEventDetailItemBean(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

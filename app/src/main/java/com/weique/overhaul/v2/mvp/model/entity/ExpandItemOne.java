package com.weique.overhaul.v2.mvp.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.weique.overhaul.v2.mvp.ui.adapter.QuickExpandableAdapter;

public class ExpandItemOne implements MultiItemEntity {
    private String title;
    private String value;
    private int superPos;

    public ExpandItemOne(String title, String value, int superPos) {
        this.title = title;
        this.value = value;
        this.superPos = superPos;
    }

    @Override
    public int getItemType() {
        return QuickExpandableAdapter.TYPE_LEVEL_1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSuperPos() {
        return superPos;
    }

    public void setSuperPos(int superPos) {
        this.superPos = superPos;
    }
}

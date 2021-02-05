package com.weique.overhaul.v2.mvp.model.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.weique.overhaul.v2.mvp.ui.adapter.QuickExpandableAdapter;

public class ExpandItem extends AbstractExpandableItem<ExpandItemOne> implements MultiItemEntity {

    private String title;
    private String value;

    public ExpandItem() {
    }

    public ExpandItem(String title, String value) {
        this.title = title;
        this.value = value;
    }

    @Override
    public int getLevel() {
        return QuickExpandableAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getItemType() {
        return QuickExpandableAdapter.TYPE_LEVEL_0;
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
}

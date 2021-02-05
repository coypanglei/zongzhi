package com.weique.overhaul.v2.mvp.model.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;

import java.util.List;

public class EconomicManagementListBean extends AbstractExpandableItem<EventsReportedSortBean.ListBean> {

    private int Level = 1;

    private String name;
    private String id;

    private boolean IsLeaf;


    public boolean isLeaf() {
        return IsLeaf;
    }

    public void setLeaf(boolean leaf) {
        IsLeaf = leaf;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EventsReportedSortBean.ListBean> getList() {
        return super.getSubItems();
    }

    public void setList(List<EventsReportedSortBean.ListBean> list) {
        super.setSubItems(list);
    }

    @Override
    public int getLevel() {
        return Level;
    }
}

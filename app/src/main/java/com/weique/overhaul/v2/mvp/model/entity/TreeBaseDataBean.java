package com.weique.overhaul.v2.mvp.model.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.google.gson.annotations.SerializedName;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.TreeDataInterface;

import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2020/8/10 17:39
 */
public class TreeBaseDataBean<T extends TreeDataInterface> extends AbstractExpandableItem<T> implements TreeDataInterface<T> {

    @SerializedName(value = "id", alternate = {"Id"})
    private String id;
    @SerializedName(value = "name", alternate = {"Name"})
    private String name;
    private int level;
    private boolean isLeaf = true;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    @Override
    public List<T> getList() {
        return super.getSubItems();
    }

    @Override
    public void setList(List<T> list) {
        super.setSubItems(list);
    }

    @Override
    public boolean isExpand() {
        return super.isExpanded();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public boolean isLeaf() {
        return isLeaf;
    }
}

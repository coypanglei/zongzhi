package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class EntInfoItemListBean {

    private String Charger;


    List<EntInfoItemBean> item;

    public String getCharger() {
        return Charger;
    }

    public void setCharger(String charger) {
        Charger = charger;
    }

    public List<EntInfoItemBean> getList() {
        return item;
    }

    public void setList(List<EntInfoItemBean> list) {
        this.item = list;
    }
}

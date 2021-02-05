package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

/**
 * @author  GK
 */
public class IntegratedWithItemBean {
    String label;
    List<HomeMenuItemBean> homeMenuItemBeans;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<HomeMenuItemBean> getHomeMenuItemBeans() {
        return homeMenuItemBeans;
    }

    public void setHomeMenuItemBeans(List<HomeMenuItemBean> homeMenuItemBeans) {
        this.homeMenuItemBeans = homeMenuItemBeans;
    }
}

package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

/**
 * @author GK
 * @description: 签到状态bean
 * @date :2020/6/24 13:52
 */
public class SigninStatusBean {
    boolean IsInGrid;
    boolean IsCheckInOrder;
    List<MySignListBean.ListBean> list;

    public boolean isInGrid() {
        return IsInGrid;
    }

    public void setInGrid(boolean inGrid) {
        IsInGrid = inGrid;
    }

    public boolean isCheckInOrder() {
        return IsCheckInOrder;
    }

    public void setCheckInOrder(boolean checkInOrder) {
        IsCheckInOrder = checkInOrder;
    }

    public List<MySignListBean.ListBean> getList() {
        return list;
    }

    public void setList(List<MySignListBean.ListBean> list) {
        this.list = list;
    }
}


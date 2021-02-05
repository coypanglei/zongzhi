package com.weique.overhaul.v2.dynamic.entity;

import java.util.List;

/**
 * 通用列表返回
 */
public class CommonBackBean<T> {


    private List<T> list;


   private int pageCount;


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}

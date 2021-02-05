package com.weique.overhaul.v2.mvp.model.entity;

public class CommonEnumBean {


    /**
     * key : 0
     * value : 投诉举报
     */

    private Integer key;
    private String value;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CommonEnumBean(String value, Integer key) {
        this.key = key;
        this.value = value;
    }
}

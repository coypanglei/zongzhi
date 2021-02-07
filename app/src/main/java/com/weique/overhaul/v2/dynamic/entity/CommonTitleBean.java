package com.weique.overhaul.v2.dynamic.entity;

import com.contrarywind.interfaces.IPickerViewData;

public class CommonTitleBean implements IPickerViewData {


    /**
     * key : 0
     * value : 投诉举报
     */

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CommonTitleBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getPickerViewText() {
        return value;
    }
}

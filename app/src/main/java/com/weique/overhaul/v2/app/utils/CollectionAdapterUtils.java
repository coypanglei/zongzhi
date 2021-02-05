package com.weique.overhaul.v2.app.utils;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionAdapterUtils {

    /**
     * 把所有参数转换 成map
     *
     * @return
     */
    public static Map<String, String> getMap(List<BasicInformationBean.RecordsBean> list) {
        Map<String, String> map = new HashMap<>();
        for (BasicInformationBean.RecordsBean bean : list) {
            if (ObjectUtils.isNotEmpty(bean.getValue())) {
                if (ObjectUtils.isNotEmpty(bean.getSelectValue())) {
                    map.put(bean.getName(), bean.getSelectValue());
                } else {
                    map.put(bean.getName(), bean.getValue());
                }
            } else {
                if (bean.getRequire()) {
                    ToastUtils.showLong(bean.getTitile() + "为必填项");
                    return null;
                }
            }
        }
        return map;
    }
}

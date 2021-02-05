package com.weique.overhaul.v2.mvp.model.entity.interfaces;

/**
 * 公共recycler view 公共字段回调
 *
 * @author GreatKing
 */
public interface NameIdCheckedInterface {
    /**
     * 获取id
     *
     * @return String
     */
    String getId();

    /**
     * 获取名称
     *
     * @return String
     */
    String getName();

    /**
     * 设置选中状态
     *
     * @param checked checked
     */
    void setChecked(boolean checked);

    /**
     * 是否是选中状态
     *
     * @return boolean
     */
    boolean getChecked();
}

package com.weique.overhaul.v2.mvp.model.entity.interfaces;

import java.util.List;

/**
 * 公共recycler view 公共字段回调
 *
 * @author GreatKing
 */
public interface TreeDataInterface<T extends TreeDataInterface> {
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
     * 等级
     *
     * @return int
     */
    int getLevel();

    /**
     * 是叶子节点
     *
     * @return boolean
     */
    boolean isLeaf();

    /**
     * 是展开的
     *
     * @return boolean
     */
    boolean isExpand();

    /**
     * @return List
     */
    List<T> getList();

    /**
     * @param list list
     */
    void setList(List<T> list);

}

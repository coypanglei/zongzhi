package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchItemBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class ResourceSearchAdapter extends BaseQuickAdapter<ResourceSearchItemBean.InterViewRecordBean, BaseViewHolder> {

    public ResourceSearchAdapter() {
        super(R.layout.resource_search_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, ResourceSearchItemBean.InterViewRecordBean item) {
        helper.setText(R.id.name, (item.getResourceName()));
        helper.setText(R.id.address, (item.getDepartmentFullPath()));
        helper.setText(R.id.time, (item.getCreateTime()));
        helper.setVisible(R.id.count, true);
        helper.setText(R.id.count, (String.format("%s次", item.getCount())));
    }
}

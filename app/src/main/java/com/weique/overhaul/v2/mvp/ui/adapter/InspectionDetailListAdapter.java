package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class InspectionDetailListAdapter extends BaseQuickAdapter<ResourceSearchDetailItemBean.ListBean, BaseViewHolder> {

    public InspectionDetailListAdapter() {
        super(R.layout.resource_search_detail_item1, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, ResourceSearchDetailItemBean.ListBean item) {
        try {
            helper.setText(R.id.tvAddress, (item.getFullPath()));
            helper.setText(R.id.tvRemind, (item.getMemo()));
            helper.setText(R.id.time, (item.getCreateTime()));
            helper.addOnClickListener(R.id.edit_btn);
            if (item.isComplete()) {
                helper.setGone(R.id.status, true)
                        .setGone(R.id.edit_btn, false);
            } else {
                helper.setGone(R.id.status, false)
                        .setGone(R.id.edit_btn, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

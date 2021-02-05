package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class ResourceSearchDetailAdapter extends BaseQuickAdapter<ResourceSearchDetailItemBean.ListBean, BaseViewHolder> {

    public ResourceSearchDetailAdapter() {
        super(R.layout.resource_search_detail_item, new ArrayList<>());
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
            if (item.getIVImageUrlsInJson().equals("[]")) {
                helper.setVisible(R.id.image_pic, false);
                helper.setVisible(R.id.line, false);
            } else {
                helper.setVisible(R.id.image_pic, true);
                helper.setVisible(R.id.line, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

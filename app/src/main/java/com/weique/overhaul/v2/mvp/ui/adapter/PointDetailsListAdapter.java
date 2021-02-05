package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.PointsDetailBean;

public class PointDetailsListAdapter extends BaseQuickAdapter<PointsDetailBean, BaseViewHolder> {

    public PointDetailsListAdapter() {
        super(R.layout.point_details_item, null);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PointsDetailBean item) {
        try {
            helper.setText(R.id.tv_name, item.getName());
            helper.setText(R.id.text, item.getDepartmentFullPath());
            helper.setText(R.id.tv_elementTypeName, item.getElementTypeName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

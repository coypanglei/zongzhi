package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.GridDataBean;

import java.util.List;

public class InformationDataStatisticsAdapter extends BaseQuickAdapter<GridDataBean, BaseViewHolder> {

    public InformationDataStatisticsAdapter(int layoutResId, @Nullable List<GridDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GridDataBean item) {
        try {
            // if (StringUtil.isNotNullString(item.getName())) {
            helper.setText(R.id.name, item.getName());

            helper.setText(R.id.memo, item.getCount() + "");
            helper.addOnClickListener(R.id.item_view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

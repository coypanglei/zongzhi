package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.PieChartBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class PieChartListAdapter extends BaseQuickAdapter<PieChartBean.Data2Bean, BaseViewHolder> {

    public PieChartListAdapter() {
        super(R.layout.item_pie, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, PieChartBean.Data2Bean item) {

        try {
            helper.setText(R.id.name, (item.getName()));
            helper.setText(R.id.size, (item.getSize() + ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

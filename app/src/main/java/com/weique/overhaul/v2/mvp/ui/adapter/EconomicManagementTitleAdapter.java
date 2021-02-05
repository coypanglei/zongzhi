package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.EconomicManageMianBean;

import java.util.ArrayList;

public class EconomicManagementTitleAdapter extends BaseQuickAdapter<EconomicManageMianBean.SummaryBean.AllListBean, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public EconomicManagementTitleAdapter() {
        super(R.layout.item_economic_management_title, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, EconomicManageMianBean.SummaryBean.AllListBean item) {
        helper.setText(R.id.tv_num, item.getNum()+"");

        helper.setText(R.id.tv_content, item.getName());
    }
}

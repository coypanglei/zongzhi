package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksOrderListBean;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksOrderStatusBean;

public class LawWorksOrderAdapter extends BaseQuickAdapter<LawWorksOrderListBean, BaseViewHolder> {
    /**
     *
     */
    public LawWorksOrderAdapter() {
        super(R.layout.fragment_law_works_order_item);
    }

    /**
     *
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, LawWorksOrderListBean item) {
        try {
            helper.setText(R.id.order_time, item.getCreateTimeStr())
                    .setText(R.id.title, item.getTitle())
                    .setText(R.id.initiator, item.getCreateEmployeeName())
                    .setText(R.id.order_state, LawWorksOrderStatusBean.getStatusStrByStatus(item.getOrderStatus()))
                    .setText(R.id.event_type, item.getCourtOrderTypeName())
                    .setText(R.id.event_theme, item.getCourtOrderSourcenName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

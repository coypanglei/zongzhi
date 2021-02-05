package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;

import java.util.ArrayList;

/**
 * @author GreatKing
 */
public class EventsReportedLookFlowAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int orderStatus = -1;

    public void setOrderStatus(int orderStatus) {
        if (this.orderStatus != orderStatus) {
            this.orderStatus = orderStatus + 1;
            notifyDataSetChanged();
        }
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public EventsReportedLookFlowAdapter() {
        super(R.layout.order_flow_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        try {
            int adapterPosition = helper.getAdapterPosition();
            helper.setText(R.id.flow_name, StringUtil.setText(item));
            if (adapterPosition < orderStatus) {
                helper.setTextColor(R.id.flow_name, ArmsUtils.getColor(mContext, R.color.blue_3982f6));
                helper.setImageResource(R.id.flow_status_image, R.drawable.icon_complete);
            } else if (adapterPosition == orderStatus) {
                helper.setTextColor(R.id.flow_name, ArmsUtils.getColor(mContext, R.color.blue_3982f6));
                helper.setImageResource(R.id.flow_status_image, R.drawable.unchecked);
            } else {
                helper.setTextColor(R.id.flow_name, ArmsUtils.getColor(mContext, R.color.blue_a4d8ff));
                helper.setImageResource(R.id.flow_status_image, R.drawable.upflow);
            }
            View view0 = helper.getView(R.id.line0);
            View view = helper.getView(R.id.line);
            if (adapterPosition == 0) {
                view0.setBackgroundColor(ArmsUtils.getColor(mContext, R.color.white));
                view.setBackgroundColor(ArmsUtils.getColor(mContext, R.color.lin2_a4d8ff));
            } else if (adapterPosition == mData.size() - 1) {
                view0.setBackgroundColor(ArmsUtils.getColor(mContext, R.color.lin2_a4d8ff));
                view.setBackgroundColor(ArmsUtils.getColor(mContext, R.color.white));
            } else {
                view0.setBackgroundColor(ArmsUtils.getColor(mContext, R.color.lin2_a4d8ff));
                view.setBackgroundColor(ArmsUtils.getColor(mContext, R.color.lin2_a4d8ff));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.EventProceedRecordBean;

import java.util.ArrayList;

/**
 * @author GreatKing
 */
public class EventsReportedAgentAdapter extends BaseQuickAdapter<EventProceedRecordBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public EventsReportedAgentAdapter() {
        super(R.layout.activity_events_reporte_dagent_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, EventProceedRecordBean item) {
        try {
            int adapterPosition = helper.getAdapterPosition();
            helper.setText(R.id.name, StringUtil.setText(item.getName()))
                    .setText(R.id.depart_name, "部门 ：" + StringUtil.setText(item.getDepartName()))
                    .setText(R.id.time, StringUtil.setText(item.getCreateTime()))
                    .addOnClickListener(R.id.all_item);
            if (item.isInCharge()) {
                helper.setText(R.id.status, StringUtil.setText(item.getEnumEventProceedStatus()));
            } else {
                helper.setText(R.id.status, ArmsUtils.getString(mContext, R.string.prove))
                        .setTextColor(R.id.status, ArmsUtils.getColor(mContext, R.color.gray_999));
            }
            if (mData.size() == 1) {
                helper.setVisible(R.id.up_line, false);
                helper.setVisible(R.id.down_line, false);
            } else if (adapterPosition == 0) {
                helper.setVisible(R.id.up_line, false);
                helper.setVisible(R.id.down_line, true);
            } else if (adapterPosition == mData.size() - 1) {
                helper.setVisible(R.id.up_line, true);
                helper.setVisible(R.id.down_line, false);
            } else {
                helper.setVisible(R.id.up_line, true);
                helper.setVisible(R.id.down_line, true);
            }
            ImageView imageView = helper.getView(R.id.head_portrait);
            if (StringUtil.isNotNullString(item.getHeadUrl())) {
                imageView.setVisibility(View.VISIBLE);
                GlideUtil.loadCircleImage(mContext, item.getHeadUrl(), imageView);
            } else {
                imageView.setImageResource(R.drawable.bu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

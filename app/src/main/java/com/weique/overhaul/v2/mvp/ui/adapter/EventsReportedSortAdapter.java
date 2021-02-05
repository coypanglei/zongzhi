package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedSortBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class EventsReportedSortAdapter extends BaseQuickAdapter<EventsReportedSortBean.ListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public EventsReportedSortAdapter() {
        super(R.layout.activity_events_reported_sort_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, EventsReportedSortBean.ListBean item) {
        try {
            helper.setText(R.id.sort_item, (item.getName()));
            helper.setVisible(R.id.arrow_icon, !item.isIsLeaf());
            helper.addOnClickListener(R.id.sort_item_layout);
            LinearLayout allItem = helper.getView(R.id.sort_item_layout);
            allItem.setPadding(ArmsUtils.dip2px(mContext, item.getLevel() * 10),
                    0, ArmsUtils.dip2px(mContext, item.getLevel() * 10),
                    0);
            ImageView imageView = helper.getView(R.id.icon_type);
            if (StringUtil.isNotNullString(item.getDefaultEventFormIconUrl())) {
                imageView.setVisibility(View.VISIBLE);
                GlideUtil.loadImage(mContext, item.getDefaultEventFormIconUrl(), helper.getView(R.id.icon_type));
            } else {
                imageView.setVisibility(View.GONE);
            }

            TextView eventSortMemo = helper.getView(R.id.event_sort_memo);
            if (StringUtil.isNullString(item.getMemo())) {
                eventSortMemo.setVisibility(View.GONE);
            } else {
                eventSortMemo.setVisibility(View.VISIBLE);
                eventSortMemo.setText("描述：" + item.getMemo());
//                helper.addOnClickListener(R.id.event_sort_memo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

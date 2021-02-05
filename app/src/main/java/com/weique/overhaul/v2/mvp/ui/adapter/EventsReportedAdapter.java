package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;

import java.util.ArrayList;

/**
 * @author GreatKing
 */
public class EventsReportedAdapter extends BaseQuickAdapter<EventsReportedBean.ListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public EventsReportedAdapter() {
        super(R.layout.activity_events_reported_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, EventsReportedBean.ListBean item) {
        try {
            helper.addOnClickListener(R.id.item_view);
            helper.setText(R.id.event_name, StringUtil.setText(item.getTitle()));
            helper.setText(R.id.event_sort_name, StringUtil.setText(item.getName()));
            helper.setText(R.id.record_time, StringUtil.setText(item.getTime()));
            helper.setText(R.id.status, StringUtil.setText(item.getCurrentIndexString(item.getEnumOrderStatus())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

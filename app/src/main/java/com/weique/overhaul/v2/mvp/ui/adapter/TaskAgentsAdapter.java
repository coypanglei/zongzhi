package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.TimeUtil;
import com.weique.overhaul.v2.mvp.model.entity.EventPriorityBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;

import java.util.ArrayList;

/**
 * @author GreatKing
 */
public class TaskAgentsAdapter extends BaseQuickAdapter<EventsReportedBean.ListBean, BaseViewHolder> {
    /**
     *
     */
    public TaskAgentsAdapter() {
        super(R.layout.home_recrcler_view_item, new ArrayList<>());
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
            helper.setText(R.id.title_text, (item.getTitle()))
                    .setText(R.id.intro, (item.getContent()))
                    .setText(R.id.start_time, (item.getTime()))
                    .setText(R.id.type_name, (item.getName()));
            TextView overdue = helper.getView(R.id.overdue);
            if (StringUtil.isNotNullString(item.getDeadlineTime())) {
                helper.setText(R.id.end_time, (item.getDeadlineTime()))
                        .setVisible(R.id.line_split, true);
                long deadlineTime = TimeUtil.StringToLong(item.getDeadlineTime());
                if (deadlineTime > System.currentTimeMillis()) {
                    overdue.setVisibility(View.GONE);
                } else {
                    overdue.setVisibility(View.VISIBLE);
                }
            } else {
                overdue.setVisibility(View.GONE);
                helper.setVisible(R.id.line_split, false);
            }
            TextView primary = helper.getView(R.id.primary);
            TextView secondary = helper.getView(R.id.secondary);
            if (item.isInCharge()) {
                primary.setVisibility(View.VISIBLE);
                secondary.setVisibility(View.GONE);
            } else {
                primary.setVisibility(View.GONE);
                secondary.setVisibility(View.VISIBLE);
            }
            EventPriorityBean eventPriorityBean = new EventPriorityBean();
            eventPriorityBean.setPriority(item.getEnumEventLevel());
            helper.setImageResource(R.id.event_image, eventPriorityBean.getdrawableId())
                    .setTextColor(R.id.event_text, ArmsUtils.getColor(mContext, eventPriorityBean.getColorId()))
                    .setText(R.id.event_text, (eventPriorityBean.getPriority()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

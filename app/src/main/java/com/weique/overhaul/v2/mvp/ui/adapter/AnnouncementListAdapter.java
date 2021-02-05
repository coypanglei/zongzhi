package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.AnnouncementListBean;
import com.weique.overhaul.v2.mvp.model.entity.EventPriorityBean;

import java.util.ArrayList;

/**
 * @author GreatKing
 */
public class AnnouncementListAdapter extends BaseQuickAdapter<AnnouncementListBean.ListBean, BaseViewHolder> {
    /**
     *
     */
    public AnnouncementListAdapter() {
        super(R.layout.activity_message_list_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, AnnouncementListBean.ListBean item) {
        //取这里的资源 EventPriorityBean.drawableId
        try {
            if (item.getEnumNoticeLevel() > 2 || item.getEnumNoticeLevel() < 0) {
                item.setEnumNoticeLevel(0);
            }
            helper.setText(R.id.title, (item.getTitle()))
                    .setText(R.id.content, (item.getSummary()))
                    .setBackgroundRes(R.id.is_reading, EventPriorityBean.drawableId[item.getEnumNoticeLevel()])
                    .setText(R.id.time, (item.getCreateTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

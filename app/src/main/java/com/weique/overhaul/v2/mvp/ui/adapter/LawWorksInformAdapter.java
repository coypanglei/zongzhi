package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksInformBean;

/**
 * @author GreatKing
 */
public class LawWorksInformAdapter extends BaseQuickAdapter<LawWorksInformBean, BaseViewHolder> {
    public LawWorksInformAdapter() {
        super(R.layout.fragment_law_works_inform_item);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, LawWorksInformBean item) {
        try {
            helper.setText(R.id.inform_title, item.getTitle())
                    .setText(R.id.inform_time, item.getCreateTimeStr())
                    .setText(R.id.inform_type, item.getCourtNotifTypeName());
            TextView textView = helper.getView(R.id.read_status);
            if (item.getRead()) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

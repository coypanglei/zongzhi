package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.PartyAnswerItemBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class PartyAnswerAdapter extends BaseQuickAdapter<PartyAnswerItemBean.SubjectBean, BaseViewHolder> {

    public PartyAnswerAdapter() {
        super(R.layout.party_answer_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, PartyAnswerItemBean.SubjectBean item) {
        try {
            helper.addOnClickListener(R.id.btn_start_answer);
            helper.setText(R.id.title, (item.getTitle()));
            helper.setText(R.id.type, (item.getType()));
            helper.setText(R.id.time, (item.getCreateTime()));
            if ("已完成".equals(item.getType())) {
                helper.setText(R.id.btn_start_answer, item.getType());
            } else {
                helper.setText(R.id.btn_start_answer, "开始答题");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

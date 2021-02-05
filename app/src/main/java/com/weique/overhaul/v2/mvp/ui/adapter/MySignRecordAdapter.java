package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.MySignListBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class MySignRecordAdapter extends BaseQuickAdapter<MySignListBean.ListBean, BaseViewHolder> {

    public MySignRecordAdapter() {
        super(R.layout.my_sign_list_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, MySignListBean.ListBean item) {

        try {
            helper.setText(R.id.address, (item.getAddress()))
                    .setText(R.id.time, (item.getCreateTime()))
                    .setText(R.id.name, (item.getName()));

            if (item.isInGrid()) {
                String signText = "";
                if (item.getEnumCheckInStatus() == 0) {
                    signText = "网格内签到";
                } else {
                    signText = "网格内签退";
                }
                helper.setText(R.id.reseau, signText)
                        .setTextColor(R.id.reseau, ArmsUtils.getColor(mContext, R.color.green));
            } else {
                String signText = "";
                if (item.getEnumCheckInStatus() == 0) {
                    signText = "网格外签到";
                } else {
                    signText = "网格外签退";
                }
                helper.setText(R.id.reseau, signText)
                        .setTextColor(R.id.reseau, ArmsUtils.getColor(mContext, R.color.red));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

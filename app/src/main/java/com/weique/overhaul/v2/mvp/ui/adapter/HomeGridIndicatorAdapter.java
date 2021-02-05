package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;

import java.util.List;

public class HomeGridIndicatorAdapter extends BaseQuickAdapter<Boolean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public HomeGridIndicatorAdapter(int layoutResId, @Nullable List<Boolean> data) {
        super(layoutResId, data);
    }

    int checkedPos = -1;

    public void setItemChanged(int pos) {
        try {
            if (pos == checkedPos || mData.size() <= 0) {
                return;
            }
            if (checkedPos >= 0) {
                mData.set(checkedPos, false);
                notifyItemChanged(checkedPos);
            }
            if (pos >= 0) {
                mData.set(pos, true);
                notifyItemChanged(pos);
                checkedPos = pos;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Boolean item) {
        try {
            if (item) {
                checkedPos = helper.getAdapterPosition();
                helper.setBackgroundRes(R.id.indicator_item, R.drawable.shape_b_789afb_c_3);
            } else {
                helper.setBackgroundRes(R.id.indicator_item, R.drawable.shape_b_d8d8d8_c_3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

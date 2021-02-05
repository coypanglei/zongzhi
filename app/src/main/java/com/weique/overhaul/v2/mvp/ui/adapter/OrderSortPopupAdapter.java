package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.CommonEnumBean;

import java.util.List;

public class OrderSortPopupAdapter extends BaseQuickAdapter<CommonEnumBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    private int pos = 0;

    public OrderSortPopupAdapter(List<CommonEnumBean> list) {
        super(R.layout.choice_item, list);
    }

    public OrderSortPopupAdapter() {
        super(R.layout.choice_item);
    }


    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, CommonEnumBean item) {
        try {
            int position = helper.getLayoutPosition();
            if (position == pos) {
                helper.setTextColor(R.id.choice_text, ArmsUtils.getColor(mContext, R.color.blue_4D8FF7));
                helper.setBackgroundRes(R.id.choice_text, R.drawable.shape_b_eaf1fd_c_19);
            } else {
                helper.setTextColor(R.id.choice_text, ArmsUtils.getColor(mContext, R.color.black_333));
                helper.setBackgroundRes(R.id.choice_text, R.drawable.shape_b_lucency_c_19);
            }
            helper.setText(R.id.choice_text, (item.getValue()));
            helper.addOnClickListener(R.id.sort_item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCheckPos(int pos) {
        this.pos = pos;
        notifyDataSetChanged();
    }

    public int getCheckPos() {
        return pos;
    }

    public CommonEnumBean getCheckItem() {
        return getItem(pos);
    }
}

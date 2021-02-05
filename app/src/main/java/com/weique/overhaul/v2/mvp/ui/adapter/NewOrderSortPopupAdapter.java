package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;

import java.util.List;

public class NewOrderSortPopupAdapter<T extends NameAndIdInterface> extends BaseQuickAdapter<T, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    private int pos = -2;

    public NewOrderSortPopupAdapter(List<T> list) {
        super(R.layout.choice_item, list);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, T item) {
        try {
            int position = helper.getLayoutPosition();
            if (position == pos) {
                helper.setTextColor(R.id.choice_text, ArmsUtils.getColor(mContext, R.color.blue_4D8FF7));
                helper.setBackgroundRes(R.id.choice_text, R.drawable.shape_b_eaf1fd_c_19);
            } else {
                helper.setTextColor(R.id.choice_text, ArmsUtils.getColor(mContext, R.color.black_333));
                helper.setBackgroundRes(R.id.choice_text, R.drawable.shape_b_lucency_c_19);
            }
            helper.setText(R.id.choice_text, (item.getName()));
            helper.addOnClickListener(R.id.sort_item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCheckPos(int pos) {
        this.pos = pos;
        notifyDataSetChanged();
    }
}

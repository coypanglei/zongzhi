package com.weique.overhaul.v2.mvp.ui.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;

/**
 * @author GK
 */
public class PrefectureScreenThreeAdapter extends BaseQuickAdapter<InformationTypeOneSecondBean.ElementTypeListBean, BaseViewHolder> {
    int mPos = 0;

    /**
     * android:background="@drawable/shape_b_2483f5_c_4"
     * android:background="@drawable/shape_b_dadada_c_4"
     * <p>
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public PrefectureScreenThreeAdapter() {
        super(R.layout.popup_prefecture_three_item);
    }

    public void setCheckedPos(int pos) {
        if (mPos == pos) {
            return;
        }
        int lastPos = mPos;
        this.mPos = pos;
        notifyItemChanged(mPos);
        notifyItemChanged(lastPos);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, InformationTypeOneSecondBean.ElementTypeListBean item) {
        try {
            int adapterPosition = helper.getAdapterPosition();
            TextView tView = helper.getView(R.id.item_text);
            tView.setText(StringUtil.setText(item.getName()));
            if (adapterPosition == mPos) {
                tView.setBackgroundResource(R.drawable.shape_b_2483f5_c_4);
                tView.setTextColor(ArmsUtils.getColor(mContext, R.color.white));
            } else {
                tView.setBackgroundResource(R.drawable.shape_b_dadada_c_4);
                tView.setTextColor(ArmsUtils.getColor(mContext, R.color.black_333));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCheckedItem() {
        try {
            if (getItem(mPos) != null) {
                return getItem(mPos).getId();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

package com.weique.overhaul.v2.mvp.ui.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;

import java.util.Objects;

/**
 * @author GK
 */
public class PrefectureScreenSecondAdapter extends BaseQuickAdapter<InformationTypeOneSecondBean.ElementTypeListBean, BaseViewHolder> {
    int mPos = 0;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public PrefectureScreenSecondAdapter() {
        super(R.layout.popup_prefecture_second_item);
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
                tView.setBackgroundResource(R.color.white);
                tView.setTextColor(ArmsUtils.getColor(mContext, R.color.colorPrimary));
            } else {
                tView.setBackgroundResource(R.color.transparent);
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
            }else{
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

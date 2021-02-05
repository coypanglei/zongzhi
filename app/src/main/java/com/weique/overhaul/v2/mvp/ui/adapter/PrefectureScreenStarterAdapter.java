package com.weique.overhaul.v2.mvp.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;

import java.util.Objects;

/**
 * @author GK
 */
public class PrefectureScreenStarterAdapter extends BaseQuickAdapter<InformationTypeOneSecondBean.ElementTypeListBean, BaseViewHolder> {
    int mPos = 0;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public PrefectureScreenStarterAdapter() {
        super(R.layout.popup_prefecture_item);
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
            TextView tView = helper.getView(R.id.text_);
            tView.setText(StringUtil.setText(item.getName()));
            ImageView imageView = helper.getView(R.id.image);
            if (StringUtil.isNullString(item.getElementTypeIconURL())) {
                if (StringUtil.isNullString(item.getElementTypeIconURL())) {
                    imageView.setImageResource(R.drawable.null_content);
                } else {
                    GlideUtil.loadImage(mContext, item.getDefaultElementTypeGISIconUrl(), imageView);
                }
            } else {
                GlideUtil.loadImage(mContext, item.getElementTypeIconURL(), imageView);
            }
            if (adapterPosition == mPos) {
                tView.setBackgroundResource(R.color.white_9);
                tView.setTextColor(ArmsUtils.getColor(mContext, R.color.colorPrimary));
                helper.itemView.setScaleX(0.9f);
                helper.itemView.setScaleY(0.9f);
            } else {
                tView.setBackgroundResource(R.color.transparent);
                tView.setTextColor(ArmsUtils.getColor(mContext, R.color.black_333));
                helper.itemView.setScaleX(1.0f);
                helper.itemView.setScaleY(1.0f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

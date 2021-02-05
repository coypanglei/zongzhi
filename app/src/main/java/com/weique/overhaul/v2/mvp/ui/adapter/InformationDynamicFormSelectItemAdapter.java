package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.GlideUtil;

import java.util.List;

/**
 * @author GreatKing
 */
public class InformationDynamicFormSelectItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public InformationDynamicFormSelectItemAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        try {
            if (item.endsWith(Constant.MP4)) {
                GlideUtil.getLoadVideoBitmap(mContext, item, helper.getView(R.id.image_));
            } else {
                GlideUtil.loadImage(mContext, item, helper.getView(R.id.image_));
            }
            helper.setVisible(R.id.remove_image, false);
            helper.addOnClickListener(R.id.image_);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

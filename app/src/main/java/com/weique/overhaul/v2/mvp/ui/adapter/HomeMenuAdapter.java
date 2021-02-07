package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.HomeMenuItemBean;

import java.util.List;

/**
 * @author  GK
 */
public class HomeMenuAdapter extends BaseQuickAdapter<HomeMenuItemBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public HomeMenuAdapter(int layoutResId, @Nullable List<HomeMenuItemBean> data) {
        super(layoutResId, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeMenuItemBean item) {
        try {
            helper.setText(R.id.text_, (item.getName()));
            ImageView imageView = helper.getView(R.id.image);
            if (StringUtil.isNullString(item.getIconURL())) {
                imageView.setImageResource(item.getIconDrawable());
            } else {
                GlideUtil.loadImage(mContext, item.getIconURL(), imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

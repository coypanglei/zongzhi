package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.IntegratedWithItemBean;

import java.util.List;

/**
 * @author  GK
 */
public class IntegratedWithRcAdapter extends BaseQuickAdapter<IntegratedWithItemBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public IntegratedWithRcAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }


    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, IntegratedWithItemBean item) {
        try {
            helper.setText(R.id.label, StringUtil.setText(item.getLabel()));
            RecyclerView recyclerView = helper.getView(R.id.item_label);
            ArmsUtils.configRecyclerView(recyclerView,new GridLayoutManager(mContext, 5));
            recyclerView.setAdapter(new HomeMenuAdapter(R.layout.home_grid_item, item.getHomeMenuItemBeans()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

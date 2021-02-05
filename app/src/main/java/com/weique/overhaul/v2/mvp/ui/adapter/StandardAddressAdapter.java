package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

import java.util.List;

/**
 * @author GK
 */
public class StandardAddressAdapter extends BaseQuickAdapter<StandardAddressStairBean.StandardAddressStairBaseBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public StandardAddressAdapter(int layoutResId, @Nullable List<StandardAddressStairBean.StandardAddressStairBaseBean> data) {
        super(layoutResId, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, StandardAddressStairBean.StandardAddressStairBaseBean item) {
        helper.setText(R.id.name, (item.getName()));
        helper.addOnClickListener(R.id.input);
//        TextView view = helper.getView(R.id.subhead);
//        view.setVisibility(View.GONE);

    }
}

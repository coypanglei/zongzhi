package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressUpItemBean;

import java.util.ArrayList;

public class StandardAddressUpListAdapter extends BaseQuickAdapter<StandardAddressUpItemBean.DataBean, BaseViewHolder> {
    /**
     *
     */
    public StandardAddressUpListAdapter() {
        super(R.layout.standard_up_list_item, new ArrayList());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, StandardAddressUpItemBean.DataBean item) {
        helper.setText(R.id.name, (item.getName()));
        helper.addOnClickListener(R.id.name);
    }
}

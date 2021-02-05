package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;

public class SearchEconoicAdapter<T extends NameAndIdInterface> extends BaseQuickAdapter<T, BaseViewHolder> {
    public SearchEconoicAdapter() {
        super(R.layout.item_search_econoic);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NameAndIdInterface item) {
        try {
            helper.addOnClickListener(R.id.name);
            helper.addOnClickListener(R.id.iv_delete);
            helper.addOnClickListener(R.id.iv_alert);
            helper.setText(R.id.name, item.getName());
            helper.setTag(R.id.name, item.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

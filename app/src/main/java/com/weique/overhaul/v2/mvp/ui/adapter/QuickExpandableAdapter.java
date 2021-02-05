package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.ExpandItem;
import com.weique.overhaul.v2.mvp.model.entity.ExpandItemOne;

import java.util.List;


public class QuickExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public QuickExpandableAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.view_expand_0);
        addItemType(TYPE_LEVEL_1, R.layout.view_expand_1);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                ExpandItem expandItem = ((ExpandItem) item);
                helper.setText(R.id.text, expandItem.getTitle());
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (expandItem.isExpanded()) {
                        collapse(pos);
                    } else {
                        expand(pos);
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final ExpandItemOne expandItemOne = (ExpandItemOne) item;
                helper.setText(R.id.text, expandItemOne.getTitle());
                break;
            default:
                break;
        }
    }
}

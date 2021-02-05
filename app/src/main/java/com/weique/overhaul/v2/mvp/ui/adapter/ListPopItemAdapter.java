package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBean;

import java.util.List;

public class ListPopItemAdapter extends BaseQuickAdapter<KnowledgeBean , BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public ListPopItemAdapter(List<KnowledgeBean> typesBeans) {
        super(R.layout.list_pop_item, typesBeans);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, KnowledgeBean item) {

        helper.setText(R.id.content, (item.getName()));
    }
}

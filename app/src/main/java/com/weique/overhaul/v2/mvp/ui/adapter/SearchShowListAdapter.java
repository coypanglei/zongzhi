package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.SearchShowListBean;

/**
 * @author GK
 * @description:
 * @date :2020/8/11 18:55
 */
public class SearchShowListAdapter<T extends SearchShowListBean> extends BaseQuickAdapter<T, BaseViewHolder> {
    public SearchShowListAdapter() {
        super(R.layout.dialog_search_show_list_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, T item) {
        try {
            helper.setText(R.id.view_one, "名称：" + item.getViewOneData())
                    .setText(R.id.view_two, "编号：" + item.getViewTwoData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;

import java.util.ArrayList;

import butterknife.BindView;

public class BasePopupWindowAdapter extends BaseQuickAdapter<CommonTitleBean, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public BasePopupWindowAdapter() {
        super(R.layout.popup_issue_type_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, CommonTitleBean item) {
        helper.setText(R.id.item_text, item.getValue());
        if (helper.getAdapterPosition() == 0) {
            helper.setGone(R.id.item_view, false);
        }


    }
}

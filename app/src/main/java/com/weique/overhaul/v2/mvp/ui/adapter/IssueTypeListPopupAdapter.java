package com.weique.overhaul.v2.mvp.ui.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;

import java.util.ArrayList;

public class IssueTypeListPopupAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public IssueTypeListPopupAdapter() {
        super(R.layout.popup_issue_type_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        TextView textView = helper.getView(R.id.item_text);
        textView.setText((item));
    }
}

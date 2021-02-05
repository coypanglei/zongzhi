package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionAddSearchItemBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class ContradictionAddSearchAdapter extends BaseQuickAdapter<ContradictionAddSearchItemBean.DepartmentBean, BaseViewHolder> {


    @BindView(R.id.address)
    TextView address;

    public ContradictionAddSearchAdapter() {
        super(R.layout.contradiction_add_search_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, ContradictionAddSearchItemBean.DepartmentBean item) {
        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);
            address.setText((item.getFullPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

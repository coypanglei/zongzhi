package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.PieChartBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class PieChartListAdapter2 extends BaseQuickAdapter<PieChartBean.Data3Bean, BaseViewHolder> {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.size)
    TextView size;

    public PieChartListAdapter2() {
        super(R.layout.item_pie, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, PieChartBean.Data3Bean item) {

        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);


            name.setText(item.getName());
            size.setText(String.format("%s", item.getSize()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

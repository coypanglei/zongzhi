package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.ScanResultBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class ScanResultListTwoAdapter extends BaseQuickAdapter<ScanResultBean.ListBean.ElementsBean, BaseViewHolder> {


    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.demo)
    TextView demo;
    @BindView(R.id.fill_line)
    View fillLine;

    public ScanResultListTwoAdapter() {
        super(R.layout.san_result_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, ScanResultBean.ListBean.ElementsBean item) {

        View convertView = helper.itemView;
        ButterKnife.bind(this, convertView);
        try {
            GlideUtil.loadRoundImage(mContext, StringUtil.setText(item.getCover()), pic, 5);

            name.setText(StringUtil.setText(item.getName()));
            demo.setText(StringUtil.setText(item.getElementTypeFullPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInterViewListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class BusinessInterviewAdapter extends BaseQuickAdapter<BusinessInterViewListBean.ListBean, BaseViewHolder> {


    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.content)
    TextView content;

    public BusinessInterviewAdapter() {
        super(R.layout.business_inter_view_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, BusinessInterViewListBean.ListBean item) {

        View convertView = helper.itemView;
        ButterKnife.bind(this, convertView);

        titleName.setText(StringUtil.setText(item.get企业名称()));
        time.setText(StringUtil.setText(item.get走访日期()));
        content.setText(StringUtil.setText(item.get问题和困难()));

    }
}

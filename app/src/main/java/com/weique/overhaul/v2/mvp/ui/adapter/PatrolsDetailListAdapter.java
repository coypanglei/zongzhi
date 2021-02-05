package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.PatrolsDetailItemBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class PatrolsDetailListAdapter extends BaseQuickAdapter<PatrolsDetailItemBean.ElementsBean, BaseViewHolder> {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.staus)
    TextView staus;

    public PatrolsDetailListAdapter() {
        super(R.layout.patrols_detail_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, PatrolsDetailItemBean.ElementsBean item) {
        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);
            name.setText((item.getName()));
            if (!item.isStatus()) {
                staus.setText("未处理");
                staus.setTextColor(ArmsUtils.getColor(mContext, R.color.orange));
            } else {
                staus.setText("已处理");
                staus.setTextColor(ArmsUtils.getColor(mContext, R.color.green_4fa74c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInformationDetailBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class BusinessInformationDetailAdapter extends BaseQuickAdapter<BusinessInformationDetailBean.PeopleBean.ListBean, BaseViewHolder> {


    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tv_sex)
    TextView tvSex;


    public BusinessInformationDetailAdapter() {
        super(R.layout.item_business_detail, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, BusinessInformationDetailBean.PeopleBean.ListBean item) {

        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);
            name.setText((item.getName()));
            tvSex.setText((item.getGender()) + "     " + (item.getAge()));
            helper.addOnClickListener(R.id.input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

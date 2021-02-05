package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInformation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class BusinessInformationAdapter extends BaseQuickAdapter<BusinessInformation.ListBean, BaseViewHolder> {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.is_work)
    TextView isWork;
    @BindView(R.id.belong_address)
    TextView belongAddress;
    @BindView(R.id.address_name)
    TextView addressName;


    public BusinessInformationAdapter() {
        super(R.layout.item_business, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, BusinessInformation.ListBean item) {

        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);
            name.setText((item.getName()));
            isWork.setText((item.getTel()));
            belongAddress.setText((item.getFullPath()));
            addressName.setText((item.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

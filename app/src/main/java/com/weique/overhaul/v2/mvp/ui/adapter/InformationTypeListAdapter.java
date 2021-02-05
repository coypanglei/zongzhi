package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class InformationTypeListAdapter extends BaseQuickAdapter<InformationTypeOneSecondBean.ElementTypeListBean, BaseViewHolder> {

    public InformationTypeListAdapter() {
        super(R.layout.standard_address_item, new ArrayList<>());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, InformationTypeOneSecondBean.ElementTypeListBean item) {
        try {
            if (StringUtil.isNotNullString(item.getAnotherName())) {
                helper.setText(R.id.name, StringUtil.setText(item.getAnotherName()));
            } else {
                helper.setText(R.id.name, StringUtil.setText(item.getName()));
            }
            helper.addOnClickListener(R.id.input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

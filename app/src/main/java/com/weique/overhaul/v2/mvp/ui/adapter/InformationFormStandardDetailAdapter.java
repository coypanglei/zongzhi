package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.TimeUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * @author GK
 */
public class InformationFormStandardDetailAdapter extends BaseQuickAdapter<InformationTypeOneSecondBean.ElementListBean, BaseViewHolder> {



    public InformationFormStandardDetailAdapter() {
        super(R.layout.information_type_second_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, InformationTypeOneSecondBean.ElementListBean item) {
        try {
            if (StringUtil.isNotNullString(item.getName())) {
                helper.setText(R.id.name, item.getName());
            } else if (StringUtil.isNotNullString(item.getSingleLine0())) {
                helper.setText(R.id.name, item.getSingleLine0());
            }

            helper.setText(R.id.memo, item.getFullPath());
            TextView eType = helper.getView(R.id.e_type);
            if (StringUtil.isNotNullString(item.getElementTypeName())) {
                eType.setVisibility(View.VISIBLE);
                eType.setText(item.getElementTypeName());
            } else {
                eType.setVisibility(View.GONE);
            }
            helper.addOnClickListener(R.id.input);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

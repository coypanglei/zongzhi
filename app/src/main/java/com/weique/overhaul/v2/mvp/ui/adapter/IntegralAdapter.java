package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.TimeUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.IntergralDetailsBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationCollectionActivity;

import java.util.ArrayList;

/**
 * @author GK
 */
public class IntegralAdapter extends BaseQuickAdapter<IntergralDetailsBean.ListBean, BaseViewHolder> {


    public IntegralAdapter() {
        super(R.layout.item_integral_details, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, IntergralDetailsBean.ListBean item) {
        try {
            helper.setText(R.id.tv_branch, StringUtil.setText(item.getScore() + "分"));
            helper.setText(R.id.tv_CreateTime, StringUtil.setText(item.getCreateTime() + ""));

            helper.setText(R.id.tv_enumAssessmentTypeStr, StringUtil.setText(item.getEnumAssessmentTypeStr()));

            helper.setText(R.id.tv_AssessmentName, StringUtil.setText(item.getAssessmentName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

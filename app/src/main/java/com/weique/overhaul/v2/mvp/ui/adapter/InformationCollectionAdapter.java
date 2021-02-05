package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationCollectionActivity;

import java.util.List;

import timber.log.Timber;

public class InformationCollectionAdapter extends BaseQuickAdapter<InformationTypeOneSecondBean.ElementTypeListBean, BaseViewHolder> {

    public InformationCollectionAdapter(int layoutResId, @Nullable List<InformationTypeOneSecondBean.ElementTypeListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, InformationTypeOneSecondBean.ElementTypeListBean item) {
        try {
            Timber.e(InformationCollectionActivity.mType+"");
            Timber.e(item.getCount());
            if (StringUtil.isNotNullString(item.getAnotherName())) {
                helper.setText(R.id.name, (item.getAnotherName()));
            } else {
                helper.setText(R.id.name, (item.getName()));
            }
            //巡查走访进来时不显示数量
            if (InformationCollectionActivity.mType <= InformationCollectionActivity.GATHER) {
                helper.setText(R.id.memo, StringUtil.setText(item.getCount()));
            }
            helper.addOnClickListener(R.id.item_view);
            GlideUtil.loadImage(mContext, item.getElementTypeIconURL(), helper.getView(R.id.icon), R.drawable.default_i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

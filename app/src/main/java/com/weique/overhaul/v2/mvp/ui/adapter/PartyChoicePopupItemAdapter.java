package com.weique.overhaul.v2.mvp.ui.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.PartyCenterRecommendedBean;

import java.util.List;

public class PartyChoicePopupItemAdapter extends BaseQuickAdapter<PartyCenterRecommendedBean.TypesBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public PartyChoicePopupItemAdapter(List<PartyCenterRecommendedBean.TypesBean> typesBeans) {
        super(R.layout.choice_item, typesBeans);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, PartyCenterRecommendedBean.TypesBean item) {
        TextView choiceText = helper.getView(R.id.choice_text);
        choiceText.setText((item.getName()));
    }
}

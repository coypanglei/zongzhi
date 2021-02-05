package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameIdCheckedInterface;

/**
 * @param <T>
 */
public class CommonCustomAdapter<T extends NameIdCheckedInterface> extends BaseQuickAdapter<T, BaseViewHolder> {


    public CommonCustomAdapter() {
        super(R.layout.popup_common_recycler);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, T item) {
        try {
            helper.addOnClickListener(R.id.name);
            helper.setText(R.id.name, item.getName());
            helper.setTag(R.id.name, item.getId());
            if (item.getChecked()) {
                helper.setTextColor(R.id.name, ArmsUtils.getColor(mContext, R.color.blue_5a93f7));
            } else {
                helper.setTextColor(R.id.name, ArmsUtils.getColor(mContext, R.color.black_333));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;

import java.util.List;

public class OptionPopupMoreRecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<Boolean> mCheck;
    /**
     * type暂时用不要 为去除
     */
    private String type;

    public OptionPopupMoreRecyclerAdapter(int layoutResId, @Nullable List<String> data, @Nullable List<Boolean> check, String type) {
        super(layoutResId, data);
        this.type = type;
        this.mCheck = check;
    }

    public List<Boolean> getmCheck() {
        return mCheck;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        try {
            int position = helper.getAdapterPosition();
            switch (type) {
                case InformationDynamicFormSelectBean.DropdownList:
                case InformationDynamicFormSelectBean.Option:
                case InformationDynamicFormSelectBean.CheckBox:
                    helper.setText(R.id.item_text, (item));
                    if (mCheck.get(position)) {
                        helper.setTextColor(R.id.item_text, ArmsUtils.getColor(mContext, R.color.blue_4D8FF7));
                        helper.setBackgroundRes(R.id.item_text, R.drawable.shape_b_eaf1fd_c_19);
                    } else {
                        helper.setTextColor(R.id.item_text, ArmsUtils.getColor(mContext, R.color.black_333));
                        helper.setBackgroundRes(R.id.item_text, R.drawable.shape_b_lucency_c_19);
                    }
                   /* if (mCheck.get(position)) {
                        helper.setTextColor(R.id.item_text, ArmsUtils.getColor(mContext, R.color.colorPrimary));
                    } else {
                        helper.setTextColor(R.id.item_text, ArmsUtils.getColor(mContext, R.color.black_333));
                    }*/
                    helper.addOnClickListener(R.id.all_item);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void itemChangedOnlyOne(int pos) {
        for (int i = 0; i < mCheck.size(); i++) {
            if (pos == i) {
                mCheck.set(i, true);
            } else {
                mCheck.set(i, false);
            }
        }
        notifyDataSetChanged();
    }

    public void itemChangedMore(int pos) {
        for (int i = 0; i < mCheck.size(); i++) {
            if (pos == i) {
                mCheck.set(i, !mCheck.get(i));
            }
        }
        notifyDataSetChanged();
    }
}

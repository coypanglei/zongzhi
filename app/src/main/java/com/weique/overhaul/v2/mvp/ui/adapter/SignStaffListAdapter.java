package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.SignStaffListCommonBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class SignStaffListAdapter extends BaseQuickAdapter<SignStaffListCommonBean, BaseViewHolder> {

    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.name)
    TextView name;

    public SignStaffListAdapter() {
        super(R.layout.sign_staff_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, SignStaffListCommonBean item) {
        try {
            View convertView = helper.itemView;
            convertView.setPadding(ArmsUtils.dip2px(mContext, item.getLevel() * 10 + 5),
                    0, ArmsUtils.dip2px(mContext, item.getLevel() * 10 + 5),
                    0);
            ButterKnife.bind(this, convertView);
            if (StringUtil.isNotNullString(item.getName())) {
                name.setText(item.getName());
                tvIntroduction.setText(item.getName().substring(0, 1));
            }
            if (item.getLoginDay() >= 0) {
                helper.setVisible(R.id.arrow_icon, false);
                helper.setText(R.id.number, "本月签到" + item.getLoginDay() + "次\n" + "本月签退" + item.getSignOut() + "次");
            } else {
                helper.setText(R.id.number, "");
                helper.setVisible(R.id.arrow_icon, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

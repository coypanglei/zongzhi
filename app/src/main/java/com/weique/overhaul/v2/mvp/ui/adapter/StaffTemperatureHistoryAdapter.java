package com.weique.overhaul.v2.mvp.ui.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.StaffTemperatureBean;

public class StaffTemperatureHistoryAdapter extends BaseQuickAdapter<StaffTemperatureBean, BaseViewHolder> {

    public StaffTemperatureHistoryAdapter() {
        super(R.layout.activity_staff_temperature_item_see, null);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, StaffTemperatureBean item) {
        try {
            helper.setText(R.id.name, "姓名: " + (item.getEmpName()))
                    .setText(R.id.time, (item.getCreateTime()));
            TextView editText_up = helper.getView(R.id.temperature_up);
            TextView temperature_d = helper.getView(R.id.temperature_d);
            editText_up.setText(String.valueOf(item.getAMTemperature()));
            temperature_d.setText(String.valueOf(item.getPMTemperature()));

            if (item.getAMTemperature() >= StaffTemperatureAdapter.WARNING) {
                helper.setTextColor(R.id.temperature_up, ArmsUtils.getColor(mContext, R.color.red));
            } else {
                helper.setTextColor(R.id.temperature_up, ArmsUtils.getColor(mContext, R.color.green));
            }

            if (item.getPMTemperature() >= StaffTemperatureAdapter.WARNING) {
                helper.setTextColor(R.id.temperature_d, ArmsUtils.getColor(mContext, R.color.red));
            } else {
                helper.setTextColor(R.id.temperature_d, ArmsUtils.getColor(mContext, R.color.green));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.StaffListBean;

public class StaffListAdapter extends BaseQuickAdapter<StaffListBean.ListBean, BaseViewHolder> {
    public StaffListAdapter() {
        super(R.layout.activity_staff_list_item, null);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, StaffListBean.ListBean item) {
        try {
            helper.setText(R.id.name, "姓名：" + (item.getName()) + "(" + (item.getGender()) + ")")
                    .setText(R.id.age, "年龄：" + item.getAge())
                    .setText(R.id.house_type, "住房类型：" + (item.getEnumPropertyType()))
                    .setText(R.id.phone, (item.getTel()))
                    .setText(R.id.go_out_status, "离开住地：" + (item.getEnumIsOutXuzhou()))
                    .addOnClickListener(R.id.phone, R.id.all_item);
            TextView placeDomicile = helper.getView(R.id.place_domicile);
            if (StringUtil.isNotNullString(item.getDomicile())) {
                placeDomicile.setVisibility(View.VISIBLE);
                placeDomicile.setText("户籍地：" + item.getDomicile());
            } else {
                placeDomicile.setVisibility(View.GONE);
            }
            TextView currentDomicile = helper.getView(R.id.current_domicile);
            if (StringUtil.isNotNullString(item.getCurrentAddress())) {
                currentDomicile.setVisibility(View.VISIBLE);
                currentDomicile.setText("现住地：" + item.getCurrentAddress());
            } else {
                currentDomicile.setVisibility(View.GONE);
            }
            TextView reworkTime = helper.getView(R.id.rework_time);
            if (StringUtil.isNotNullString(item.getReturnToWorkTime())) {
                reworkTime.setVisibility(View.VISIBLE);
                reworkTime.setText("复工时间：" + item.getReturnToWorkTime());
            } else {
                reworkTime.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

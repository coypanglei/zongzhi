package com.weique.overhaul.v2.mvp.ui.adapter;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionItemBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class ContradictionListAdapter extends BaseQuickAdapter<ContradictionItemBean.ListBean, BaseViewHolder> {


    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.remind)
    TextView remind;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.status)
    TextView status;

    public ContradictionListAdapter() {
        super(R.layout.contradiction_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, ContradictionItemBean.ListBean item) {
        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);
            num.setText(item.getCode());
            address.setText(item.getAddress());

            if (item.getEnumResolveType() == 0) {
                type.setText("不愿化解");
            } else if (item.getEnumResolveType() == 1) {
                type.setText("自愿化解");
            }else if (item.getEnumResolveType() == 2) {
                type.setText("人民调解");
            }else if (item.getEnumResolveType() == 3) {
                type.setText("行政调解");
            }else if (item.getEnumResolveType() == 4) {
                type.setText("律师调解");
            }else if (item.getEnumResolveType() == 5) {
                type.setText("行政复议");
            }else if (item.getEnumResolveType() == 6) {
                type.setText("行政裁决");
            }else if (item.getEnumResolveType() == 7) {
                type.setText("仲裁");
            }else if (item.getEnumResolveType() == 8) {
                type.setText("公证");
            }else if (item.getEnumResolveType() == 9) {
                type.setText("民主协商");
            }
            
            remind.setText(item.getMemo());
            time.setText(item.getTime());

            if (item.getEnumCAEventOrderSaveStatus() == 0) {
                status.setText("暂存");
                status.setTextColor(mContext.getResources().getColor(R.color.orange));
            } else if (item.getEnumCAEventOrderSaveStatus() == 1) {
                status.setText("上报");
                status.setTextColor(mContext.getResources().getColor(R.color.orange));
            } else if (item.getEnumCAEventOrderSaveStatus() == 3) {
                status.setText("已受理");
                status.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            } else if (item.getEnumCAEventOrderSaveStatus() == 4) {
                status.setText("退回");
                status.setTextColor(mContext.getResources().getColor(R.color.red));
            } else if (item.getEnumCAEventOrderSaveStatus() == 5) {
                status.setText("作废");
                status.setTextColor(mContext.getResources().getColor(R.color.red));
            } else if (item.getEnumCAEventOrderSaveStatus() == 6) {
                status.setText("处理完毕");
                status.setTextColor(mContext.getResources().getColor(R.color.green_4fa74c));
            }else if (item.getEnumCAEventOrderSaveStatus() == 2) {
                status.setText("已指派");
                status.setTextColor(mContext.getResources().getColor(R.color.green_4fa74c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

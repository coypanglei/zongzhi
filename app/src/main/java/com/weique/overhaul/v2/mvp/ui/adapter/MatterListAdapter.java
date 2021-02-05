package com.weique.overhaul.v2.mvp.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.MatterListBean;

/**
 * @author GK
 * @description:
 * @date :2020/10/29 15:05
 */
public class MatterListAdapter extends BaseQuickAdapter<MatterListBean.ListBean, BaseViewHolder> {
    private int flag;

    public MatterListAdapter(int flag) {
        super(R.layout.item_matter_list);
        this.flag = flag;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MatterListBean.ListBean item) {
        try {
            helper.setText(R.id.name, StringUtil.setText(item.getTitle()))
                    .setText(R.id.code_event, StringUtil.setText(item.getCode()))
                    .setText(R.id.sort_v, StringUtil.setText(item.getEnumSourceStr()))
                    .setText(R.id.level, StringUtil.setText(item.getEnumEventLevelStr()))
                    .setText(R.id.time_v, StringUtil.setText(item.getCreateTimeStr()))

            ;
            if (flag == 1) {
                helper.setText(R.id.state_v, StringUtil.setText(item.getEnumEventProceedStatusStr()));
            } else {
                helper.setText(R.id.state_v, StringUtil.setText(item.getEnumOrderStatusStr()));
            }
            ImageView view = helper.getView(R.id.resource_color);
            TextView level = helper.getView(R.id.level);
            switch (item.getEnumEventLevel()) {
                case 0:
                    view.setImageResource(R.drawable.shape_circle_10_b_214bff);
                    level.setTextColor(ArmsUtils.getColor(mContext, R.color.green_00cfab));
                    break;
                case 1:
                    view.setImageResource(R.drawable.shape_circle_10_b_16ceab);
                    level.setTextColor(ArmsUtils.getColor(mContext, R.color.yellow_ffb700));
                    break;
                case 2:
                    view.setImageResource(R.drawable.shape_circle_10_b_ffb600);
                    level.setTextColor(ArmsUtils.getColor(mContext, R.color.red_ff2700));
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

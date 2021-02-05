package com.weique.overhaul.v2.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.AreaDetailsListBean;

import java.util.ArrayList;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class AreaDetailsListAdapter extends BaseQuickAdapter<AreaDetailsListBean, BaseViewHolder> {

    @BindView(R.id.layout_click)
    RelativeLayout layoutClick;
    @BindView(R.id.down_data)
    LinearLayout downData;
    @BindView(R.id.street_name)
    TextView streetName;
    @BindView(R.id.inxicaiji)
    TextView inxicaiji;
    @BindView(R.id.wanggezoufang)
    TextView wanggezoufang;
    @BindView(R.id.wanggexunjian)
    TextView wanggexunjian;
    @BindView(R.id.shijianshangbao)
    TextView shijianshangbao;
    @BindView(R.id.maoduntiaojie)
    TextView maoduntiaojie;
    @BindAnim(R.anim.view_rotate)
    Animation view_rotate;
    @BindAnim(R.anim.view_rotate_reversal)
    Animation view_rotate_reversal;

    @BindView(R.id.arrow_icon_info_type)
    ImageView imageView;

    public AreaDetailsListAdapter() {
        super(R.layout.area_detail_list_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, AreaDetailsListBean item) {
        View convertView = helper.itemView;
        ButterKnife.bind(this, convertView);


        try {
            view_rotate.setInterpolator(new LinearInterpolator());
            view_rotate.setFillAfter(true);

            view_rotate_reversal.setInterpolator(new LinearInterpolator());
            view_rotate_reversal.setFillAfter(true);
            streetName.setText(item.getName());

            if (!TextUtils.isEmpty(item.getCollectionCount())) {
                streetName.setText(item.getName());
                inxicaiji.setText(String.format("信息采集：%s", item.getCollectionCount()));
                wanggezoufang.setText(item.getVisitCount());
                wanggexunjian.setText(item.getInspectionCount());
                shijianshangbao.setText(item.getEventCount());
                maoduntiaojie.setText(item.getMediateCount());
            }

            if (item.isClick()) {
                downData.setVisibility(View.VISIBLE);
                imageView.startAnimation(view_rotate);
            } else {
                downData.setVisibility(View.GONE);
                imageView.startAnimation(view_rotate_reversal);

            }
            helper.addOnClickListener(R.id.layout_click);
            helper.addOnClickListener(R.id.down_data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

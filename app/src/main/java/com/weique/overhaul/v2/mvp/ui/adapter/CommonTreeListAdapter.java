package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.TreeDataInterface;

import java.util.List;


/**
 * 公用树形 adapter
 *
 * @author GreatKing
 */
public class CommonTreeListAdapter<T extends TreeDataInterface> extends BaseQuickAdapter<T, BaseViewHolder> {

    /**
     * 是否所有的item  都可以点击 选择
     */
    private boolean allOk = false;

    public CommonTreeListAdapter(List<T> data) {
        super(R.layout.common_tree_item, data);
    }


    public CommonTreeListAdapter(List<T> data, boolean allOk) {
        super(R.layout.common_tree_item_all_ok, data);
        this.allOk = allOk;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, TreeDataInterface typeBean) {
        try {
            helper.setText(R.id.text, StringUtil.setText(typeBean.getName()));
            LinearLayout allItem = helper.getView(R.id.all_item);
            allItem.setPadding(ArmsUtils.dip2px(mContext, typeBean.getLevel() * 10),
                    0, ArmsUtils.dip2px(mContext, typeBean.getLevel() * 10), 0);
            //所有item 都可以点击
            if (allOk) {
                Button arrowIcon = helper.getView(R.id.arrow_icon);
                Button input = helper.getView(R.id.input);
                if(typeBean.isExpand()){
                    arrowIcon.setText("收起");
                }else{
                    arrowIcon.setText("展开");
                }
                if (typeBean.isLeaf()) {
                    arrowIcon.setVisibility(View.GONE);
                    input.setVisibility(View.VISIBLE);
                    helper.addOnClickListener(R.id.input);
                } else {
                    arrowIcon.setVisibility(View.VISIBLE);
                    input.setVisibility(View.VISIBLE);
                    helper.addOnClickListener(R.id.arrow_icon);
                    helper.addOnClickListener(R.id.input);
                }
            } else {
                ImageView arrowIcon = helper.getView(R.id.arrow_icon);
                Button input = helper.getView(R.id.input);
                if (typeBean.isLeaf()) {
                    arrowIcon.setVisibility(View.GONE);
                    input.setVisibility(View.VISIBLE);
                    helper.addOnClickListener(R.id.input);
                } else {
                    arrowIcon.setVisibility(View.VISIBLE);
                    input.setVisibility(View.GONE);
                    helper.addOnClickListener(R.id.all_item);
                    if (typeBean.isExpand()) {
                        arrowIcon.setImageResource(R.drawable.arrow_icon);
                    } else {
                        arrowIcon.setImageResource(R.drawable.arrow_icon_down);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

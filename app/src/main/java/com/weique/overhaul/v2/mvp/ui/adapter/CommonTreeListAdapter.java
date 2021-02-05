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

    public CommonTreeListAdapter(List<T> data) {
        super(R.layout.common_tree_item, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, TreeDataInterface typeBean) {
        try {
            helper.setText(R.id.text, StringUtil.setText(typeBean.getName()));
            LinearLayout allItem = helper.getView(R.id.all_item);
            allItem.setPadding(ArmsUtils.dip2px(mContext, typeBean.getLevel() * 10),
                    0, ArmsUtils.dip2px(mContext, typeBean.getLevel() * 10), 0);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

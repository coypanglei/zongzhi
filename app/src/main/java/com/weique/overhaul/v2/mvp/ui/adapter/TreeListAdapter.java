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
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationCollectionActivity;

import java.util.List;


/**
 * @author GreatKing
 */
public class TreeListAdapter extends BaseQuickAdapter<InformationTypeOneSecondBean.ElementTypeListBean, BaseViewHolder> {
    //从2开始时因为  上个界面的数据时 1

    //是否是搜索信息界面
    private boolean isSearch;

    public boolean isSearch() {
        return isSearch;
    }

    public void setSearch(boolean search) {
        isSearch = search;
    }

    public TreeListAdapter(List<InformationTypeOneSecondBean.ElementTypeListBean> data) {
        super(R.layout.item_tree_0, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, InformationTypeOneSecondBean.ElementTypeListBean typeBean) {
        try {
            if (StringUtil.isNotNullString(typeBean.getAnotherName())) {
                helper.setText(R.id.text, (typeBean.getAnotherName()));
            } else {
                helper.setText(R.id.text, StringUtil.setText(typeBean.getName()));
            }
            //巡查走访进来时不显示数量
            if (InformationCollectionActivity.mType <= InformationCollectionActivity.GATHER) {
                helper.setText(R.id.count, typeBean.getCount());
            }
            LinearLayout allItem = helper.getView(R.id.all_item);
            if (!isSearch) {
                allItem.setPadding(ArmsUtils.dip2px(mContext, typeBean.getLevel() * 10),
                        0, ArmsUtils.dip2px(mContext, typeBean.getLevel() * 10), 0);
            }
            ImageView arrowIcon = helper.getView(R.id.arrow_icon);
            ImageView typeIcon = helper.getView(R.id.type_icon);
            GlideUtil.loadImage(mContext, typeBean.getElementTypeIconURL(), typeIcon, R.drawable.default_i);
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

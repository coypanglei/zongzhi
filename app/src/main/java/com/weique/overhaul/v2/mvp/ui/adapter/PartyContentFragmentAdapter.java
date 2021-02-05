package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class PartyContentFragmentAdapter extends BaseQuickAdapter<PartyContentItemBean.ListBean, BaseViewHolder> {

    public PartyContentFragmentAdapter() {
        super(R.layout.item_party_second, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, PartyContentItemBean.ListBean item) {
        try {
            helper.addOnClickListener(R.id.front_layout);
            ImageView pic = helper.getView(R.id.pic);
            if (StringUtil.isNotNullString(item.getCoverPicturePath())) {
                pic.setVisibility(View.VISIBLE);
                GlideUtil.loadImage(mContext, item.getCoverPicturePath(), pic);
            } else {
                pic.setVisibility(View.GONE);
            }
            TextView itemTitle = helper.getView(R.id.item_title);
            itemTitle.setText(StringUtil.setText(item.getTitle()));
            helper.setText(R.id.author, StringUtil.setText(item.getAuthor()));
            helper.setText(R.id.time, StringUtil.setText(item.getReleaseTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class PartyCollectsItemAdapter extends BaseQuickAdapter<PartyContentItemBean.ListBean, BaseViewHolder> {

    public PartyCollectsItemAdapter() {
        super(R.layout.item_party_collect, new ArrayList<>());
    }

    /**
     * 新打开的位置
     */
    private int newOpenedPos = -1;
    /**
     * 要关闭地位置
     */
    private int oldOpenedPos = -1;

    /**
     * @param closePos 关闭的position
     */
    public void notifiItemClose(int closePos) {
        notifyItemChanged(closePos);
        oldOpenedPos = -1;
    }

    public void deleteItem(int deletePos) {
        newOpenedPos = -1;
        oldOpenedPos = -1;
        remove(deletePos);
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
            int adapterPosition = helper.getAdapterPosition();
            helper.addOnClickListener(R.id.front_layout);
            helper.addOnClickListener(R.id.delete_layout);
            ImageView pic = helper.getView(R.id.pic);
            if (StringUtil.isNotNullString(item.getCoverPicturePath())) {
                pic.setVisibility(View.VISIBLE);
                GlideUtil.loadImage(mContext, item.getCoverPicturePath(), pic);
            } else {
                pic.setVisibility(View.GONE);
            }
            TextView itemTitle = helper.getView(R.id.item_title);
            itemTitle.setText((item.getTitle()));
            helper.setText(R.id.author, (item.getAuthor()));
            helper.setText(R.id.time, (item.getReleaseTime()));
            SwipeRevealLayout swipeRevealLayout = helper.getView(R.id.swipe_reveal_layout);
            if (adapterPosition != newOpenedPos && swipeRevealLayout.isOpened()) {
                swipeRevealLayout.close(true);
            }
            swipeRevealLayout.setSwipeListener(new SwipeRevealLayout.SimpleSwipeListener() {
                @Override
                public void onOpened(SwipeRevealLayout view) {
                    super.onOpened(view);
                    oldOpenedPos = newOpenedPos;
                    newOpenedPos = adapterPosition;
                    notifiItemClose(oldOpenedPos);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

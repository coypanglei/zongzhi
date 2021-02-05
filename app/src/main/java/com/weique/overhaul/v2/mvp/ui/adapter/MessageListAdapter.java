package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.MessageListBean;

import java.util.ArrayList;

/**
 * @author GreatKing
 */
public class MessageListAdapter extends BaseQuickAdapter<MessageListBean.ListBean, BaseViewHolder> {
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
     *
     */
    public MessageListAdapter() {
        super(R.layout.activity_message_list_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageListBean.ListBean item) {
        try {
            int adapterPosition = helper.getAdapterPosition();
            helper.setText(R.id.title, (item.getMessage()))
                    .setText(R.id.content, (item.getMemo()))
                    .setBackgroundRes(R.id.is_reading, R.drawable.shape_circle_10_b_ffb600)
                    .setVisible(R.id.is_reading, !item.isIsRead())
                    .setText(R.id.time, (item.getCreateTime()));
//            SwipeRevealLayout swipeRevealLayout = helper.getView(R.id.swipe_reveal_layout);
//            if (adapterPosition != newOpenedPos && swipeRevealLayout.isOpened()) {
//                swipeRevealLayout.close(true);
//            }
//            swipeRevealLayout.setSwipeListener(new SwipeRevealLayout.SimpleSwipeListener() {
//                @Override
//                public void onOpened(SwipeRevealLayout view) {
//                    super.onOpened(view);
//                    oldOpenedPos = newOpenedPos;
//                    newOpenedPos = adapterPosition;
//                    notifiItemClose(oldOpenedPos);
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

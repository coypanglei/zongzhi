package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class ChatSelectListAdapter extends BaseQuickAdapter<AddressBookListBean.ListBean, BaseViewHolder> {


    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.iv_select)
    ImageView ivSelect;
    @BindView(R.id.add_image)
    ImageView addImage;
    @BindView(R.id.layout)
    LinearLayout layout;

    public ChatSelectListAdapter() {
        super(R.layout.chat_select_list_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddressBookListBean.ListBean item) {
        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);

            if (item.isSelect()) {
                ivSelect.setImageResource(R.drawable.icon_duihao);
            } else {
                ivSelect.setImageResource(R.drawable.icon_unselect);
            }

            GlideUtil.loadCircleImage(mContext, (item.getHeadUrl()), image);
            name.setText((item.getName()));
            helper.addOnClickListener(R.id.add_image);
            helper.addOnClickListener(R.id.layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

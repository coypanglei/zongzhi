package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class AddressBookListAdapter extends BaseQuickAdapter<AddressBookListBean.ListBean, BaseViewHolder> {


    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    private String source;

    public AddressBookListAdapter() {
        super(R.layout.address_book_list_item, new ArrayList<>());
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
            GlideUtil.loadCircleImage(mContext, (item.getHeadUrl()),image);
            name.setText((item.getName()));
            if (RouterHub.APP_CHATSELECTACTIVITY.equals(source)) {
                helper.setGone(R.id.phone, false).setGone(R.id.video, true);
            } else {
                helper.setGone(R.id.phone, true).setGone(R.id.video, false);
            }
            helper.addOnClickListener(R.id.phone);
            helper.addOnClickListener(R.id.video);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSource(String source) {
        this.source = source;
    }
}

package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookItemBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class AddressBookAdapter extends BaseQuickAdapter<AddressBookItemBean.ListBean, BaseViewHolder> {

    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.enter)
    TextView enter;

    public AddressBookAdapter() {
        super(R.layout.address_book_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddressBookItemBean.ListBean item) {
        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);
            if (StringUtil.isNotNullString(item.getName())) {
                name.setText(item.getName());
                tvIntroduction.setText(item.getName().substring(0, 1));
            }
            if ((item.getEnumCommunityLevel() + "").equals("0")) {
                enter.setText("进入");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

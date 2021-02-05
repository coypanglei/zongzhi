package com.weique.overhaul.v2.mvp.ui.adapter;


import android.graphics.drawable.Drawable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.AddressSelectBean;

import butterknife.BindDrawable;


/**
 * 作者：PangLei on 2019/3/21 0021 11:03
 * <p>
 * 邮箱：xjs250@163.com
 *
 * @author Administrator
 */
public class UnlimitedNestingAdapter extends BaseQuickAdapter<AddressSelectBean, BaseViewHolder> {

    /**
     * 跳转所需的数值
     */
    private String title;
    @BindDrawable(R.drawable.arrow_icon)
    Drawable drawable;

    public UnlimitedNestingAdapter() {
        super(R.layout.item_guojihy);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AddressSelectBean item) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

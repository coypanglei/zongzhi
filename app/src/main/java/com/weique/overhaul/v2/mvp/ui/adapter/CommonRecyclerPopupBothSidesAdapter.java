package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;

import java.util.List;

/**
 * 两个值两边显示 的item
 *
 * @param <T>
 * @author Administrator
 */
public class CommonRecyclerPopupBothSidesAdapter<T extends NameAndIdInterface> extends BaseQuickAdapter<T, BaseViewHolder> {


    public CommonRecyclerPopupBothSidesAdapter() {
        super(R.layout.popup_common_two_text_recycler);
    }

    @Override
    public void setNewData(@Nullable List<T> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NameAndIdInterface item) {
        try {
            helper.addOnClickListener(R.id.item);
            helper.setText(R.id.name, item.getName());
            helper.setText(R.id.name1, item.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

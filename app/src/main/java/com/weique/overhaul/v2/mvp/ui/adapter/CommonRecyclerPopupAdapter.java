package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;

import java.util.ArrayList;
import java.util.List;

public class CommonRecyclerPopupAdapter<T extends NameAndIdInterface> extends BaseQuickAdapter<T, BaseViewHolder> {

    private List<Boolean> list;

    public CommonRecyclerPopupAdapter() {
        super(R.layout.popup_common_recycler);
    }

    @Override
    public void setNewData(@Nullable List<T> data) {
        super.setNewData(data);
        if (list == null) {
            list = new ArrayList<>();
            for (T t : getData()) {
                list.add(false);
            }
        }
    }

    /**
     * 设置 选中位置
     *
     * @param pos pos
     */
    public void setCheckPos(int pos) {
        if (list.get(pos)) {
            list.set(pos, false);
        } else {
            list.set(pos, true);
        }
        notifyItemChanged(pos);
    }

    public List<T> getListByPosList() {
        List<T> nameAndIdBeans = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)) {
                nameAndIdBeans.add(getData().get(i));
            }
        }
        return nameAndIdBeans;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NameAndIdInterface item) {
        try {
            int adapterPosition = helper.getAdapterPosition();
            helper.addOnClickListener(R.id.name);
            helper.setText(R.id.name, item.getName());
            helper.setTag(R.id.name, item.getId());
            if (list != null && list.size() > 0) {
                if (list.get(adapterPosition)) {
                    helper.setTextColor(R.id.name, ArmsUtils.getColor(mContext, R.color.blue_5a93f7));
                } else {
                    helper.setTextColor(R.id.name, ArmsUtils.getColor(mContext, R.color.black_333));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

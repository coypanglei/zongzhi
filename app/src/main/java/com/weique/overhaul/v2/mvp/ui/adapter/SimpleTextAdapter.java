package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.BaseSearchPopupBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

public class SimpleTextAdapter extends BaseQuickAdapter<BaseSearchPopupBean, BaseViewHolder> {

    public SimpleTextAdapter() {
        super(R.layout.adapter_simper_item, null);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BaseSearchPopupBean item) {
        try {
            String name = "";
            if (item instanceof StandardAddressStairBean.StandardAddressBean) {
                StandardAddressStairBean.StandardAddressBean standardAddressBean =
                        (StandardAddressStairBean.StandardAddressBean) item;
                name = standardAddressBean.getFullPath();
            } else {
                name = item.getName();
            }
            helper.setText(R.id.text, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

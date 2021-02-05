package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InspectionListItemBean;

import java.util.ArrayList;

/**
 * @author GK
 */
public class InspectionListAdapter extends BaseQuickAdapter<InspectionListItemBean.InspectionRecordBean, BaseViewHolder> {

    public InspectionListAdapter() {
        super(R.layout.resource_search_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, InspectionListItemBean.InspectionRecordBean item) {
        try {
            helper.setText(R.id.name, StringUtil.setText(item.getResourceName()));
            helper.setText(R.id.address, StringUtil.setText(item.getDepartmentFullPath()));
            helper.setText(R.id.time, StringUtil.setText(item.getCreateTime()));
            helper.setVisible(R.id.count, true);
            helper.setText(R.id.count, (item.getCount()) + "次");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

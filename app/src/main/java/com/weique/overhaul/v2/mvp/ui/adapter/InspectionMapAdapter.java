package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.InspectionLatLng;

import java.util.ArrayList;

/**
 * @author GK
 */
public class InspectionMapAdapter extends BaseQuickAdapter<InspectionLatLng.ListBean, BaseViewHolder> {

    public InspectionMapAdapter() {
        super(R.layout.inspectionmap_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, InspectionLatLng.ListBean item) {

        try {
            helper.setText(R.id.time, (item.getTime()));
            helper.setText(R.id.memo, (item.getMemo()));
            helper.addOnClickListener(R.id.edit_btn);
            if (!item.getIRImageUrlsInJson().equals("[]")) {
                helper.setVisible(R.id.image_pic, true);
                helper.setVisible(R.id.view, true);
            } else {
                helper.setVisible(R.id.image_pic, false);
                helper.setVisible(R.id.view, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

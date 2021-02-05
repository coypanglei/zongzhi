package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.CaseReportedBean;

import java.util.ArrayList;

/**
 * @author GreatKing
 */
public class CasesReportedAdapter extends BaseQuickAdapter<CaseReportedBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public CasesReportedAdapter() {
        super(R.layout.activity_events_reported_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, CaseReportedBean item) {
        try {
            helper.addOnClickListener(R.id.item_view);
            helper.setText(R.id.event_name, item.getTitle());
            helper.setText(R.id.event_sort_name, item.getComprehensiveLawEnforcementCaseTypeName());
            helper.setText(R.id.record_time, (item.getUpdateTimeStr()));
            /*
             案件状态
             */
            helper.setText(R.id.status, (item.getEnumComprehensiveLawEnforcementCaseStatusStr()));
            if (StringUtil.isNotNullString(item.getEnumCaseSourceStr())) {
                helper.setVisible(R.id.source_of_the_incident, true);
                helper.setText(R.id.source_of_the_incident, item.getEnumCaseSourceStr());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

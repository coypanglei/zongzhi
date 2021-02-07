package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.ResourceAuditBean;

import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2020/12/18 14:03
 */
public class ResourceAuditListAdapter extends BaseQuickAdapter<ResourceAuditBean.ListBean, BaseViewHolder> {
    public ResourceAuditListAdapter(@Nullable List<ResourceAuditBean.ListBean> data) {
        super(R.layout.item_resource_audit_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ResourceAuditBean.ListBean item) {
        try {
            helper.setTextColor(R.id.state, ArmsUtils.getColor(mContext, item.getStatusColor()));
            helper.setText(R.id.name, StringUtil.setText(item.getName()))
                    .setText(R.id.state, StringUtil.setText(item.getStatus()))
                    .setText(R.id.type, StringUtil.setText(item.getFullPath()))
                    .setText(R.id.time, StringUtil.setText(item.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

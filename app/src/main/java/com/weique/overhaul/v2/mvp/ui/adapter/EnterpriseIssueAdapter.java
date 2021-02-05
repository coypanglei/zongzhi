package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueListBean;

public class EnterpriseIssueAdapter extends BaseQuickAdapter<EnterpriseIssueListBean.ListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public EnterpriseIssueAdapter() {
        super(R.layout.enterprise_issue_item, null);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, EnterpriseIssueListBean.ListBean item) {
        try {
            helper.setText(R.id.title_name, (item.get问题类型()))
                    .setText(R.id.dispose_status, (item.get问题回复状态()))
                    .setText(R.id.time, (item.get创建时间()))
                    .setText(R.id.synopsis, (item.get问题内容()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

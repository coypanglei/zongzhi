package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.FileBean;
import com.weique.overhaul.v2.mvp.model.entity.event.ProgressChangeBean;

public class MessageDetailAdapter extends BaseQuickAdapter<FileBean, BaseViewHolder> {

    public MessageDetailAdapter() {
        super(R.layout.message_detail_item, null);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FileBean item) {
        try {
            String name = "";
            if (StringUtil.isNotNullString(item.getUrl())) {
                name = item.getUrl().substring(item.getUrl().lastIndexOf("/") + 1, item.getUrl().length());
            }
            helper.setText(R.id.name, name);
            ProgressBar progressBar = helper.getView(R.id.download_progress);
            if (item.getProgress() > 0) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(item.getProgress());
            } else {
                progressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpdateProgress(ProgressChangeBean changeBean) {
        try {
            FileBean item = this.getItem(changeBean.getPos());
            item.setProgress(changeBean.getProgress());
            notifyItemChanged(changeBean.getPos());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

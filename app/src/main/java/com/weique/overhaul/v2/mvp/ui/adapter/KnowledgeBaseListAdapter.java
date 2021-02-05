package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBaseBean;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author  GK
 */
public class KnowledgeBaseListAdapter extends BaseQuickAdapter<KnowledgeBaseBean.ListBean, BaseViewHolder> {

    public KnowledgeBaseListAdapter() {
        super(R.layout.knowledge_list_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, KnowledgeBaseBean.ListBean item) {

        try {
            helper.setText(R.id.title, (item.getTitle()));
            helper.setText(R.id.type,(item.getTypeName()));

            String[] storeStr = item.getLabels().toArray(new String[item.getLabels().size()]);// list转成字符数组
            helper.setText(R.id.label, Arrays.toString(storeStr));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

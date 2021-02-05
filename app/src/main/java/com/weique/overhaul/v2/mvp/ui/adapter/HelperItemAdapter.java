package com.weique.overhaul.v2.mvp.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.HelperListItemBean;

/**
 * @author GK
 * @description:
 * @date :2020/11/10 17:59
 */
public class HelperItemAdapter extends BaseQuickAdapter<HelperListItemBean, BaseViewHolder> {
    public HelperItemAdapter() {
        super(R.layout.item_layout_helper);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HelperListItemBean item) {
        helper.setText(R.id.name, StringUtil.setText(item.getName()));
        EditText editText = helper.getView(R.id.issue);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setIssue(s.toString());
            }
        });
    }
}

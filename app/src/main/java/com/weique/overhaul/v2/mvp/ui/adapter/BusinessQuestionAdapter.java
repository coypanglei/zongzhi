package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.BusinessQuestion;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class BusinessQuestionAdapter extends BaseQuickAdapter<BusinessQuestion.ListBean, BaseViewHolder> {


    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.isAnswer)
    TextView isAnswer;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.synopsis)
    TextView synopsis;

    public BusinessQuestionAdapter() {
        super(R.layout.business_qustion_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, BusinessQuestion.ListBean item) {

        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);
            businessName.setText((item.get企业名称()));
            titleName.setText((item.get问题类型()));
            time.setText((item.get企业提交问题时间()));
            synopsis.setText((item.get问题内容()));
            isAnswer.setText((item.get类型字符串()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

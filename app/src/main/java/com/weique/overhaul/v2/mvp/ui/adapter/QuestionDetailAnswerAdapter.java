package com.weique.overhaul.v2.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.QuestionDetailAnswerItemBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class QuestionDetailAnswerAdapter extends BaseQuickAdapter<QuestionDetailAnswerItemBean.AnswersInJsonBean, BaseViewHolder> {


    @BindView(R.id.option)
    TextView option;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.answer_layout)
    LinearLayout answerLayout;

    private String mAnswer;

    public QuestionDetailAnswerAdapter(String answer) {
        super(R.layout.item_danxuan_answer, new ArrayList<QuestionDetailAnswerItemBean.AnswersInJsonBean>());
        this.mAnswer = answer;
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, QuestionDetailAnswerItemBean.AnswersInJsonBean item) {
        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);
            content.setText(item.getAnswer());
            if (helper.getAdapterPosition() == 0) {
                option.setText("A");
            } else if (helper.getAdapterPosition() == 1) {
                option.setText("B");
            } else if (helper.getAdapterPosition() == 2) {
                option.setText("C");
            } else if (helper.getAdapterPosition() == 3) {
                option.setText("D");
            }
            helper.addOnClickListener(R.id.answer_layout);
            if (item.isClick()) {
                if (item.isAnswer()) {
                    if (item.isResult()) {
                        option.setTextColor(mContext.getResources().getColor(R.color.green_4fa74c));
                        content.setTextColor(mContext.getResources().getColor(R.color.green_4fa74c));
                        answerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.shape_b_ebf6ec_c_2));

                    } else {
                        option.setTextColor(mContext.getResources().getColor(R.color.red_e25352));
                        content.setTextColor(mContext.getResources().getColor(R.color.red_e25352));
                        answerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.shape_b_e2a9a5_c_2));
                    }
                } else {
                    option.setTextColor(mContext.getResources().getColor(R.color.yellow_e2b65));
                    content.setTextColor(mContext.getResources().getColor(R.color.yellow_e2b65));
                    answerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.shape_b_e2b265_c2));
                }
            } else {
                answerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.shape_b_e9e9e9_c_2));
                option.setTextColor(mContext.getResources().getColor(R.color.black));
                content.setTextColor(mContext.getResources().getColor(R.color.black));
            }
            if (!TextUtils.isEmpty(mAnswer)) {
                if (item.getAnswer().equals(mAnswer)) {
                    if (item.isResult()) {
                        option.setTextColor(mContext.getResources().getColor(R.color.green_4fa74c));
                        content.setTextColor(mContext.getResources().getColor(R.color.green_4fa74c));
                        answerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.shape_b_ebf6ec_c_2));
                    } else {
                        option.setTextColor(mContext.getResources().getColor(R.color.red_e25352));
                        content.setTextColor(mContext.getResources().getColor(R.color.red_e25352));
                        answerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.shape_b_e2a9a5_c_2));
                    }
                } else {
                    answerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.shape_b_e9e9e9_c_2));
                    option.setTextColor(mContext.getResources().getColor(R.color.black));
                    content.setTextColor(mContext.getResources().getColor(R.color.black));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

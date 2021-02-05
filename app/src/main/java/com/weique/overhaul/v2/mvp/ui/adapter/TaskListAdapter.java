package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.TaskListBean;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GreatKing
 */
public class TaskListAdapter extends BaseQuickAdapter<TaskListBean.ListBean, BaseViewHolder> {
    private int[] drawables = {R.drawable.information_icon,
            R.drawable.interview,
            R.drawable.patrol_icon,
            R.drawable.my_icon_kaoqin,
            R.drawable.my_icon_event,};
    /**
     * 未完成
     */
    public static final int UN_COMPLETE = 0;
    /**
     * 已完成
     */
    public static final int COMPLETE = 1;
    /**
     * 过期
     */
    public static final int CAUTION = 2;

    public TaskListAdapter() {
        super(R.layout.adapter_task_list_item, new ArrayList<>());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TaskListBean.ListBean item) {
        try {
            helper.setText(R.id.name, StringUtil.setText(item.getName()))
                    .setText(R.id.period, StringUtil.setText(item.getEnumMissitonDateType()))
                    .setText(R.id.ratio, item.getCompleteNum() + "/" + item.getPlanNum())
                    .setText(R.id.time_scope, StringUtil.setText(item.getStartTime())
                            + "至" + StringUtil.setText(item.getDeadlineTime()));
            TextView target = helper.getView(R.id.target_);
            List<TaskListBean.ListBean.MissionConditionsBean> missionConditions = item.getMissionConditions();
            StringBuffer tagerString = new StringBuffer();
            for (TaskListBean.ListBean.MissionConditionsBean missionConditionsBean : missionConditions) {
                tagerString.append("(" + missionConditionsBean.getName() + ")");
            }
            if (StringUtil.isNotNullString(tagerString.toString())) {
                target.setVisibility(View.VISIBLE);
                target.setText("目标 : " + (tagerString.toString()));
            } else {
                target.setVisibility(View.GONE);
            }
            ProgressBar progressBar = helper.getView(R.id.pb);
            TextView caution = helper.getView(R.id.caution);
            //0 1 2 注解在类中
            if (item.getEnumMissionStatus() == UN_COMPLETE) {
                helper.setText(R.id.go_to_complete, ArmsUtils.getString(mContext, R.string.go_to_complete))
                        .setBackgroundRes(R.id.go_to_complete, R.drawable.shape_b_3982f6_c_19);
                progressBar.setProgressDrawable(ArmsUtils.getDrawablebyResource(mContext, R.drawable.progressbar_style_red));
            } else if (item.getEnumMissionStatus() == COMPLETE) {
                helper.setText(R.id.go_to_complete, ArmsUtils.getString(mContext, R.string.is_finish))
                        .setBackgroundRes(R.id.go_to_complete, R.drawable.shape_b_b0b2b1_c_19);
                progressBar.setProgressDrawable(ArmsUtils.getDrawablebyResource(mContext, R.drawable.progressbar_style_green));
            } else if (item.getEnumMissionStatus() == CAUTION) {
                if (item.getCompleteNum() < item.getPlanNum()) {
                    caution.setVisibility(View.GONE);
                    helper.setText(R.id.go_to_complete, ArmsUtils.getString(mContext, R.string.go_to_complete))
                            .setBackgroundRes(R.id.go_to_complete, R.drawable.shape_b_3982f6_c_19);
                    progressBar.setProgressDrawable(ArmsUtils.getDrawablebyResource(mContext, R.drawable.progressbar_style_red));
                } else {
                    caution.setVisibility(View.VISIBLE);
                }
            }
            progressBar.setMax(item.getPlanNum());
            progressBar.setProgress(item.getCompleteNum());
            helper.setImageResource(R.id.icon_task, drawables[item.getEnumMissionType()]);
            TextView memo = helper.getView(R.id.memo);
            if (StringUtil.isNotNullString(item.getMemo())) {
                memo.setVisibility(View.VISIBLE);
                memo.setText(StringUtil.setText(item.getMemo()));
                memo.setOnClickListener(v -> {
                    try {
                        new CommonDialog.Builder(mContext).setTitle("备注")
                                .setContent(item.getMemo())
                                .setPositiveButton("关闭", (v1, commonDialog) -> {
                                })
                                .setNegativeBtnShow(false).create().show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                memo.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

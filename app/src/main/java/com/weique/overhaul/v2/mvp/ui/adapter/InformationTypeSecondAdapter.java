package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.TimeUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationCollectionActivity;

import java.util.ArrayList;

/**
 * @author GK
 */
public class InformationTypeSecondAdapter extends BaseQuickAdapter<InformationTypeOneSecondBean.ElementListBean, BaseViewHolder> {

    private int mType = 0;

    public InformationTypeSecondAdapter() {
        super(R.layout.information_type_second_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, InformationTypeOneSecondBean.ElementListBean item) {
        try {
            helper.setText(R.id.name, StringUtil.setText(item.getName()));
            helper.setText(R.id.tv_type, getTypeStr(item.getRecordTime()));

            helper.setText(R.id.memo, StringUtil.setText(item.getFullPath()));
            helper.addOnClickListener(R.id.input);
            if (InformationCollectionActivity.mType == InformationCollectionActivity.GATHER) {
                helper.setText(R.id.input, "查看");
            } else {
                helper.setText(R.id.input, "选择");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据类型转化字符串
     *
     * @return
     */
    private String getTypeStr(String recordTime) {
        String str = "";
        switch (mType) {
            // 走访
            case 1:
                if (ObjectUtils.isNotEmpty(recordTime)) {
                    str = "最近走访: " + TimeUtil.changedFormat(recordTime, Constant.YMDHMS, Constant.YMD_C);
                } else {
                    str = "无走访记录";
                }
                break;
            //巡检
            case 2:
                if (ObjectUtils.isNotEmpty(recordTime)) {
                    str = "最近巡检: " + TimeUtil.changedFormat(recordTime, Constant.YMDHMS, Constant.YMD_C);
                } else {
                    str = "无巡检记录";
                }
                break;
            default:
                return str;
        }
        return str;

    }

    public void setType(int type) {
        this.mType = type;
    }
}

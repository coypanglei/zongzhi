package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.PatrolsBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class PatrolsListAdapter extends BaseQuickAdapter<PatrolsBean.ListBean, BaseViewHolder> {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.type_key)
    TextView typeKey;
    @BindView(R.id.type_name)
    TextView typeName;
    @BindView(R.id.intro)
    TextView intro;
    @BindView(R.id.item_view)
    LinearLayout itemView;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;

    public PatrolsListAdapter() {
        super(R.layout.partrols_item, new ArrayList<>());
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, PatrolsBean.ListBean item) {
        try {
            View convertView = helper.itemView;
            ButterKnife.bind(this, convertView);
            title.setText((item.getName()));
            if (item.getEnumElementProcType() == 1) {
                typeName.setText(mContext.getText(R.string.foot_line));
            } else if (item.getEnumElementProcType() == 2) {
                typeName.setText(mContext.getText(R.string.work_line));
            }
            intro.setText((item.getMemo()));
            startTime.setText((item.getExecutTime()));
            endTime.setText((item.getDeadlineTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

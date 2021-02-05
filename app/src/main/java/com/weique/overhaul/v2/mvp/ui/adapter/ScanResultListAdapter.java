package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.ScanResultBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationDynamicFormSelectActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GK
 */
public class ScanResultListAdapter extends BaseQuickAdapter<ScanResultBean.ListBean, BaseViewHolder> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.type_name)
    TextView typeName;

    public ScanResultListAdapter() {
//        super(R.layout.san_result_item, new ArrayList<>());
        super(R.layout.san_result_item_one, new ArrayList<>());
    }


    private boolean isHavePermission = true;


    public boolean isHavePermission() {
        return isHavePermission;
    }

    public void setHavePermission(boolean havePermission) {
        isHavePermission = havePermission;
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, ScanResultBean.ListBean item) {

        View convertView = helper.itemView;
        ButterKnife.bind(this, convertView);

        try {
            typeName.setText(StringUtil.setText(item.getElementTypeName()));

            ScanResultListTwoAdapter scanResultListTwoAdapter = new ScanResultListTwoAdapter();
            scanResultListTwoAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mContext));
            recyclerView.setAdapter(scanResultListTwoAdapter);
            scanResultListTwoAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            scanResultListTwoAdapter.setNewData(item.getElements());
            if (isHavePermission) {
                recyclerView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                        try {
                            if (AppUtils.isFastClick()) {
                                return;
                            }
                            ARouter.getInstance().build(RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
                                    .withString(ARouerConstant.ID, item.getElements().get(position).getId())
                                    .withString(InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON, item.getElements().get(position).getDataStructureInJson())
                                    .withString(InformationDynamicFormSelectActivity.TYPE_ID, item.getElements().get(position).getElementTypeId())
                                    .navigation();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.LawDetailBean;

import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2020/8/15 15:25
 */
public class LawDetailProcessesAdapter extends BaseQuickAdapter<LawDetailBean.ProcessesBean, BaseViewHolder> {
    public LawDetailProcessesAdapter() {
        super(R.layout.item_accept_record);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LawDetailBean.ProcessesBean item) {
        try {
            helper.setText(R.id.record_content, StringUtil.setText(item.getMemo()))
                    .setText(R.id.transaction_cases, "办理人: " + StringUtil.setText(item.getCreateSignature()))
                    .setText(R.id.time, StringUtil.setText(item.getCreateTimeStr()))
                    .setText(R.id.next_link_handler, "下一环节办理人: " + StringUtil.setText(item.getNextStepHandlerName()))
                    .setText(R.id.next_transaction_link, "下一环节: " + StringUtil.setText(item.getNextStepStatusStr()))
                    .setText(R.id.transaction_link, "办理环节: " + StringUtil.setText(item.getCurrentStepStatusStr()));


            RecyclerView recordList = helper.getView(R.id.file_list);
            if (StringUtil.isNotNullString(item.getEnclosure())) {
                List list = new Gson().fromJson(item.getEnclosure(), List.class);
                if (list != null && list.size() > 0) {
                    helper.setGone(R.id.attachment_layout, true);
                    SimplePhotoAdapter simplePhotoAdapter = new SimplePhotoAdapter(list);
                    ArmsUtils.configRecyclerView(recordList, new GridLayoutManager(mContext, 5));
                    recordList.setAdapter(simplePhotoAdapter);
                    simplePhotoAdapter.setOnItemClickListener((adapter, view, position) -> {
                        try {
                            if (AppUtils.isFastClick()) {
                                return;
                            }
                            String i = (String) adapter.getItem(position);
//                            if (i.endsWith(Constant.PNG)
//                                    || i.endsWith(Constant.JPG)
//                                    || i.endsWith(Constant.BMP)
//                                    || i.endsWith(Constant.JPEG)) {
//                                ARouter.getInstance()
//                                        .build(RouterHub.APP_PICTURELOOKACTIVITY)
//                                        .withString(PictureLookActivity.URL_, i)
//                                        .navigation();
//                            } else {
                            AppUtils.openBrowser(mContext, GlideUtil.handleUrl(mContext, i));
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }else{
                    helper.setGone(R.id.attachment_layout, false);
                }
            } else {
                helper.setGone(R.id.attachment_layout, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.EventProceedRecordBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.DialogAddPhotoAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;



import static com.lzy.imagepicker.util.Utils.dp2px;
import static com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean.IS_VIS_DELETE;

/** 流程弹窗展示
 * @author 43460
 */
public class OrderHandleDetailDialog extends Dialog {

    Gson gson;
    private Context mContext;
    private TextView content;
    private TextView name;
    private TextView departName;
    private TextView time;
    private TextView status;
    private Button confirm;
    private LinearLayout synergyLayout;
    private LinearLayout nameLayout;
    private TextView yesNo;
    private EventProceedRecordBean bean;

    public OrderHandleDetailDialog(@NonNull Context context, EventProceedRecordBean bean) {
        super(context, R.style.dialog_style);
        this.mContext = context;
        this.bean = bean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_order_handle_detail, null);
        setContentView(view);
        //alertDialog是否可以点击外围消失
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        initView(view);
    }

    private void initView(View view) {
        try {
            TextView content = view.findViewById(R.id.content);
            if (StringUtil.isNullString(bean.getMemo())) {
                content.setText(ArmsUtils.getString(mContext, R.string.no_feedback));
            } else {
                content.setText(bean.getMemo());
            }
            name = view.findViewById(R.id.name);
            name.setText(StringUtil.setText(bean.getName()));
            nameLayout = view.findViewById(R.id._name_layout);
            departName = view.findViewById(R.id.depart_name);
            gson = new Gson();
            String[] strings = gson.fromJson(bean.getFileUrls(), String[].class);
            List<InformationItemPictureBean> commentList = new ArrayList<>();
            RecyclerView imageRecyclerView = view.findViewById(R.id.rv_images);
            imageRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(AppManager.getAppManager().getmApplication())
                    .colorResId(R.color.white)
                    .sizeResId(R.dimen.dp_5)
                    .build());
            DialogAddPhotoAdapter informationAddPhotoAdapter = new DialogAddPhotoAdapter(commentList);
            ArmsUtils.configRecyclerView(imageRecyclerView, new GridLayoutManager(AppManager.getAppManager().getmApplication(), 4, RecyclerView.VERTICAL, false));
            imageRecyclerView.setAdapter(informationAddPhotoAdapter);
            informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }

                    InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                    assert bean != null;
                    if (StringUtil.isNotNullString(bean.getImageUrl()) && view1.getId() == R.id.image_) {

                        ARouter.getInstance()
                                .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                .withString(PictureLookActivity.URL_, bean.getImageUrl())
                                .navigation();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (!ArmsUtils.isEmpty(strings)) {

                for (String str : strings) {
                    /*
                     * 评论图片集合
                     */
                    commentList.add(new InformationItemPictureBean(str, IS_VIS_DELETE));
                }
                informationAddPhotoAdapter.setNewData(commentList);
                if (commentList.size() > 0) {
                    imageRecyclerView.setVisibility(View.VISIBLE);
                }
//                    //高度最多显示三个item的高度，如果多于3个条目，让其可滑动显示。如果少于三个条目，则自适应高度

                if (commentList.size() > 4) {
                    imageRecyclerView.setMinimumHeight(dp2px(AppManager.getAppManager().getmApplication(),150));

                } else {
                    imageRecyclerView.setMinimumHeight(dp2px(AppManager.getAppManager().getmApplication(),75));
                }

            }


            TextView name = view.findViewById(R.id.name);
            name.setText(bean.getName());
            TextView departName = view.findViewById(R.id.depart_name);
            if (StringUtil.isNotNullString(bean.getDepartName())) {
                departName.setVisibility(View.VISIBLE);
                departName.setText(bean.getDepartName());
            } else {
                departName.setVisibility(View.GONE);
            }
            time = view.findViewById(R.id.time);
            time.setText(StringUtil.setText(bean.getCreateTime()));
            status = view.findViewById(R.id.status);
            if (bean.isInCharge()) {
                status.setText(StringUtil.setText(bean.getEnumEventProceedStatus()));
            } else {
                status.setTextColor(ArmsUtils.getColor(mContext, R.color.gray_999));
                status.setText(R.string.prove);
            }
            Button confirm = view.findViewById(R.id.confirm);
            LinearLayout synergyLayout = view.findViewById(R.id.synergy_layout);
            TextView yesNo = view.findViewById(R.id.yes_no);
            if (bean.getEnumIsInSite() > 0 && bean.getEnumIsInSite() < 3) {
                synergyLayout.setVisibility(View.VISIBLE);
                if (bean.getEnumIsInSite() == 1) {
                    yesNo.setText(ArmsUtils.getString(mContext, R.string.no));
                } else if (bean.getEnumIsInSite() == 2) {
                    yesNo.setText(ArmsUtils.getString(mContext, R.string.yes));
                }
            } else {
                synergyLayout.setVisibility(View.GONE);
            }
            confirm.setOnClickListener(v -> dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

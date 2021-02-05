package com.weique.overhaul.v2.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.EventsReportedLookContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.DialogAddPhotoAdapter;
import com.weique.overhaul.v2.mvp.ui.dialogFragment.CommonDialogFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean.ListBean;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@ActivityScope
public class EventsReportedLookPresenter extends ReworkBasePresenter<EventsReportedLookContract.Model, EventsReportedLookContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    Gson gson;
    @Inject
    IRepositoryManager iRepositoryManager;

    @Inject
    public EventsReportedLookPresenter(EventsReportedLookContract.Model model, EventsReportedLookContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 获取事件详情
     *
     * @param id id
     */
    public void getEventDetail(String id, String custId) {
        commonGetData(mModel.getEventRecordInfo(id, custId), mErrorHandler, eventsReportedLookBean -> {
            mRootView.setData(eventsReportedLookBean);
        });
    }

    /**
     * 退回
     *
     * @param content
     * @param id
     * @param status
     */
    public void submitHandlingSuggestion(String content, String id, int status, int synergyStatus) {
        commonGetData(mModel.setEventNodeHandle(content, id, status, synergyStatus), mErrorHandler, o -> {
            mRootView.next();
        });
    }

    /**
     * 评价
     *
     * @param evaluation
     * @param recordId
     */
    public void createEvaluation(String evaluation, String recordId, String images) {
        commonGetData(mModel.createEvaluation(evaluation, recordId, images), mErrorHandler, o -> {
            mRootView.next();
        });
    }

    /**
     * 归档
     *
     * @param recordId
     */
    public void placeOnFile(String recordId) {
        commonGetData(mModel.placeOnFile(recordId), mErrorHandler, o -> {
            mRootView.next();
        });
    }

    /**
     * 获取小流程  - 办理人处理信息列表
     *
     * @param custId
     */
    public void getEventProceedRecord(String custId, String eventRId) {
        commonGetData(mModel.getEventProceedRecord(custId, eventRId), mErrorHandler, eventProceedRecordBeans -> {
            mRootView.setTransactList(eventProceedRecordBeans);
        });
    }

    public void getDepartment(String departmentId) {
        commonGetData(mModel.getGetDepartment(departmentId), mErrorHandler, gridInformationBean -> {
            mRootView.gotoMapTv(gridInformationBean.getPointsInJSON());
        });
    }

    /**
     * 作废
     *
     * @param id id
     */
    public void invalid(String id) {
        commonGetData(mModel.invalid(id), mErrorHandler, o -> mRootView.next());
    }

    public void getPermission(int max) {
        CommonPermissionUtil.getPermission((Activity) mRootView.getActivity(), mErrorHandler, () -> mRootView.goToPhotoAlbum(max), Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
    }

    /**
     * 上传文件
     *
     * @param compressPaths compressPath 文件压缩路径
     */
    public void upLoadFile(String elementId, List<String> compressPaths) {
        try {
            CommonNetworkRequest.upLoadFile(elementId, compressPaths,
                    mErrorHandler, mRootView, iRepositoryManager,
                    uploadFileRsponseBeans -> {
                        try {
                            for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                                String path = uploadFileRsponseBean.getUrl();
                                list.add(new InformationItemPictureBean((path)));
                            }
                            informationAddPhotoAdapter.setNewData(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 0无协同部门  1. 协同部门未道现场  2.  到现场
     */
    private int synergyStatus = 0;

    private DialogAddPhotoAdapter informationAddPhotoAdapter;
    private ArrayList<InformationItemPictureBean> list;

    private void initAdapter(RecyclerView recyclerView) {
        try {
            list = new ArrayList<>();
            list.add(0, new InformationItemPictureBean(R.drawable.picture));
            informationAddPhotoAdapter = new DialogAddPhotoAdapter(list);
            informationAddPhotoAdapter.setMaxItem(10);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(AppManager.getAppManager().getmApplication())
                    .colorResId(R.color.white)
                    .sizeResId(R.dimen.dp_5)
                    .build());
            ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(AppManager.getAppManager().getmApplication(), 4, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(informationAddPhotoAdapter);
            informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                    if (view.getId() == R.id.image_) {
                        assert bean != null;
                        if (bean.getType() == InformationItemPictureBean.IS_DRAWABLE) {
                            if (adapter.getData().size() >= 8) {
                                ArmsUtils.makeText("最多上传7张图片");
                                return;
                            }
                            //9  是加上了默认的加号 图标
                            int max = 8 - adapter.getData().size();
                            getPermission(max);
                        } else {
                            ARouter.getInstance()
                                    .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                    .withString(PictureLookActivity.URL_, bean.getImageUrl())
                                    .navigation();
                        }
                    } else if (view.getId() == R.id.remove_image) {
                        adapter.remove(position);
                        adapter.notifyItemChanged(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化dialog
     */
    public void initDialog(View view, boolean hasSynergy, int getEnumOrderStatus, String id, CommonDialogFragment dialogFragment, int status) {
        try {
            TextView title = view.findViewById(R.id.title);
            EditText editText = view.findViewById(R.id.content);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            TextView impose = view.findViewById(R.id.impose);
            Button confirm = view.findViewById(R.id.confirm);
            LinearLayout synergyLayout = view.findViewById(R.id.synergy_layout);
            if (hasSynergy) {
                synergyLayout.setVisibility(View.VISIBLE);
            } else {
                synergyLayout.setVisibility(View.GONE);
            }
            if (ListBean.EventsReportedEnumBean.COMPLETE == getEnumOrderStatus) {
                RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
                recyclerView.setVisibility(View.VISIBLE);
                initAdapter(recyclerView);
            }
            RadioGroup radioGroup = view.findViewById(R.id.radio_group);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void afterTextChanged(Editable s) {
                    impose.setText(s.toString().length() + "/150");
                }
            });
            dialogFragment.setmCancelListener(new CommonDialogFragment.OnDialogCancelListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void onDismiss() {
                    if (!ArmsUtils.isEmpty(informationAddPhotoAdapter)) {
                        informationAddPhotoAdapter = null;
                    }
                    if (!ArmsUtils.isEmpty(list)) {
                        list = null;
                    }
                }
            });
            confirm.setOnClickListener(v -> {
                try {
                    if (hasSynergy) {
                        if (synergyStatus == 0) {
                            ArmsUtils.makeText("请选择协同部门是否到达现场！");
                            return;
                        }
                    }
                    String content = editText.getText().toString();
                    if (StringUtil.isNullString(editText.getText().toString())) {
                        ArmsUtils.makeText(title.getText().toString());
                        return;
                    }
                    switch (getEnumOrderStatus) {
                        case ListBean.EventsReportedEnumBean.COMPLETE:
                            ArrayList<String> strings = new ArrayList<>();
                            for (InformationItemPictureBean bean : list) {
                                if (StringUtil.isNotNullString(bean.getImageUrl())) {
                                    strings.add(bean.getImageUrl());
                                }
                            }
                            if (strings.size() == 0) {
                                createEvaluation(content, id, "");
                            } else {
                                createEvaluation(content, id, gson.toJson(strings));
                            }

                            break;
                        case ListBean.EventsReportedEnumBean.TRANSACTION:
                        case ListBean.EventsReportedEnumBean.AUDIT:
                            submitHandlingSuggestion(content, id, status, synergyStatus);
                            break;
                        case ListBean.EventsReportedEnumBean.SENDBACK:
                            createEvaluation(content, id, "");
                            break;
                        default:
                            break;
                    }
                    dialogFragment.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (getEnumOrderStatus == ListBean.EventsReportedEnumBean.AUDIT) {
                title.setText("请填写办理意见或备注信息");
                confirm.setText("办理");
            }
            if (getEnumOrderStatus == ListBean.EventsReportedEnumBean.TRANSACTION) {
                title.setText("请填写办理意见或备注信息");
                confirm.setText("办理");
            }
            if (getEnumOrderStatus == ListBean.EventsReportedEnumBean.COMPLETE) {
                title.setText("请填写评价");
                confirm.setText("评价");
            }
            if (getEnumOrderStatus == ListBean.EventsReportedEnumBean.SENDBACK) {
                title.setText("请填写退回原因");
                confirm.setText("退回");
            }
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                switch (checkedId) {
                    case R.id.no_:
                        synergyStatus = 1;
                        break;
                    case R.id.yes_:
                        synergyStatus = 2;
                        break;
                    default:
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

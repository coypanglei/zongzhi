package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.RecordView;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.PhoneInfoUtil;
import com.weique.overhaul.v2.mvp.contract.EventsReportedCrudContract;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedLookBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedLookActivity;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.RecordPopup;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class EventsReportedCrudPresenter extends ReworkBasePresenter<EventsReportedCrudContract.Model, EventsReportedCrudContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RxPermissions rxPermissions;
    @Inject
    IRepositoryManager iRepositoryManager;


    @Inject
    public EventsReportedCrudPresenter(EventsReportedCrudContract.Model model, EventsReportedCrudContract.View rootView) {
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
        commonGetData(mModel.getEventRecordInfo(id, custId), mErrorHandler, o -> {
            mRootView.setData(o);
        });
    }

    public void upLoadFile(String elementId, List<String> paths, boolean isInDynamic) {
        try {
            CommonNetworkRequest.upLoadFile(elementId, paths,
                    mErrorHandler, mRootView, iRepositoryManager,
                    uploadFileRsponseBeans -> {
                        mRootView.setUpdatePicture(uploadFileRsponseBeans, isInDynamic);
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交/残存
     *
     * @param eventsReportedLookBean
     */
    public void submit(EventsReportedLookBean eventsReportedLookBean) {
        commonGetData(mModel.createEventRecord(eventsReportedLookBean), mErrorHandler, o -> {
            EventsReportedBean.ListBean listBean = new EventsReportedBean.ListBean();
            listBean.setCustId(eventsReportedLookBean.getId());
            listBean.setName(eventsReportedLookBean.getElementTypeName());
            listBean.setTitle(eventsReportedLookBean.getName());
            listBean.setTime(eventsReportedLookBean.getCreateTime());
            listBean.setEnumOrderStatus(eventsReportedLookBean.getEnumOrderStatus());
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.ADD, listBean), RouterHub.APP_EVENTSREPORTEDACTIVITY);
            mRootView.killMyself();
            mAppManager.killActivity(EventsReportedLookActivity.class);
        });
    }

    public void alterSubmit(EventsReportedLookBean eventsReportedLookBean) {
        commonGetData(mModel.editEventRecord(eventsReportedLookBean), mErrorHandler, o -> {
            EventsReportedBean.ListBean listBean = new EventsReportedBean.ListBean();
            listBean.setCustId(eventsReportedLookBean.getId());
            listBean.setName(eventsReportedLookBean.getElementTypeName());
            listBean.setTitle(eventsReportedLookBean.getName());
            listBean.setTime(eventsReportedLookBean.getCreateTime());
            listBean.setEnumOrderStatus(eventsReportedLookBean.getEnumOrderStatus());
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.ALERT, listBean), RouterHub.APP_EVENTSREPORTEDACTIVITY);
            mRootView.killMyself();
        });
    }

    public void alterSubmits(EventsReportedLookBean eventsReportedLookBean) {
        commonGetData(mModel.createEventRecordSnap(eventsReportedLookBean), mErrorHandler, o -> {
            EventsReportedBean.ListBean listBean = new EventsReportedBean.ListBean();
            listBean.setCustId(eventsReportedLookBean.getId());
            listBean.setName(eventsReportedLookBean.getElementTypeName());
            listBean.setTitle(eventsReportedLookBean.getName());
            listBean.setTime(eventsReportedLookBean.getCreateTime());
            listBean.setEnumOrderStatus(eventsReportedLookBean.getEnumOrderStatus());
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.ALERT, listBean), RouterHub.APP_EVENTSREPORTEDACTIVITY);
            mRootView.killMyself();
        });
    }

    public void deleteEvent(String custId, String id) {
        commonGetData(mModel.delEventRecord(custId, id), mErrorHandler, o -> {
            mRootView.killMyself();
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.DELETE), RouterHub.APP_EVENTSREPORTEDACTIVITY);
        });
    }

    /**
     * @param adapterPosition adapter 位置
     * @param max             最大数量
     * @param type            是什么item、
     * @param isInDynamic     是否是 动态表单中的 列表
     */
    public void getPermission(int adapterPosition, int max, String type, boolean isInDynamic) {
        PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {

            @Override
            public void onRequestPermissionSuccess() {
                mRootView.goToPhotoAlbum(adapterPosition, max, type, isInDynamic);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                new CommonDialog.Builder(mRootView.getContext()).setTitle("注意")
                        .setContent("您拒绝了部分授权,部分功能无法使用")
                        .setPositiveButton("知道了", (v, commonDialog) -> {
                        }).create().show();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                new CommonDialog.Builder(mRootView.getContext()).setTitle("注意")
                        .setContent("您拒绝了部分授权,并选择不在询问,部分功能无法正常使用")
                        .setPositiveButton("去设置", (v, commonDialog) -> {
                            String brand = Build.BRAND;//手机厂商
                            if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
                                PhoneInfoUtil.gotoMiuiPermission(mRootView.getContext());
                            } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
                                PhoneInfoUtil.gotoMeizuPermission(mRootView.getContext());
                            } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
                                PhoneInfoUtil.gotoHuaweiPermission(mRootView.getContext());
                            } else {
                                mRootView.launchActivity(PhoneInfoUtil.getAppDetailSettingIntent(mRootView.getContext()));
                            }
                        })
                        .setNegativeButton("知道了!", (v, commonDialog) -> {
                        }).create().show();
            }
        }, rxPermissions, mErrorHandler, Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
    }

    /**
     *
     */
    public void getPermissionRecord() {
        try {
            CommonPermissionUtil.getPermission(mRootView.getContext(),
                    mErrorHandler, () -> {
                        try {
                            RecordPopup recordPopup = new RecordPopup(mRootView.getContext(),
                                    RecordView.MODEL_RECORD, "", path -> {
                                File file = new File(path);
                                CommonNetworkRequest.upLoadFile("", path,
                                        mErrorHandler, mRootView, iRepositoryManager,
                                        uploadFileRsponseBeans -> {
                                            try {
                                                if (uploadFileRsponseBeans == null || uploadFileRsponseBeans.size() <= 0) {
                                                    ArmsUtils.makeText("上传文件失败");
                                                    return;
                                                }
                                                if (file.exists()) {
                                                    file.delete();
                                                }
                                                mRootView.setRecordInfo(uploadFileRsponseBeans);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        });
                            });
                            recordPopup.showPopupWindow();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, Constant.READ_WRITE_RECORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

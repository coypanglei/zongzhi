package com.weique.overhaul.v2.mvp.presenter;

import android.app.Activity;
import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EventsReportedLookContract;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.ui.popupwindow.HeatDialog;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
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
    IRepositoryManager mRepositoryManager;

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
     * 评价
     *
     * @param evaluation
     * @param recordId
     */
    public void createEvaluation(String evaluation, String recordId, String images) {
        commonGetData(mModel.createEvaluation(evaluation, recordId, images), mErrorHandler, o -> {
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.SELECT), RouterHub.APP_EVENTSREPORTEDACTIVITY);
            mRootView.killMyself();
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
     * 处置
     *
     * @param id           事件流程记录Id
     * @param fileUrls     图片上传
     * @param returnReason 处理内容
     */
    public void proceedingOrder(String id, String fileUrls, String returnReason) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        map.put("FileUrls", fileUrls);
        map.put("ReturnReason", returnReason);
        Observable<BaseBean<Object>> observable = mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                .proceedingOrder1(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, o -> {
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.SELECT), RouterHub.APP_EVENTSREPORTEDACTIVITY);
            mRootView.killMyself();
        });
    }

    /**
     * 同意 、 拒绝协同
     *
     * @param id           事件流程记录Id
     * @param returnReason 处理内容
     */
    public void eventRecordCoopReceivingOrder(String id, int enumEventProceedStatus, String returnReason) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        map.put("enumEventProceedStatus", enumEventProceedStatus);
        map.put("ReturnReason", returnReason);
        Observable<BaseBean<Object>> observable = mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                .eventRecordCoopReceivingOrder(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, o -> {
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.SELECT), RouterHub.APP_EVENTSREPORTEDACTIVITY);
            mRootView.killMyself();
        });
    }

    /**
     * 受理 退回
     *
     * @param intEventProceedStatus
     */
    public void receivingOrder(String id, int intEventProceedStatus, String returnReason) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        map.put("intEventProceedStatus", intEventProceedStatus);
        map.put("ReturnReason", returnReason);
        Observable<BaseBean<Object>> observable = mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                .receivingOrder(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, o -> {
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.SELECT), RouterHub.APP_EVENTSREPORTEDACTIVITY);
            mRootView.killMyself();
        });
    }

    /**
     * 核查通过  退回
     *
     * @param id
     * @param enumOrderStatus enumOrderStatus
     * @param returnReason
     */
    public void eventRecordCheckOrder(String id, int enumOrderStatus, String returnReason) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        map.put("enumOrderStatus", enumOrderStatus);
        map.put("ReturnReason", returnReason);
        Observable<BaseBean<Object>> observable = mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                .EventRecordCheckOrder(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, o -> {
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.SELECT), RouterHub.APP_EVENTSREPORTEDACTIVITY);
            mRootView.killMyself();
        });
    }


    /**
     * 作废
     *
     * @param id id
     */
    public void invalid(String id) {
        commonGetData(mModel.invalid(id), mErrorHandler, o -> {
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.SELECT), RouterHub.APP_EVENTSREPORTEDACTIVITY);
            mRootView.killMyself();
        });
    }

    public void getPermission(int max) {
        CommonPermissionUtil.getPermission((Activity) mRootView.getActivity(), mErrorHandler,
                () -> mRootView.goToPhotoAlbum(max), Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
    }


    /**
     * 上传文件
     *
     * @param compressPaths compressPath 文件压缩路径
     */
    public void upLoadFile(String elementId, List<String> compressPaths) {
        try {
            CommonNetworkRequest.upLoadFile(elementId, compressPaths,
                    mErrorHandler, mRootView, mRepositoryManager,
                    uploadFileRsponseBeans -> {
                        try {
                            List<InformationItemPictureBean> list = new ArrayList<>();
                            for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                                String path = uploadFileRsponseBean.getUrl();
                                list.add(new InformationItemPictureBean(path));
                            }
                            mRootView.setUpLoadItem(list);
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
     *
     * @param id     事件id
     * @param dialog 弹框
     * @param status 要操作到的 目标状态
     */
    public void initDialog(String id, HeatDialog dialog, int status) {
        try {
            if (status == ListBean.EventsReportedEnumNewBean.DISPOSE_END) {
                dialog.setContentHint("请填写处置意见");
                dialog.setConfirmBtnText("处置");
            }
            if (status == ListBean.EventsReportedEnumNewBean.EXIT) {
                dialog.setContentHint("请填写退回原因");
                dialog.setConfirmBtnText("退回");
            }
            if (status == ListBean.EventsReportedEnumNewBean.INSPECT_FAIL) {
                dialog.setContentHint("请填写核查退回原因");
                dialog.setConfirmBtnText("核查退回");
            }
            if (status == ListBean.EventsReportedEnumNewBean.EVALUATE) {
                dialog.setContentHint("请填写评价信息");
                dialog.setConfirmBtnText("评价");
            }
            if (status == ListBean.SYNCHRONIZE) {
                dialog.setContentHint("请填写同意协同理由");
                dialog.setConfirmBtnText("同意协同");
            }
            if (status == ListBean.REFUSED) {
                dialog.setContentHint("请填写拒绝协同理由");
                dialog.setConfirmBtnText("拒绝协同");
            }
            dialog.setOnConfirmClickListener((heatDialog, content) -> {
                try {
                    switch (status) {
                        case ListBean.EventsReportedEnumNewBean.DISPOSE_END:
                            proceedingOrder(id, dialog.getImageUrls(), content);
                            break;
                        case ListBean.EventsReportedEnumNewBean.EXIT:
                            receivingOrder(id, 1, "");
                            break;
                        case ListBean.EventsReportedEnumNewBean.INSPECT_FAIL:
                            eventRecordCheckOrder(id, 7, "");
                            break;
                        case ListBean.EventsReportedEnumNewBean.EVALUATE:
                            createEvaluation(content, id, dialog.getImageUrls());
                            break;
                        case ListBean.SYNCHRONIZE:
                            eventRecordCoopReceivingOrder(id, 2, content);
                            break;
                        case ListBean.REFUSED:
                            eventRecordCoopReceivingOrder(id, 3, content);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
